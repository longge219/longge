package com.longge.gather.mqtt.bean;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.Builder;
import lombok.Data;

/**
 * @description 会话消息
 * @author jianglong
 * @create 2019-03-01
 **/
@Builder
@Data
public class SessionMessage {

    private byte[]  byteBuf;

    private MqttQoS qoS;

    private  String topic;


    public String getString(){
        return new String(byteBuf);
    }
}
