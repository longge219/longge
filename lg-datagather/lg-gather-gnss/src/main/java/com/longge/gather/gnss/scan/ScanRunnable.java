package com.longge.gather.gnss.scan;
import com.longge.gather.gnss.gnss.model.Observations;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * @description 扫描已封装好的历元数据
 * @author jianglong
 * @create 2019-04-10
 **/
public abstract class ScanRunnable  implements Runnable{

    //安全非堵塞队列
    private ConcurrentLinkedQueue<Observations> queue = new ConcurrentLinkedQueue<>();

    //队列添加一个元素
    public  boolean addQueue(Observations observations){
        return queue.add(observations);
    }

    //队列添加一个集合
    public boolean addQueues(List<Observations> observationsList){
        return queue.addAll(observationsList);
    }


    @Override
    public void run() {
        for (;;){
            Observations observations;
            for (;(observations=queue.poll())!=null;){
                 doObservations(observations);
            }
        }
    }

    /**扫描业务处理观测数据*/
    public  abstract  void  doObservations(Observations observations);

}
