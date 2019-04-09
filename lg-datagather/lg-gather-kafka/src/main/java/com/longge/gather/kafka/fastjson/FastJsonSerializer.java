package com.longge.gather.kafka.fastjson;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.nio.charset.Charset;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.common.serialization.Serializer;
/**
 * @author: jianglong
 * @description: FastJson序列化
 * @date: 2019-04-09
 * */
public class FastJsonSerializer<T> implements Serializer<T> {

    static final byte[] EMPTY_ARRAY = new byte[0];

    static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, T t) {
        if (null == t) {
            return EMPTY_ARRAY;
        }
        try{
            return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
        }catch(Exception e){
            return EMPTY_ARRAY;
        }
    }

    @Override
    public void close() {

    }
}
