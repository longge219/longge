package com.longge.flink.source.kafka.properties;

/**
 * @description KAFKA配置常量名称
 * @author jianglong
 * @create 2019-10-18
 */
public class KafkaPropertiesConstants {

    public static final String KAFKA_BROKERS = "kafka.brokers";

    public static final String KAFKA_ZOOKEEPER_CONNECT = "kafka.zookeeper.connect";

    public static final String KAFKA_GROUP_ID = "kafka.group.id";

    public static final String CONSUMER_FROM_TIME = "consumer.from.time";

    public static final String KEY_DESERIALIZER = "key.deserializer";

    public static final String  VALUE_DESERIALIZER = "value.deserializer";

    public static final String AUTO_OFFSET_RESET = "auto.offset.reset";

    public static final String STREAM_CHECKPOINT_INTERVAL  = "stream.checkpoint.interval"; // 非常关键，一定要设置启动检查点！！
}
