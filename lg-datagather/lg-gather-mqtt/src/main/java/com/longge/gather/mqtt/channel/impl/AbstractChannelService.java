package com.longge.gather.mqtt.channel.impl;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.longge.gather.mqtt.bean.MqttChannel;
import com.longge.gather.mqtt.bean.RetainMessage;
import com.longge.gather.mqtt.channel.BaseApi;
import com.longge.gather.mqtt.channel.MqttChannelService;
import com.longge.gather.mqtt.common.util.CacheMap;
import com.longge.gather.mqtt.scan.ScanRunnable;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.apache.commons.lang3.StringUtils;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @description channel业务处理
 * @author jianglong
 * @create 2019-09-09
 **/
public abstract class AbstractChannelService extends PublishApiSevice implements MqttChannelService, BaseApi {

    public AbstractChannelService(ScanRunnable scanRunnable) {
        super(scanRunnable);
    }
    
    //channel的登录状态属性
    protected AttributeKey<Boolean> _login = AttributeKey.valueOf("login");

    //channel的客户端唯一标识数据
    protected   AttributeKey<String> _deviceId = AttributeKey.valueOf("deviceId");

    //主题消息分隔符
    protected  static char SPLITOR = '/';

    //线程池线程管理
    protected ExecutorService executorService =Executors.newCachedThreadPool();

    //全局channel缓存
    protected static ConcurrentHashMap<String , MqttChannel> mqttChannels = new ConcurrentHashMap<>();

    //缓存chnnel主题消息
    protected static CacheMap<String,MqttChannel> cacheMap= new CacheMap<>();
 
    //缓存chnnel保留消息
    protected  static  ConcurrentHashMap<String,ConcurrentLinkedQueue<RetainMessage>> retain = new ConcurrentHashMap<>();

    //主题消息关联的channel关联
    protected  static  Cache<String, Collection<MqttChannel>> mqttChannelCache = CacheBuilder.newBuilder().maximumSize(100).build();

    /**根据客户端全局ID查找channel*/
    public MqttChannel getMqttChannel(String deviceId){
        return mqttChannels.get(deviceId);

    }

    /**获取channelId*/
    public String  getDeviceId(Channel channel){
        return channel.attr(_deviceId).get();
    }

    /**根据主题消息查找需要发布的channel集合*/
    @FunctionalInterface
    interface TopicFilter{
        Collection<MqttChannel> filter(String topic);
    }
    protected  Collection<MqttChannel> getChannels(String topic,TopicFilter topicFilter){
            try {
                return  mqttChannelCache.get(topic, () -> topicFilter.filter(topic));
            } catch (Exception e) {
            }
            return null;
    }

    /**删除channel的主题消息*/
    protected boolean deleteChannel(String topic,MqttChannel mqttChannel){
        mqttChannelCache.invalidate(topic);
        return  cacheMap.delete(getTopic(topic),mqttChannel);
    }
    /**添加channel的主题消息*/
    protected boolean addChannel(String topic,MqttChannel mqttChannel)
    {
        mqttChannelCache.invalidate(topic);
        return  cacheMap.putData(getTopic(topic),mqttChannel);
    }
    /**获取主题消息*/
    protected String[] getTopic(String topic)  {
        return StringUtils.split(topic,SPLITOR);
    }
}
