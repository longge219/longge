package com.longge.gather.business.service;
/**
 * @description 报文业务处理
 * @author jianglong
 * @create 2019-10-09
 **/
public interface BusinessService {

    /**
     *@description: connect、diconnect报文业务处理
     *@param deviceId  设备ID
     *@param isConnect 是上线还是掉线
     *@return boolean
     */
     void doLinePacket(String deviceId, boolean isConnect);

    /**
     *@description: publish报文业务处理
     * @param deviceId  设备ID
     *@param topic  主题
     *@param payload  消息内容
     *@return boolean
     */
     boolean doPublishPacket(String deviceId, String topic, byte[] payload);

}
