package com.longge.flink.source.kafka.schemas;

import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author: jianglong
 * @description: kafka传输对象序列化反序列化
 * @date: 2019-02-22
 * */
public class MetricSchema implements DeserializationSchema<Metrics>, SerializationSchema<Metrics>{

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    static final byte[] EMPTY_ARRAY = new byte[0];

    /**反序列化*/
    @Override
    public Metrics deserialize(byte[] bytes) throws IOException {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        try{
        return JSON.parseObject(new String(bytes), Metrics.class);
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public boolean isEndOfStream(Metrics metrics) {
        return false;
    }

    /**序列化*/
    @Override
    public byte[] serialize(Metrics metrics) {
        if (null == metrics) {
            return EMPTY_ARRAY;
        }
        try{
            return JSON.toJSONString(metrics).getBytes(DEFAULT_CHARSET);
        }catch(Exception e){
            return EMPTY_ARRAY;
        }
    }

    @Override
    public TypeInformation<Metrics> getProducedType() {
        return TypeInformation.of(Metrics.class);
    }
}
