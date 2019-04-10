package com.longge.gather.gnss.server.start;
import com.longge.gather.gnss.app.NettyBean;
/**
 * @description 初始化服务
 * @author jianglong
 * @create 2019-03-01
 **/
public class InitServer {

    private NettyBean nettyBean;
    
    private BootstrapServer bootstrapServer;

    private Thread thread;

    public InitServer(NettyBean nettyBean) {
        this.nettyBean = nettyBean;
    }
    /**初始化调用方法*/
    public void open(){
        if(nettyBean!=null){
            bootstrapServer = new ServerStart(nettyBean);
            bootstrapServer.start();
        }
    }
    /**销毁时调用方法*/
    public void close(){
        if(bootstrapServer!=null){
            bootstrapServer.shutdown();
            if(thread!=null && thread.isDaemon()){
                thread.interrupt();
            }
        }
    }

}
