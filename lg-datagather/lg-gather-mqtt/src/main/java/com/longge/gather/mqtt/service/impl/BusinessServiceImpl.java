package com.longge.gather.mqtt.service.impl;
import com.longge.gather.mqtt.code.PayloadDecode;
import com.longge.gather.mqtt.service.BusinessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
/**
 * @description MQTT控制报文业务处理
 * @author jianglong
 * @create 2019-09-09
 **/
@Service("businessServiceImpl")
public class BusinessServiceImpl  implements BusinessService{

    //上传数据点字符标识
    private static final String publishDataFlag = "$dp";

    /**
     *@description: publish报文业务处理
     *@param topic  主题
     *@param payload  消息内容
     *@return boolean
     */
    public boolean doPublishPacket(String topic, byte[] payload){
        if (StringUtils.isBlank(topic) || payload == null || payload.length==0) {
            return false;
        }else {
            if(topic.equals(publishDataFlag)){
                PayloadDecode payloadDecode = new PayloadDecode();
                try{
                    payloadDecode.decode(payload);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
