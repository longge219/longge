package com.longge.gather.mqtt.service;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
/**
 * @description MQTT控制报文业务处理接口
 * @author jianglong
 * @create 2018-01-23
 **/
public interface MqttHandlerService{
	
    /**
     * @description 收到登录控制报文处理
     * @param channel 通道
     * @param mqttConnectMessage 登录控制报文
     * @return 登录是否成功
     */
    public boolean login(Channel channel, MqttConnectMessage mqttConnectMessage);
    
    /**
     * @description 收到断开连接控制报文处理
     * @param channel 通道
     * @return void
     */
    public void disconnect(Channel channel);
    
    /**
     * @description 收到心跳请求控制报文处理
     * @param channel 通道
     * @return void
     */
    public void pong(Channel channel);
    
    /**
     * @description 收到订阅控制报文业务处理
     * @param channel  通道
     * @param mqttSubscribeMessage 订阅消息
     * @return void 
     */
    public void subscribe(Channel channel, MqttSubscribeMessage mqttSubscribeMessage);
    
    /**
     * @description 收到取消订阅控制报文处理
     * @param channel 通道
     * @param mqttMessage 取消订阅消息
     * @return void
     */
    public void unsubscribe(Channel channel, MqttUnsubscribeMessage mqttMessage);
    
    /**
     * @description 收到发布消息业务处理
     * @param channel 通道
     * @param mqttPublishMessage 发布消息
     * @return void
     */
    public void  publish(Channel channel, MqttPublishMessage mqttPublishMessage);
    
    /**
     * @description 收到消息回复确认控制报文处理
     * @param channel
     * @param mqttMessage
     * @return void
     */
    public void puback(Channel channel, MqttMessage mqttMessage);
    
    /**
     * @description 收到 qos2发布收到控制报文处理
     * @param channel
     * @param mqttMessage
     * @return void
     */
    public void pubrec(Channel channel, MqttMessage mqttMessage);
    
    /**
     * @description 收到qos2发布释放控制报文处理
     * @param channel
     * @param mqttMessage
     * @return void
     */
    public void pubrel(Channel channel, MqttMessage mqttMessage);
    
    /**
     * 收到qos2发布完成控制报文
     * @param channel
     * @param mqttMessage
     * @return void
     */
    void pubcomp(Channel channel, MqttMessage mqttMessage);
  }
