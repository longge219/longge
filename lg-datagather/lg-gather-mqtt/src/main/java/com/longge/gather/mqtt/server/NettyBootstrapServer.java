package com.longge.gather.mqtt.server;
import com.longge.gather.mqtt.bean.InitBean;
import com.longge.gather.mqtt.common.util.IpUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Setter;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * @description netty服务启动类--主类
 * @author jianglong
 * @create 2018-01-23
 **/
public class NettyBootstrapServer extends AbstractBootstrapServer {

	@Setter
    private InitBean serverBean; //启动参数
	
    private ServerBootstrap bootstrap; //服务端启动类

    private NioEventLoopGroup bossGroup; //处理客户端的连接请求线程池

    private NioEventLoopGroup workGroup; //处理与各个客户端连接的 IO 操作线程池
    
    private static final Logger logger = LogManager.getLogger(NettyBootstrapServer.class);
    /**
     * 启动服务
     */
    public void start() {
        initEventPool();//初始化EnentPool
        bootstrap.group(bossGroup, workGroup)
                  /**bossGroup线程池设置*/
                 .channel(NioServerSocketChannel.class) //服务类型
                 .option(ChannelOption.SO_REUSEADDR, serverBean.isReuseaddr()) //地址复用
                 .option(ChannelOption.SO_BACKLOG, serverBean.getBacklog()) //服务端接受连接的队列长度
                 .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                 .option(ChannelOption.SO_RCVBUF, serverBean.getRevbuf()) //TCP数据接收缓冲区大小
                  /**workGroup线程池设置*/
                 .childHandler(new ChannelInitializer<SocketChannel>() {
                     protected void initChannel(SocketChannel ch) throws Exception {
                         initHandler(ch.pipeline(),serverBean);
                     }
                })
                .childOption(ChannelOption.TCP_NODELAY, serverBean.isTcpNodelay()) //立即发送数据
                .childOption(ChannelOption.SO_KEEPALIVE, serverBean.isKeepalive())  //长连接
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.bind(serverBean.getPort()).addListener((ChannelFutureListener) channelFuture -> {
                 if (channelFuture.isSuccess())
            	      logger.info("服务端启动成功【" + IpUtils.getHost() + ":" + serverBean.getPort() + "】");
                 else
            	      logger.info("服务端启动失败【" + IpUtils.getHost() + ":" + serverBean.getPort() + "】");
        });
    }
    /**
     * 关闭服务
     */
    public void shutdown() {
        if(workGroup!=null && bossGroup!=null ){
            try {
            	// 优雅关闭
                bossGroup.shutdownGracefully().sync();
                workGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
            	logger.info("服务端关闭资源失败【" + IpUtils.getHost() + ":" + serverBean.getPort() + "】");
            }
        }
    }
    /**
     * 初始化EnentPool
     */
    private void  initEventPool(){
        bootstrap= new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(serverBean.getBossThread(), new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);
            public Thread newThread(Runnable r) {
            return new Thread(r, "BOSS_" + index.incrementAndGet());
            }
        });
        workGroup = new NioEventLoopGroup(serverBean.getWorkThread(), new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);
            public Thread newThread(Runnable r) {
                return new Thread(r, "WORK_" + index.incrementAndGet());
            }
        });
    }
}

