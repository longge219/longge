package com.longge.gather.mqtt.server;
import com.longge.gather.mqtt.bean.InitBean;
import com.longge.gather.mqtt.coder.ByteBufToWebSocketFrameEncoder;
import com.longge.gather.mqtt.coder.WebSocketFrameToByteBufDecoder;
import com.longge.gather.mqtt.common.ssl.SecureSocketSslContextFactory;
import com.longge.gather.mqtt.common.util.SpringBeanUtils;
import com.longge.gather.mqtt.handler.MqttHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.lang3.ObjectUtils;
import org.jboss.netty.util.internal.SystemPropertyUtil;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.security.KeyStore;
/**
 * @description 抽象类 负责加载--编解码器---业务处理器
 * @author jianglong
 * @create 2019-03-01
 **/
public abstract class AbstractBootstrapServer implements BootstrapServer {
    /**
     * 设置过滤器链表
     * @param channelPipeline
     * @param serverBean  服务配置参数
     */
    protected  void initHandler(ChannelPipeline channelPipeline, InitBean serverBean){
        if(serverBean.isSsl()){
            if(!ObjectUtils.allNotNull(serverBean.getJksCertificatePassword(),serverBean.getJksFile(),serverBean.getJksStorePassword())){
                throw  new NullPointerException("SSL file and password is null");
            }
            //设置SSL--channel
            channelPipeline.addLast("ssl",initSsl(serverBean));
        }
        //设置协议处理器--channel
        intProtocolHandler(channelPipeline,serverBean);
        //设置空闲心跳空闲检测--channel
        channelPipeline.addLast(new IdleStateHandler(serverBean.getRead(), serverBean.getWrite(), serverBean.getReadAndWrite())); 
        //设置MQTT默认处理--channel
        channelPipeline.addLast(SpringBeanUtils.getBean(MqttHandler.class));
    }

     /**设置协议---编解码*/
    private void intProtocolHandler(ChannelPipeline channelPipeline,InitBean serverBean){
            switch (serverBean.getProtocol()){
                    case "MQTT":
                        channelPipeline.addLast("encoder", MqttEncoder.INSTANCE);
                        channelPipeline.addLast("decoder", new MqttDecoder());
                        break;
                    case "MQTT_WS_MQTT":
                        channelPipeline.addLast("httpCode", new HttpServerCodec());
                        channelPipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                        channelPipeline.addLast("webSocketHandler", new WebSocketServerProtocolHandler("/", "mqtt, mqttv3.1, mqttv3.1.1"));
                        channelPipeline.addLast("wsDecoder", new WebSocketFrameToByteBufDecoder());
                        channelPipeline.addLast("wsEncoder", new ByteBufToWebSocketFrameEncoder());
                        channelPipeline.addLast("decoder", new MqttDecoder());
                        channelPipeline.addLast("encoder", MqttEncoder.INSTANCE);
                        break;
                    case "MQTT_WS_PAHO":
                        channelPipeline.addLast("httpCode", new HttpServerCodec());
                        channelPipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                        channelPipeline.addLast("webSocketHandler",new WebSocketServerProtocolHandler("/mqtt", "mqtt, mqttv3.1, mqttv3.1.1"));
                        channelPipeline.addLast("wsDecoder", new WebSocketFrameToByteBufDecoder());
                        channelPipeline.addLast("wsEncoder", new ByteBufToWebSocketFrameEncoder());
                        channelPipeline.addLast("decoder", new MqttDecoder());
                        channelPipeline.addLast("encoder", MqttEncoder.INSTANCE);
                        break;
                }
    }
  /**
    * 设置SSL
    * @param serverBean
    */
    private SslHandler initSsl(InitBean serverBean){
        String algorithm = SystemPropertyUtil.get("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SunX509";
        }
        SSLContext serverContext;
        try {
            //
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(SecureSocketSslContextFactory.class.getResourceAsStream(serverBean.getJksFile()),serverBean.getJksStorePassword().toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            kmf.init(ks,serverBean.getJksCertificatePassword().toCharArray());
            serverContext = SSLContext.getInstance("TLS");
            serverContext.init(kmf.getKeyManagers(), null, null);
        } catch (Exception e) {
            throw new Error("Failed to initialize the server-side SSLContext", e);
        }
        SSLEngine engine = serverContext.createSSLEngine();
        engine.setUseClientMode(false);
        return  new SslHandler(engine);
    }
}
