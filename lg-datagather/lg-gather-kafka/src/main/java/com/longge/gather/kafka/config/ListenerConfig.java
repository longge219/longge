package com.longge.gather.kafka.config;
import com.longge.gather.kafka.producer.KafkaProducerListener;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/**
 * @author: jianglong
 * @description: kafka线程池配置参数
 * @date: 2019-02-28
 */
@Component
@ConfigurationProperties(prefix = "listener")
@Getter
@Setter
@ToString
public class ListenerConfig {
    /**
     * 配置线程池大小
     */
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer poolQueueCapacity;
    private String threadNamePrefix;
    private Boolean visiblePool;
    /**
     * 配置kafka回调监听器
     */
    private Boolean visibleLog;
    private Integer listenerQueueSize;

    @Bean
    public KafkaProducerListener kafkaProducerListener() {
        BlockingQueue<String> blockingDeque = new ArrayBlockingQueue<>(listenerQueueSize);
        return new KafkaProducerListener(blockingDeque, this.visibleLog);
    }
}
