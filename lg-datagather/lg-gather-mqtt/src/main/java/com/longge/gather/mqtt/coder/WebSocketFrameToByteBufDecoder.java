package com.longge.gather.mqtt.coder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import java.util.List;

/**
 * WebSocketFrameToByteBufDecoder
 * @author jianglong
 * @create 2019-03-01
 **/
public class WebSocketFrameToByteBufDecoder extends MessageToMessageDecoder<BinaryWebSocketFrame> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame wsFrame, List<Object> out) throws Exception {
        ByteBuf buf = wsFrame.content();
        buf.retain();
        out.add(buf);
    }
}
