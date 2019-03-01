package com.longge.gather.kafka.core;
import java.util.concurrent.BlockingQueue;

/**
 * @author: jianglong
 * @description: kafka生产者线程
 * @date: 2019-02-28
 */
public class KafkaProducerMsgDispatcher extends Thread {

    private BlockingQueue<String> blockingQueue;

    public KafkaProducerMsgDispatcher(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            String msg = this.blockingQueue.poll();
            if (msg != null) {
//                log.info("接收到kafka消息: " + msg);
            } else {
                try {
                    // 线程重新争抢cpu执行权, 将cpu资源让给其它线程
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
