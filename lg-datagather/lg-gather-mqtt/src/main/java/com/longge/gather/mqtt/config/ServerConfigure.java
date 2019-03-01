package com.longge.gather.mqtt.config;
import com.longge.gather.mqtt.bean.InitBean;
import com.longge.gather.mqtt.scan.SacnScheduled;
import com.longge.gather.mqtt.scan.ScanRunnable;
import com.longge.gather.mqtt.server.InitServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @description 自动化配置初始化服务
 * @author jianglong
 * @create 2019-03-01
 **/
@Configuration
@ConditionalOnClass
@EnableConfigurationProperties({InitBean.class})
public class ServerConfigure {
    /**
     * 启动扫描消息确认线程(qos消息重发)
     * */
    @Bean
    @ConditionalOnMissingBean(name = "sacnScheduled")
    public ScanRunnable initRunable(@Autowired InitBean serverBean){
    	//消息重发周期
        long time =(serverBean==null || serverBean.getPeriod()<5)?10:serverBean.getPeriod();
        ScanRunnable sacnScheduled = new SacnScheduled(time);
        Thread scanRunnable = new Thread(sacnScheduled);
        scanRunnable.setDaemon(true);
        scanRunnable.start();
        return sacnScheduled;
    }


    /**
     * 初始化服务器通信参数对象
     * */
    @Bean(initMethod = "open", destroyMethod = "close")
    @ConditionalOnMissingBean
    public InitServer initServer(InitBean serverBean){
        return new InitServer(serverBean);
    }


}
