package com.longge.gather.mqtt.server;
import com.longge.gather.mqtt.bean.InitBean;
/**
 * @description 初始化服务
 * @author jianglong
 * @create 2019-03-01
 **/
public class InitServer {

    private InitBean serverBean;
    
    private BootstrapServer bootstrapServer;

    private Thread thread;

    public InitServer(InitBean serverBean) {
        this.serverBean = serverBean;
    }
    /**
     * 初始化调用方法
     * */
    public void open(){
        if(serverBean!=null){
            bootstrapServer = new NettyBootstrapServer();
            bootstrapServer.setServerBean(serverBean);
            bootstrapServer.start();
        }
    }
    /**
     * 销毁时调用方法
     * */
    public void close(){
        if(bootstrapServer!=null){
            bootstrapServer.shutdown();
            if(thread!=null && thread.isDaemon()){
                thread.interrupt();
            }
        }
    }

}
