package com.longge.flink.source.kafka.factory;
import com.longge.flink.source.kafka.properties.KafkaConfigPropertiesConstants;
import com.longge.flink.source.kafka.schemas.MetricSchema;
import com.longge.flink.source.kafka.watermarks.MetricWatermark;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.internals.KafkaTopicPartition;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.longge.flink.source.kafka.schemas.Metrics;
/**
 * @description KAFKA-SOURCE创建工厂
 * @author jianglong
 * @create 2019-02-25
 */
public class KafkaSourceFactory {

    /**设置 kafka 配置*/
    public static Properties buildKafkaProps(ParameterTool parameterTool) {
        Properties props = parameterTool.getProperties();
        props.put("bootstrap.servers", parameterTool.get(KafkaConfigPropertiesConstants.KAFKA_BROKERS));
        props.put("zookeeper.connect", parameterTool.get(KafkaConfigPropertiesConstants.KAFKA_ZOOKEEPER_CONNECT));
        props.put("group.id", parameterTool.get(KafkaConfigPropertiesConstants.KAFKA_GROUP_ID));
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest");
        return props;
    }

    /**创建source*/
    public static DataStreamSource<Metrics> createKafkaSource(StreamExecutionEnvironment env) throws IllegalAccessException {
        ParameterTool parameter = (ParameterTool) env.getConfig().getGlobalJobParameters();
        String topic = parameter.getRequired(KafkaConfigPropertiesConstants.METRICS_TOPIC);
        Long time = parameter.getLong(KafkaConfigPropertiesConstants.CONSUMER_FROM_TIME, 0L);
        return createKafkaSource(env, topic, time);
    }

    /**创建source*/
    public static DataStreamSource<Metrics> createKafkaSource(StreamExecutionEnvironment env, String topic, Long time) throws IllegalAccessException {
        ParameterTool parameterTool = (ParameterTool) env.getConfig().getGlobalJobParameters();
        Properties props = buildKafkaProps(parameterTool);
        FlinkKafkaConsumer011<Metrics> consumer = new FlinkKafkaConsumer011<>(
                topic,
                new MetricSchema(),
                props);
        //重置offset到time时刻
        if (time != 0L) {
            Map<KafkaTopicPartition, Long> partitionOffset = buildOffsetByTime(props, parameterTool, time);
            consumer.setStartFromSpecificOffsets(partitionOffset);
        }
        return env.addSource(consumer);
    }

    private static Map<KafkaTopicPartition, Long> buildOffsetByTime(Properties props, ParameterTool parameterTool, Long time) {
        props.setProperty("group.id", "query_time_" + time);
        KafkaConsumer consumer = new KafkaConsumer(props);
        List<PartitionInfo> partitionsFor = consumer.partitionsFor(parameterTool.getRequired(KafkaConfigPropertiesConstants.METRICS_TOPIC));
        Map<TopicPartition, Long> partitionInfoLongMap = new HashMap<>();
        for (PartitionInfo partitionInfo : partitionsFor) {
            partitionInfoLongMap.put(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()), time);
        }
        Map<TopicPartition, OffsetAndTimestamp> offsetResult = consumer.offsetsForTimes(partitionInfoLongMap);
        Map<KafkaTopicPartition, Long> partitionOffset = new HashMap<>();
        offsetResult.forEach((key, value) -> partitionOffset.put(new KafkaTopicPartition(key.topic(), key.partition()), value.offset()));

        consumer.close();
        return partitionOffset;
    }

    public static SingleOutputStreamOperator<Metrics> parseSource(DataStreamSource<Metrics> dataStreamSource) {
        return dataStreamSource.assignTimestampsAndWatermarks(new MetricWatermark());
    }
}
