package com.longge.gather.mqtt.service;
/**
 * @description 报文业务处理
 * @author jianglong
 * @create 2019-09-11
 **/
public interface BusinessService {
    /**
     *@description: publish报文业务处理
     *@param topic  主题
     *@param payload  消息内容
     *@return boolean
     */
    public boolean doPublishPacket(String topic, byte[] payload);
}
