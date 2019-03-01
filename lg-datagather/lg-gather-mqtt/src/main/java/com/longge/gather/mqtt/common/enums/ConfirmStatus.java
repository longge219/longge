package com.longge.gather.mqtt.common.enums;
/**
 * @description MQTT确认状态
 * @author jianglong
 * @create 2019-03-01
 **/
public enum ConfirmStatus {
    PUB, 
    PUBREC,//Server->Client发布PUBREC(已收到)
    PUBREL,//Client->Server发布PUBREL(已释放)
    COMPLETE,//Client发布COMPLETE(已完成),Client删除msg
}
