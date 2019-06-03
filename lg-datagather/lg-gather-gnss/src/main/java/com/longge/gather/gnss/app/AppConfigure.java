package com.longge.gather.gnss.app;
import com.longge.gather.gnss.scan.ScanRunnable;
import com.longge.gather.gnss.scan.ScanScheduled;
import com.longge.gather.gnss.server.start.InitServer;
import com.longge.gather.kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @description 自动化配置初始化服务
 * @author jianglong
 * @create 2019-04-10
 **/
@Configuration
@ConditionalOnClass
@EnableConfigurationProperties({NettyBean.class})
public class AppConfigure {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    /**初始化服务器通信参数对象*/
    @Bean(initMethod = "open", destroyMethod = "close")
    @ConditionalOnMissingBean
    public InitServer initServer(NettyBean nettyBean){
        return new InitServer(nettyBean);
    }

    /***/
    @Bean
    @ConditionalOnMissingBean(name = "scanScheduled")
    public ScanRunnable initRunable(){
        //消息重发周期
        ScanRunnable sacnScheduled = new ScanScheduled(kafkaProducerService);
        Thread scanRunnable = new Thread(sacnScheduled);
        scanRunnable.setDaemon(true);
        scanRunnable.start();
        return sacnScheduled;
    }

}
