package com.longge.gather.mqtt.bean;
import com.longge.gather.mqtt.common.enums.SessionStatus;
import com.longge.gather.mqtt.common.enums.SubStatus;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @description 封装类
 * @author jianglong
 * @create 2019-09-09
 **/
@Builder
@Getter
@Setter
public class MqttChannel {

	//通信channel
    private transient  volatile  Channel channel;

    //客户端全局ID
    private String deviceId;

    //是否是遗言消息
    private boolean isWill;

    //是否订阅过主题
    private volatile SubStatus subStatus;

    //订阅的主题
    private  Set<String> topic  ;

    //会话状态
    private volatile SessionStatus sessionStatus;

    //当为true时channel close时从缓存中删除此channel
    private volatile boolean cleanSession; 

    // messageId - message(qos1)待确认消息
    private ConcurrentHashMap<Integer,SendMqttMessage>  message ; 

    //接收消息ID
    private Set<Integer>  receive;
    
    
    public boolean addTopic(Set<String> topics){
        return topic.addAll(topics);
    }
    
    public void  addRecevice(int messageId){
        receive.add(messageId);
    }
 
    public boolean  checkRecevice(int messageId){
       return  receive.contains(messageId);
    }

    public boolean  removeRecevice(int messageId){
        return receive.remove(messageId);
    }


    public void addSendMqttMessage(int messageId,SendMqttMessage msg){
        message.put(messageId,msg);
    }


    public SendMqttMessage getSendMqttMessage(int messageId){
        return  message.get(messageId);
    }


    public  void removeSendMqttMessage(int messageId){
        message.remove(messageId);
    }

    public boolean isLogin(){
        if(this.channel!=null){
            AttributeKey<Boolean> _login = AttributeKey.valueOf("login");
            return channel.isActive() && channel.hasAttr(_login);
        }
        return false;
    }

    public void close(){
        Optional.ofNullable(this.channel).ifPresent(channel1 -> channel1.close());
    }

    public  boolean isActive(){
        return  channel!=null&&this.channel.isActive();
    }
}
