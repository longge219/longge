package com.longge.gather.mqtt.scan;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.longge.gather.mqtt.bean.SendMqttMessage;
import com.longge.gather.mqtt.common.enums.ConfirmStatus;
/**
 * @description 扫描未确认信息
 * @author jianglong
 * @create 2019-03-01
 **/
public abstract class ScanRunnable  implements Runnable {


    //安全非堵塞队列
    private ConcurrentLinkedQueue<SendMqttMessage> queue  = new ConcurrentLinkedQueue<>();

    //队列添加一个元素
    public  boolean addQueue(SendMqttMessage t){
        return queue.add(t);
    }
    //队列添加一个集合
    public  boolean addQueues(List<SendMqttMessage> ts){
        return queue.addAll(ts);
    }


    //判断消息队列是否确认
    @Override
    public void run() {
        for(;;){
            List<SendMqttMessage> list =new LinkedList<>();
            SendMqttMessage poll ;
            for(;(poll=queue.poll())!=null;){
                if(poll.getConfirmStatus()!= ConfirmStatus.COMPLETE){
                    list.add(poll);
                    doInfo(poll);
                }
                break;
            }
            addQueues(list);
        }
    }
    //向客户端确认消息
    public  abstract  void  doInfo(SendMqttMessage poll);


}
