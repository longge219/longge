package com.longge.gather.gnss.server.start;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import com.longge.gather.gnss.app.init.SystemBean;
import com.longge.gather.gnss.utils.IpUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
/**
 * @description 服务启动类--主类
 * @author jianglong
 * @create 2018-07-10
 **/
@Component
public class ServerStart extends AbstractBootstrapServer {

	private SystemBean systemBean; // 启动参数

	private ServerBootstrap bootstrap; // 服务端启动类

	private NioEventLoopGroup bossGroup; // 处理客户端的连接请求线程池

	private NioEventLoopGroup workGroup; // 处理与各个客户端连接的 IO 操作线程池

	private static final Logger logger = LogManager.getLogger(ServerStart.class);

	/**构造方法*/
	public ServerStart(SystemBean systemBean) {
		this.systemBean = systemBean;
	}

	/** 启动服务*/
	public void start() {
		initEventPool();// 初始化EnentPool
		bootstrap.group(bossGroup, workGroup)
				/** bossGroup线程池设置 */
				.channel(NioServerSocketChannel.class) // 服务类型
				.option(ChannelOption.SO_REUSEADDR, systemBean.isSocketReuseaddr()) // 地址复用
				.option(ChannelOption.SO_BACKLOG, systemBean.getSocketBacklog()) // 服务端接受连接的队列长度
				.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
				.option(ChannelOption.SO_RCVBUF, systemBean.getSocketRevbuf()) // TCP数据接收缓冲区大小
				/** workGroup线程池设置 */
				.childHandler(new ChannelInitializer<SocketChannel>() {
					protected void initChannel(SocketChannel ch) throws Exception {
						initHandler(ch.pipeline(), systemBean);
					}
				}).childOption(ChannelOption.TCP_NODELAY, systemBean.isSocketTcpNodelay()) // 立即发送数据
				.childOption(ChannelOption.SO_KEEPALIVE, systemBean.isSocketKeepalive()) // 长连接
				.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		bootstrap.bind(systemBean.getServerPort()).addListener((ChannelFutureListener) channelFuture -> {
			if (channelFuture.isSuccess())
				logger.info("服务端启动成功【" + IpUtils.getHost() + ":" + systemBean.getServerPort() + "】");
			else
				logger.info("服务端启动失败【" + IpUtils.getHost() + ":" + systemBean.getServerPort() + "】");
		});
	}

	/**
	 * 关闭服务
	 */
	public void shutdown() {
		if (workGroup != null && bossGroup != null) {
			try {
				// 优雅关闭
				bossGroup.shutdownGracefully().sync();
				workGroup.shutdownGracefully().sync();
			} catch (InterruptedException e) {
				logger.info("服务端关闭资源失败【" + IpUtils.getHost() + ":" + systemBean.getServerPort() + "】");
			}
		}
	}

	/**
	 * 初始化EnentPool
	 */
	private void initEventPool() {
		bootstrap = new ServerBootstrap();
		bossGroup = new NioEventLoopGroup(systemBean.getServerBossThread(), new ThreadFactory() {
			private AtomicInteger index = new AtomicInteger(0);

			public Thread newThread(Runnable r) {
				return new Thread(r, "SERVER_BOSS_" + index.incrementAndGet());
			}
		});
		workGroup = new NioEventLoopGroup(systemBean.getServerWorkThread(), new ThreadFactory() {
			private AtomicInteger index = new AtomicInteger(0);

			public Thread newThread(Runnable r) {
				return new Thread(r, "SERVER_WORK_" + index.incrementAndGet());
			}
		});
	}
}
