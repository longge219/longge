package com.longge.gather.kafka.service;
/**
 * @author: jianglong
 * @description: kafka发送消息接口
 * @date: 2019-02-28
 */
public interface KafkaProducerService {

     /**发送消息*/
     void sendMessage(String topic, String msgData);
}
