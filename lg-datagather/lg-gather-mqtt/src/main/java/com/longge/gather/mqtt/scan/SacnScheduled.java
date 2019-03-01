package com.longge.gather.mqtt.scan;
import com.longge.gather.mqtt.bean.SendMqttMessage;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;

/**
 * @description 扫描消息确认
 * @author jianglong
 * @create 2019-03-01
 **/
public class SacnScheduled extends ScanRunnable {

    @SuppressWarnings("unused")
	private final long time;

    public SacnScheduled(long time) {
        this.time = time;
    }

    private boolean checkTime(long time) {
        return System.currentTimeMillis()-time>=10*1000;
    }

    @SuppressWarnings("incomplete-switch")
	@Override
    public void doInfo(SendMqttMessage poll) {
        if(checkTime(poll.getTime()) && poll.getChannel().isActive()){
            poll.setTime(System.currentTimeMillis());
            switch (poll.getConfirmStatus()){
                case PUB:
                    pubMessage(poll.getChannel(),poll);
                    break;
                case PUBREL:
                    sendAck(MqttMessageType.PUBREL,poll);
                    break;
                case PUBREC:
                    sendAck(MqttMessageType.PUBREC,poll);
                    break;
            }
        }
    }

    /**发布消息*/
    private   void pubMessage(Channel channel, SendMqttMessage mqttMessage){
    	//MQTT消息的固定头部
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.PUBLISH,true, mqttMessage.getQos(),mqttMessage.isRetain(),0);
        //MQTT消息的可变头部
        MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader(mqttMessage.getTopic(),mqttMessage.getMessageId());
        //MQTT消息
        MqttPublishMessage mqttPublishMessage = new MqttPublishMessage(mqttFixedHeader,mqttPublishVariableHeader, Unpooled.wrappedBuffer(mqttMessage.getByteBuf()));
        channel.writeAndFlush(mqttPublishMessage);
    }

    /**回复已确认消息*/
    protected void  sendAck(MqttMessageType type,SendMqttMessage mqttMessage){
    	//MQTT消息的固定头部
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(type,true, MqttQoS.AT_LEAST_ONCE,false,0x02);
        //MQTT消息的可变头部
        MqttMessageIdVariableHeader from = MqttMessageIdVariableHeader.from(mqttMessage.getMessageId());
        //MQTT消息
        MqttPubAckMessage mqttPubAckMessage = new MqttPubAckMessage(mqttFixedHeader,from);
        mqttMessage.getChannel().writeAndFlush(mqttPubAckMessage);
    }

}
