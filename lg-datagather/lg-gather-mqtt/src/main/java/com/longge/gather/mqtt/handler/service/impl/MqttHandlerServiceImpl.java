package com.longge.gather.mqtt.handler.service.impl;
import com.longge.gather.mqtt.bean.SendMqttMessage;
import com.longge.gather.mqtt.channel.MqttChannelService;
import com.longge.gather.mqtt.common.enums.ConfirmStatus;
import com.longge.gather.mqtt.handler.service.MqttHandlerService;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description MQTT控制报文业务处理
 * @author jianglong
 * @create 2019-09-09
 **/
@Service("mqttHandlerServiceImpl")
public class MqttHandlerServiceImpl implements MqttHandlerService {

    @Autowired
    private MqttChannelService mqttChannelServiceImpl;
    
    /**
     * @description 收到登录报文处理
     * @param channel 通道
     * @param mqttConnectMessage 登录报文
     * @return 登录是否成功
     */
    @SuppressWarnings("incomplete-switch")
	@Override
    public boolean login(Channel channel, MqttConnectMessage mqttConnectMessage) {
    	//客户端全局ID
        String deviceId = mqttConnectMessage.payload().clientIdentifier();
        //登录失败业务处理
        if (StringUtils.isBlank(deviceId)) {
            return false;
        }
        return  Optional.ofNullable(mqttChannelServiceImpl.getMqttChannel(deviceId))
        		         //已登录过且保存到了全局变量中
		                .map(value -> {
		                    switch (value.getSessionStatus()){
		                        case OPEN:
		                             return false;
		                    }
		                    mqttChannelServiceImpl.loginSuccess(channel, deviceId, mqttConnectMessage);
		                    return true;
		                })
		                 //首次登录
		                .orElseGet(() -> {
		                	mqttChannelServiceImpl.loginSuccess(channel, deviceId, mqttConnectMessage);
		                    return  true;
		                });

    }
    /**
     * @description 收到断开连接报文处理
     * @param channel 通道
     * @return void
     */
    @Override
    public void disconnect(Channel channel) {
    	mqttChannelServiceImpl.closeSuccess(mqttChannelServiceImpl.getDeviceId(channel), true);
    }
    /**
     * @description 收到心跳请求控制报文处理
     * @param channel 通道
     * @return void
     */
    @Override
    public void pong(Channel channel) {
        if (channel.isOpen() && channel.isActive() && channel.isWritable()) {
            MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.PINGRESP, false, MqttQoS.AT_MOST_ONCE, false, 0);
            channel.writeAndFlush(new MqttMessage(fixedHeader));
        }
    }

    /**
     * @description 收到订阅控制报文业务处理
     * @param channel  通道
     * @param mqttSubscribeMessage 订阅消息
     * @return void 
     */
    @Override
    public void subscribe(Channel channel, MqttSubscribeMessage mqttSubscribeMessage) {
        Set<String> topics = mqttSubscribeMessage.payload().topicSubscriptions().stream()
        		             .map(mqttTopicSubscription -> mqttTopicSubscription.topicName())
        		             .collect(Collectors.toSet());
        mqttChannelServiceImpl.suscribeSuccess(mqttChannelServiceImpl.getDeviceId(channel), topics);
        /**返回订阅确认*/
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.SUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0);
        MqttMessageIdVariableHeader variableHeader = MqttMessageIdVariableHeader.from(mqttSubscribeMessage.variableHeader().messageId());
        List<Integer> grantedQoSLevels = new ArrayList<>(topics.size());
        for (int i = 0; i < topics.size(); i++) {
            grantedQoSLevels.add(mqttSubscribeMessage.payload().topicSubscriptions().get(i).qualityOfService().value());
        }
        MqttSubAckPayload payload = new MqttSubAckPayload(grantedQoSLevels);
        MqttSubAckMessage mqttSubAckMessage = new MqttSubAckMessage(mqttFixedHeader, variableHeader, payload);
        channel.writeAndFlush(mqttSubAckMessage);
    }

    /**
     * @description 收到取消订阅控制报文处理
     * @param channel 通道
     * @param mqttMessage 取消订阅消息
     * @return void
     */
    @Override
    public void unsubscribe(Channel channel, MqttUnsubscribeMessage mqttMessage) {
        List<String> topics1 = mqttMessage.payload().topics();
        mqttChannelServiceImpl.unsubscribe(mqttChannelServiceImpl.getDeviceId(channel), topics1);
        /**取消订阅确认*/
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.UNSUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0x02);
        MqttMessageIdVariableHeader variableHeader = MqttMessageIdVariableHeader.from(mqttMessage.variableHeader().messageId());
        MqttUnsubAckMessage mqttUnsubAckMessage = new MqttUnsubAckMessage(mqttFixedHeader, variableHeader);
        channel.writeAndFlush(mqttUnsubAckMessage);
    }

    /**
     * @description 收到发布消息业务处理
     * @param channel 通道
     * @param mqttPublishMessage 发布消息
     * @return void
     */
    @Override
    public void publish(Channel channel, MqttPublishMessage mqttPublishMessage) {
    	mqttChannelServiceImpl.publishSuccess(channel, mqttPublishMessage);
    }
    
    /**
     * @description 收到消息回复确认控制报文处理
     * @param channel
     * @param mqttMessage
     * @return void
     */
    @Override
    public void puback(Channel channel, MqttMessage mqttMessage) {
        MqttMessageIdVariableHeader messageIdVariableHeader = (MqttMessageIdVariableHeader) mqttMessage.variableHeader();
        int messageId = messageIdVariableHeader.messageId();
        mqttChannelServiceImpl.getMqttChannel(mqttChannelServiceImpl.getDeviceId(channel)).getSendMqttMessage(messageId).setConfirmStatus(ConfirmStatus.COMPLETE);
    }
    
    /**
     * @description 收到 qos2发布收到控制报文处理
     * @param channel
     * @param mqttMessage
     * @return void
     */
    @Override
    public void pubrec(Channel channel, MqttMessage mqttMessage ) {
        MqttMessageIdVariableHeader messageIdVariableHeader = (MqttMessageIdVariableHeader) mqttMessage.variableHeader();
        int messageId = messageIdVariableHeader.messageId();
        mqttChannelServiceImpl.getMqttChannel(mqttChannelServiceImpl.getDeviceId(channel)).getSendMqttMessage(messageId).setConfirmStatus(ConfirmStatus.PUBREL);
        mqttChannelServiceImpl.doPubrec(channel, messageId);
    }
    
    /**
     * @description 收到qos2发布释放控制报文处理
     * @param channel
     * @param mqttMessage
     * @return void
     */
    @Override
    public void pubrel(Channel channel, MqttMessage mqttMessage ) {
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = (MqttMessageIdVariableHeader) mqttMessage.variableHeader();
        int messageId = mqttMessageIdVariableHeader.messageId();
        mqttChannelServiceImpl.getMqttChannel(mqttChannelServiceImpl.getDeviceId(channel)).getSendMqttMessage(messageId).setConfirmStatus(ConfirmStatus.COMPLETE); // 复制为空
        mqttChannelServiceImpl.doPubrel(channel, messageId);

    }
    
    /**
     * @description 收到qos2发布完成控制报文
     * @param channel
     * @param mqttMessage
     * @return void
     */
    @Override
    public void pubcomp(Channel channel, MqttMessage mqttMessage ) {
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = (MqttMessageIdVariableHeader) mqttMessage.variableHeader();
        int messageId = mqttMessageIdVariableHeader.messageId();
        SendMqttMessage sendMqttMessage = mqttChannelServiceImpl.getMqttChannel(mqttChannelServiceImpl.getDeviceId(channel)).getSendMqttMessage(messageId);
        sendMqttMessage.setConfirmStatus(ConfirmStatus.COMPLETE); // 复制为空
    }
    
}