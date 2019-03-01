package com.longge.gather.mqtt.server;
import com.longge.gather.mqtt.bean.InitBean;
/**
 * @description 启动类接口
 * @author jianglong
 * @create 2019-03-01
 **/
public interface BootstrapServer {
	
	void start();

    void shutdown();

    void setServerBean(InitBean serverBean);
}
