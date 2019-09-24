package com.longge.gather.mqtt.common.fastjson;
import com.alibaba.fastjson.JSON;
import java.nio.charset.Charset;
/**
 * @author: jianglong
 * @description: FastJson反列化
 * @date: 2019-09-11
 * */
public class FastJsonDeserializer<T>{

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    /**
     *对象反序列化
     * */
    public  T deserialize(String topic, byte[] bytes) {
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
}
