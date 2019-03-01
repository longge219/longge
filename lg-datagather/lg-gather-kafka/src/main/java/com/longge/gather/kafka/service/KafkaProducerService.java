package com.longge.gather.kafka.service;

public interface KafkaProducerService {

     void sendMessage(String topic, String msgData);
}
