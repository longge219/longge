package com.longge.gather.mqtt.common.util;
import io.netty.buffer.ByteBuf;
/**
 *@description byteBuf 需要转换成byte[]
 * @author jianglong
 * @create 2019-03-01
 **/
public class ByteBufUtil {
    public  static byte[]  copyByteBuf(ByteBuf byteBuf){
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return bytes;
    }
}
