package com.longge.gather.gnss.scan;
import com.longge.gather.gnss.server.model.Observations;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * @description 扫描已封装好的历元数据
 * @author jianglong
 * @create 2019-04-10
 **/
public abstract class ScanRunnable  implements Runnable{

    //观测数据安全非堵塞队列
    private ConcurrentLinkedQueue<Observations> obsQueue = new ConcurrentLinkedQueue<>();

    //队列一个时刻的观测数据
    public  boolean adObsQueue(Observations observations){
        return obsQueue.add(observations);
    }

    @Override
    public void run() {
        for (;;){
            Observations observations;
            for (;(observations=obsQueue.poll())!=null;){
                 doObservations(observations);
            }
        }
    }
    /**扫描业务处理观测数据*/
    public  abstract  void  doObservations(Observations observations);

}
