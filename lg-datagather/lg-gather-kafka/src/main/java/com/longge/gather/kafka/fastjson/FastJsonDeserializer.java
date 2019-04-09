package com.longge.gather.kafka.fastjson;
import java.nio.charset.Charset;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.common.serialization.Deserializer;
/**
 * @author: jianglong
 * @description: FastJson反列化
 * @date: 2019-04-09
 * */
public class FastJsonDeserializer<T> implements Deserializer<T> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        try{
            String str = new String(bytes, DEFAULT_CHARSET);
            return (T) JSON.parseObject(str, clazz);
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public void close() {

    }
}
