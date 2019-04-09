package com.longge.gather.gnss.server.start;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import com.longge.gather.gnss.app.init.SystemBean;
import com.longge.gather.gnss.server.codec.QXRtcm32Decoder;
import com.longge.gather.gnss.server.codec.QXRtcm32Encoder;
import com.longge.gather.gnss.server.codec.Rtcm32Decoder;
import com.longge.gather.gnss.server.codec.Rtcm32Encoder;
import com.longge.gather.gnss.server.handler.ServerHandler;
import com.longge.gather.gnss.server.ssl.SecureSocketSslContextFactory;
import com.longge.gather.gnss.utils.SpringBeanUtils;
import io.netty.util.internal.SystemPropertyUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
/**
 * @description 抽象类 负责加载--编解码器---业务处理器
 * @author jianglong
 * @create 2018-07-10
 **/
public abstract class AbstractBootstrapServer implements BootstrapServer {
	
	private static final Logger logger = LogManager.getLogger(AbstractBootstrapServer.class);
			
	/**
	 * 设置过滤器链表
	 * @param: channelPipeline
	 * @param: serverBean
	 */
	protected void initHandler(ChannelPipeline channelPipeline, SystemBean systemBean) {
		if (systemBean.isSocketSsl()) {
			if (!ObjectUtils.allNotNull(systemBean.getSocketjksCertificatePassword(), systemBean.getSocketJksFile(),systemBean.getSocketJksStorePassword())) {
				throw new NullPointerException("SSL file and password is null");
			}
			// 设置SSL--channel
			channelPipeline.addLast("ssl", initSsl(systemBean));
		}
		// 设置协议处理器--channel
		intProtocolHandler(channelPipeline, systemBean);
		// 设置空闲心跳空闲检测--channel
		channelPipeline.addLast(new IdleStateHandler(systemBean.getSocketRead(), 
				                                    systemBean.getSocketWrite(), 
				                                    systemBean.getSocketReadAndWrite(),
				                                    TimeUnit.SECONDS));
		// 设置业务处理类
		channelPipeline.addLast(SpringBeanUtils.getBean(ServerHandler.class));
	}

	/** 设置协议---编解码 */
	private void intProtocolHandler(ChannelPipeline channelPipeline, SystemBean systemBean) {
		if (StringUtils.isNotBlank(systemBean.getSocketProtocol())) {
			switch (systemBean.getSocketProtocol()) {
			case "qxrtcm3.2":
				// HTTP请求消息解码器
				channelPipeline.addLast("decoder", new QXRtcm32Decoder());
				// HTTP响应编码器,对HTTP响应进行编码
				channelPipeline.addLast("encoder", new QXRtcm32Encoder());
				break;
		   case "rtcm3.2":
				// HTTP请求消息解码器
				channelPipeline.addLast("decoder", new Rtcm32Decoder());
				// HTTP响应编码器,对HTTP响应进行编码
				channelPipeline.addLast("encoder", new Rtcm32Encoder());
				break;
			
		   default:
				  logger.error("启动系统参数配置协议名称错处-----" + systemBean.getSocketProtocol());
			}
		}
	}

	/**设置SSL*/
	private SslHandler initSsl(SystemBean systemBean) {
		String algorithm = SystemPropertyUtil.get("ssl.KeyManagerFactory.algorithm");
		if (algorithm == null) {
			algorithm = "SunX509";
		}
		SSLContext serverContext;
		try {
			//
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(SecureSocketSslContextFactory.class.getResourceAsStream(systemBean.getSocketJksFile()),
					systemBean.getSocketJksStorePassword().toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
			kmf.init(ks, systemBean.getSocketjksCertificatePassword().toCharArray());
			serverContext = SSLContext.getInstance("TLS");
			serverContext.init(kmf.getKeyManagers(), null, null);
		} catch (Exception e) {
			throw new Error("Failed to initialize the server-side SSLContext", e);
		}
		SSLEngine engine = serverContext.createSSLEngine();
		engine.setUseClientMode(false);
		return new SslHandler(engine);
	}
}
