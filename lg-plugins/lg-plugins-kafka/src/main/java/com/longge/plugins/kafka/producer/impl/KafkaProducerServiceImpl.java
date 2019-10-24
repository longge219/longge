package com.longge.plugins.kafka.producer.impl;
import com.longge.plugins.kafka.fastjson.FastJsonUtils;
import com.longge.plugins.kafka.producer.KafkaProducerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.concurrent.Executor;
/**
 * @author: jianglong
 * @description: kafka发送消息接口实现
 * @date: 2019-09-24
 */
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Resource
    private Executor executor;

    /**发送消息*/
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic , Object msgData){
        executor.execute(() ->{
            kafkaTemplate.send(topic,FastJsonUtils.ObjectTojson(msgData));
        });
    }
}
