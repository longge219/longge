package com.longge.gather.mqtt.code;
import com.longge.gather.mqtt.code.protocol.Payload_3;
import com.longge.gather.mqtt.code.protocol.Payload_4;
import com.longge.gather.mqtt.code.protocol.ProtocolHead;
import com.longge.gather.mqtt.code.reflect.ClassProcess;
import com.longge.gather.mqtt.code.reflect.SubAnnotation;
import com.longge.gather.mqtt.common.util.Bits;
import com.longge.gather.mqtt.common.util.ByteConvert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;

/**
 * @description 报文体解码
 * @author jianglong
 * @create 2019-09-11
 **/
public class PayloadDecode {

    private  int nextLength = 0;

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    //反射工具类
    private static ClassProcess classProcess  = new ClassProcess();

    public ProtocolHead decode(byte[] payload) throws Exception{
        return (ProtocolHead)dodecode(payload);
    }

     /**解码*/
    public Object dodecode(byte[] payload) throws Exception{
        if(payload.length > 3){
            //数据类型
            boolean[] booleanData= Bits.byteToBits(payload[0],6);
            int dataType = Bits.bitsToUInt(booleanData);
            System.out.println("数据类型..............." + dataType);
            if(classProcess.verifyTag(dataType)){
                Class<?> Class = classProcess.getClassByTag(dataType);
                Object object = getObjectByBytes(Class,payload);
                return object;
            }
        }
        return null;
    }

    /**封装对象并赋值*/
    public Object getObjectByBytes(Class<?> clazz, byte[] payload) throws Exception {
        // 实例化类
        Object obj = clazz.newInstance();
        // 得到类中private 的属性
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // 得到属性对应的注释
            Annotation ano = field.getAnnotation(SubAnnotation.class);
            if (ano != null) {
                SubAnnotation sub = (SubAnnotation) ano;
                field.setAccessible(true);
                Object v = getValues(sub.type(),sub.startPos(), sub.len(),sub.mark(),sub.className(), payload);
                if (v == null) {

                } else {
                    field.set(obj, v);// 得到此属性设值
                }
            }
        }
        return obj;
    }


    //获取属性值
    public Object getValues(String type,int startPos, int len, String mark,String className, byte[] payload) throws Exception {
        if (type.equalsIgnoreCase("uint")) {
            if (mark.equals("nextLength")) {
                byte[] nextLengthBytes = Arrays.copyOfRange(payload, startPos, startPos+len);
                nextLength = ByteConvert.bytesToShortS(nextLengthBytes);
            }
            return nextLength;
        } else if (type.equalsIgnoreCase("json")) {
            if (mark.equals("lenByNextLength")) {
                byte[] dataJsonBytes = Arrays.copyOfRange(payload, startPos, startPos+nextLength);
                String dataJsonStr = new String(dataJsonBytes, DEFAULT_CHARSET);
                System.out.println("JSON字符串.................." + dataJsonStr);
                return dataJsonStr;
            }
            return "";
        } else if (type.equalsIgnoreCase("object")) {
            Class<?> clazz = Class.forName(className);
            return getObjectByBytes(clazz, payload);
        } else {
            return null;
        }
    }

}
