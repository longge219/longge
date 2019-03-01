package com.longge.gather.mqtt.channel;
import com.longge.gather.mqtt.bean.MqttChannel;
import com.longge.gather.mqtt.bean.WillMeaasge;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import java.util.List;
import java.util.Set;
/**
 * @description MQTT控制报文channel处理
 * @author jianglong
 * @create 2018-01-23
 **/
public interface MqttChannelService {    
    
    /**成功登陆*/
    public void loginSuccess(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage);
    
    /**成功断开连接*/
    public void closeSuccess(String deviceId, boolean isDisconnect);
    
    /**成功订阅消息*/
    public void suscribeSuccess(String deviceId, Set<String> topics);
   
    /**成功取消订阅*/
    public void unsubscribe(String deviceId, List<String> topics1);
    
    /**成功发布消息*/
    public void publishSuccess(Channel channel, MqttPublishMessage mqttPublishMessage);
    
    /**成功qos2发布释放*/
    public void  doPubrel(Channel channel, int mqttMessage);
    
    /**成功qos2发布收到*/
    public void  doPubrec(Channel channel, int mqttMessage);  
    
    /**发送遗嘱消息*/
    public void sendWillMsg(WillMeaasge willMeaasge);

    /**根据客户端全局ID查找channel*/
    public MqttChannel getMqttChannel(String deviceId);
    
    /**获取channelId*/
    public String  getDeviceId(Channel channel);   

}
