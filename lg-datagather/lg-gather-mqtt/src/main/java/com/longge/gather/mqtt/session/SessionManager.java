package com.longge.gather.mqtt.session;
import com.longge.gather.mqtt.bean.SessionMessage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @description 会话消息缓存
 * @author jianglong
 * @create 2018-01-23
 **/
public class  SessionManager {

    private static ConcurrentHashMap<String,ConcurrentLinkedQueue<SessionMessage>>  queueSession  = new ConcurrentHashMap<>();

    /**连接关闭后保留会话的消息*/
    public  void saveSessionMsg(String deviceId, SessionMessage sessionMessage) {
        ConcurrentLinkedQueue<SessionMessage> sessionMessages = queueSession.getOrDefault(deviceId, new ConcurrentLinkedQueue<>());
        boolean flag;
        do{
             flag = sessionMessages.add(sessionMessage);
        }
        while (!flag);
        queueSession.put(deviceId,sessionMessages);
    }

    /**根据客户端全局ID获取缓存的消息*/
    public  ConcurrentLinkedQueue<SessionMessage> getByteBuf(String deviceId){
        return queueSession.get(deviceId);
    }
}
