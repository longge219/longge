package com.longge.gather.mqtt.common.util;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @description 生成messgaeId
 * @author jianglong
 * @create 2019-09-09
 **/
public class MessageId {
	
    private static AtomicInteger index = new AtomicInteger(1);
    /**获取messageId*/
    public  static int  messageId(){
        for (;;) {
            int current = index.get();
            int next = (current >= Integer.MAX_VALUE ? 0: current + 1);
            if (index.compareAndSet(current, next)) {
                return current;
            }
        }
    }
}
