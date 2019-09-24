package com.longge.gather.mqtt.channel.impl;
import com.longge.gather.mqtt.bean.WillMeaasge;
import com.longge.gather.mqtt.channel.MqttChannelService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @description 遗嘱消息处理
 * @author jianglong
 * @create 2019-09-09
 **/
public class WillService {

    @Autowired
    MqttChannelService mqttChannelServiceImpl;
    
    private static final Logger logger = LogManager.getLogger(WillService.class);

    //缓存的遗言消息
    private static  ConcurrentHashMap<String,WillMeaasge> willMeaasges = new ConcurrentHashMap<>();

    /**
     * 保存遗嘱消息
     */
    public void save(String deviceid, WillMeaasge build) {
        willMeaasges.put(deviceid,build);
    }
    
    /**
     * 删除遗嘱消息
     */
    public void del(String deviceid ) {
    	willMeaasges.remove(deviceid);
    }
     /**
      * 客户端断开连接后 开启遗嘱消息发送
      */
    public void doSend(String deviceId) {  
        if(StringUtils.isNotBlank(deviceId)&&(willMeaasges.get(deviceId))!=null){
            WillMeaasge willMeaasge = willMeaasges.get(deviceId);
            mqttChannelServiceImpl.sendWillMsg(willMeaasge);
            if(!willMeaasge.isRetain()){
                willMeaasges.remove(deviceId);
                logger.info("will message["+willMeaasge.getWillMessage()+"] is removed");
            }
        }
    }

}
