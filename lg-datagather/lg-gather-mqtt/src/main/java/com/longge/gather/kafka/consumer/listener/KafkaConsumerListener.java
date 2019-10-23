package com.longge.gather.kafka.consumer.listener;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;

/**
 * @author: jianglong
 * @description: kafka消费者监听器
 * @date: 2019-09-24
 */
@Slf4j
public class KafkaConsumerListener implements MessageListener<String, String> {
    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        //根据不同的主题进行消费
        String topic = data.topic();
        switch (topic) {
            case "rtudata":
                log.info("--------------topic_test---------------"+data.value());
                break;
            default:
                log.info("存在未监听的topic");
                break;
        }

    }

    @Override
    public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {

    }

    @Override
    public void onMessage(ConsumerRecord<String, String> data, Consumer<?, ?> consumer) {

    }

    @Override
    public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {

    }
}
