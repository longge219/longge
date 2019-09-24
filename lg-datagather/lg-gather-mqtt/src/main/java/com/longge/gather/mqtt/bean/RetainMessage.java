package com.longge.gather.mqtt.bean;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.Builder;
import lombok.Data;

/**
 * @description 保留消息
 * @author jianglong
 * @create 2019-09-09
 **/
@Builder
@Data
public class RetainMessage {

    private byte[]  byteBuf;

    private MqttQoS qoS;

    public String getString(){
        return new String(byteBuf);
    }
}
