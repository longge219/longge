package com.longge.flink.task.rtu.utils;

import com.longge.flink.source.kafka.model.EquipLineData;
import com.longge.flink.task.rtu.sink.SinkToMySQL;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception{
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.30.0.106:9092");
        props.put("zookeeper.connect", "10.30.0.106:2181");
        props.put("group.id", "jianglong");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //props.put("auto.offset.reset", "latest");
        props.put("auto.offset.reset", "earliest");

        SingleOutputStreamOperator<EquipLineData> student = env.addSource(new FlinkKafkaConsumer011<>(
                "EQUIPLINEDATA",   //这个 kafka topic 需要和上面的工具类的 topic 一致
                new SimpleStringSchema(),
                props)).setParallelism(1)
                .map(string -> FastJsonUtils.jsonToObject(string, EquipLineData.class)); //博客里面用的是 fastjson，这里用的是gson解析，解析字符串成 student 对象
                student.addSink(new SinkToMySQL()); //数据 sink 到 mysql
        env.execute("Flink data sink");
    }
}
