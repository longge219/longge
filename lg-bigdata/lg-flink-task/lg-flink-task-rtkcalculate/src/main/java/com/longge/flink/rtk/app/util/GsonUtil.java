package com.longge.flink.rtk.app.util;
import com.alibaba.fastjson.JSON;
import java.nio.charset.Charset;

public class GsonUtil {

    public static <T> T fromJson(String value, Class<T> type) {
        return JSON.parseObject(value, type);
    }

    public static String toJson(Object value) {
        return JSON.toJSONString(value);
    }

    public static byte[] toJSONBytes(Object value) {
        return JSON.toJSONString(value).getBytes(Charset.forName("UTF-8"));
    }
}
