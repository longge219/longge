package com.longge.cloud.business.authcenter.redis.serializer;
import java.nio.charset.Charset;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
/**
 * @author: jianglong
 * @description: 定义redis对象使用fastjson进行序列化
 * @date: 2019-01-14
 * */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
	 
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    
    static final byte[] EMPTY_ARRAY = new byte[0];
 
    private Class<T> clazz;
 
    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }
 
    /**对象序列化*/
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (null == t) {
            return EMPTY_ARRAY;
        }
        try{
        	return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
        }catch(Exception e){
        	return EMPTY_ARRAY;
        }
    }
 
    /**对象反序列化*/
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
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