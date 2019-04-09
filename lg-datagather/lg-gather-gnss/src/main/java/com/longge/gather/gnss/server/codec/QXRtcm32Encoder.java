package com.longge.gather.gnss.server.codec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Description:自定义RTCM3.2协议编码器
 * @create Author:jianglong
 * @create Date:2018-06-12
 * @version V1.0
 */
public class QXRtcm32Encoder extends MessageToByteEncoder<Object> {
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
           		
	}
}
