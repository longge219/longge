package com.longge.gather.mqtt.handler;
import com.longge.gather.mqtt.channel.MqttChannelService;
import com.longge.gather.mqtt.service.MqttHandlerService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @description MQTT处理
 * @author jianglong
 * @create 2019-03-01
 **/
@ChannelHandler.Sharable
@Component
public class MqttHandler extends SimpleChannelInboundHandler<MqttMessage> {

    private static final Logger logger = LogManager.getLogger(MqttHandler.class);

    @Autowired
    private MqttChannelService mqttChannelServiceImpl;
    
    @Autowired
    private MqttHandlerService mqttHandlerServiceImpl;
    /**
     * 服务端业务接收到数据
     * */
    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, MqttMessage mqttMessage) throws Exception {
    	logger.info("服务端接收到消息");
        MqttFixedHeader mqttFixedHeader = mqttMessage.fixedHeader();
        Optional.ofNullable(mqttFixedHeader).ifPresent((value) -> doMessage(channelHandlerContext,mqttMessage));
    }
    /**
     *客户端关闭连接
     * */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	logger.info("服务端空闲");
    	mqttChannelServiceImpl.closeSuccess(mqttChannelServiceImpl.getDeviceId(ctx.channel()), false);
    	ctx.channel().close();
        super.channelInactive(ctx);
    }

    /**
     * 超时处理
     * */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
        	logger.info("心跳超时");
        	IdleStateEvent  idleStateEvent = (IdleStateEvent) evt;
        	Channel channel =  ctx.channel();
            switch (idleStateEvent.state()) {
            case READER_IDLE:
            	mqttChannelServiceImpl.closeSuccess(mqttChannelServiceImpl.getDeviceId(channel), false);
                channel.close();
            case WRITER_IDLE:
            	mqttChannelServiceImpl.closeSuccess(mqttChannelServiceImpl.getDeviceId(channel), false);
                channel.close();
            case ALL_IDLE:
            	mqttChannelServiceImpl.closeSuccess(mqttChannelServiceImpl.getDeviceId(channel), false);
                channel.close();
        }
        }
        super.userEventTriggered(ctx, evt);
    }
    
    /**处理消息*/
    private void doMessage(ChannelHandlerContext channelHandlerContext, MqttMessage mqttMessage) {
        Channel channel = channelHandlerContext.channel();
        MqttFixedHeader mqttFixedHeader = mqttMessage.fixedHeader();
        if(mqttFixedHeader.messageType().equals(MqttMessageType.CONNECT)){
            if(!mqttHandlerServiceImpl.login(channel, (MqttConnectMessage) mqttMessage)){
                channel.close();
            }
            return ;
        }
        //第一个报文不是登录(CONNECT)控制报文
        if (StringUtils.isBlank(mqttChannelServiceImpl.getDeviceId(channel))) {
        	channel.close();
        	return;
        }
        if(mqttChannelServiceImpl.getMqttChannel(mqttChannelServiceImpl.getDeviceId(channel)).isLogin()){
            switch (mqttFixedHeader.messageType()){
	            case DISCONNECT:
	            	 mqttHandlerServiceImpl.disconnect(channel);
	                 break;
                case PINGREQ:
                	mqttHandlerServiceImpl.pong(channel);
                    break;
                case SUBSCRIBE:
                	mqttHandlerServiceImpl.subscribe(channel, (MqttSubscribeMessage) mqttMessage);
                    break;
                case UNSUBSCRIBE:
                	mqttHandlerServiceImpl.unsubscribe(channel,(MqttUnsubscribeMessage)mqttMessage);
                    break;
                case PUBLISH:
                	mqttHandlerServiceImpl.publish(channel, (MqttPublishMessage) mqttMessage);
                    break;
                case PUBACK:
                	mqttHandlerServiceImpl.puback(channel,mqttMessage);
                    break;
                case PUBREC:
                	mqttHandlerServiceImpl.pubrec(channel,mqttMessage);
                    break;
                case PUBREL:
                	mqttHandlerServiceImpl.pubrel(channel,mqttMessage);
                    break;
                case PUBCOMP:
                	mqttHandlerServiceImpl.pubcomp(channel,mqttMessage);
                    break;
                default:
                    break;
            }
        }
    }
}
