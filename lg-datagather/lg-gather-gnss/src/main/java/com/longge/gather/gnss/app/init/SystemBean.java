package com.longge.gather.gnss.app.init;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;
/**
 * @description 系统启动通信配置参数
 * @author jianglong
 * @create 2018-03-26
 **/
@Component
@ConfigurationProperties(prefix ="com.orieange.system")
@Data
public class SystemBean {
	
	/**socket类型*/
	private String socketType;
	
	/**协议名称*/
    private String socketProtocol;
    /**
     * #Socket参数，连接保活，默认值为False。启用该功能时，TCP会主动探测空闲连接的有效性。
     * 可以将此功能视为TCP的心跳机制，需要注意的是：默认的心跳间隔是7200s即2小时。Netty默认关闭该功能
     * */
    private boolean socketKeepalive ;
    /**
     * 地址复用，默认值False。有四种情况可以使用：
     * (1).当有一个有相同本地地址和端口的socket1处于TIME_WAIT状态时，而你希望启动的程序的socket2要占用该地址和端口，比如重启服务且保持先前端口。
     * (2).有多块网卡或用IP Alias技术的机器在同一端口启动多个进程，但每个进程绑定的本地IP地址不能相同。
     * (3).单个进程绑定相同的端口到多个socket上，但每个socket绑定的ip地址不同。
     * (4).完全相同的地址和端口的重复绑定。但这只用于UDP的多播，不用于TCP。
     * */
    private boolean socketReuseaddr ;
    
    /**
     * TCP参数，立即发送数据，默认值为Ture（Netty默认为True而操作系统默认为False）。
     * 该值设置Nagle算法的启用，改算法将小的碎片数据连接成更大的报文来最小化所发送的报文的数量，
     * 如果需要发送一些较小的报文，则需要禁用该算法。Netty默认禁用该算法，从而最小化报文传输延时。
     * */
    private boolean socketTcpNodelay ;
    
    /**Socket参数，服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝*/
    private int socketBacklog ;
    
    /**Socket参数，TCP数据发送缓冲区大小*/
    private  int  socketSndbuf ;

    /**Socket参数，TCP数据接收缓冲区大小*/
    private int socketRevbuf ;
    
    /**读超时时间*/
    private  int  socketRead;

    /**写超时时间*/
    private int socketWrite;
    
    /**读写超时时间*/
    private int socketReadAndWrite;
    
    /**使用ssl加密*/
    private boolean socketSsl ;
    
    /**securesocket.jks #ssl 加密 jks文件地址*/
    private String socketJksFile;

    /**读取jks密码*/
    private String socketJksStorePassword;

    /** 读取私钥密码*/
    private String socketjksCertificatePassword;

    /**服务端启动监听端口*/
    private int serverPort ;

    /**用于处理客户端的连接请求*/
    private int serverBossThread;

    /**用于处理与各个客户端连接的IO操作*/
    private int serverWorkThread;
    
    /**扫描频次*/
    private  int serverPeriod ;
    
    /**主线程*/
    private int clientBossThread;

	/**链接超时*/
    private int clientConnectTime;
    
	/**
	 * 服务端停止服务后，客户端尝试重连的最大次数，重连最大次数仍失败后，不再进行重连尝试。
	 * 赋值小于1时表示客户端尝试次数没有限制，直到重连成功。
	 */
	private int clientMaxTimes;
	
	/**尝试重连时的间隔时间，毫秒*/
	private long clientPeriod;
}
