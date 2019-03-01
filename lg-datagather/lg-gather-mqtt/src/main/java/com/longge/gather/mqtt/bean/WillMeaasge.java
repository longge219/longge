package com.longge.gather.mqtt.bean;
import lombok.Builder;
import lombok.Data;
/**
 * @description 遗嘱消息
 * @author jianglong
 * @create 2019-03-01
 **/
@Builder
@Data
public class WillMeaasge {

    private String willTopic;

    private String willMessage;
    
    private  boolean isRetain;

    private int qos;

}
