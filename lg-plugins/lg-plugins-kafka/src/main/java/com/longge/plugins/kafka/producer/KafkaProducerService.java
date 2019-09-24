package com.longge.plugins.kafka.producer;
/**
 * @author: jianglong
 * @description: kafka发送消息接口
 * @date: 2019-09-24
 */
public interface KafkaProducerService {

     /**发送消息*/
     void sendMessage(String topic, Object msgData);
}
