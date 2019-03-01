package com.longge.gather.mqtt.bean;
import com.longge.gather.mqtt.common.enums.ConfirmStatus;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.Builder;
import lombok.Data;

/**
 * @description MQTT消息
 * @author jianglong
 * @create 2019-03-01
 **/
@Builder
@Data
public class SendMqttMessage {

    private int messageId;

    private Channel channel;

    private volatile ConfirmStatus confirmStatus;

    private long time;

    private byte[]  byteBuf;

    private boolean isRetain;

    private MqttQoS qos;

    private String topic;

}
