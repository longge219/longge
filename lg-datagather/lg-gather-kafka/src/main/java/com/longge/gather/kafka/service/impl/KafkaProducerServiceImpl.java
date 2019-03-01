package com.longge.gather.kafka.service.impl;
import com.longge.gather.kafka.service.KafkaProducerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.concurrent.Executor;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Resource
    private Executor executor;

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic , String msgData){
        executor.execute(() ->{
            kafkaTemplate.sendDefault(topic,msgData);
        });
    }
}
