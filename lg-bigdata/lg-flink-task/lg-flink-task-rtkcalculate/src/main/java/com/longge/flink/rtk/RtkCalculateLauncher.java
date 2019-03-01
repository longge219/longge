package com.longge.flink.rtk;
import com.longge.flink.rtk.app.listener.ApplicationStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
/**
 * @description RTK计算启动类
 * @author jianglong
 * @create 2019-02-25
 */
@SpringBootApplication(scanBasePackages = {"com.longge.flink.rtk"})
@EnableAspectJAutoProxy
public class RtkCalculateLauncher {
    public static void main( String[] args ){
        SpringApplication springApplication = new SpringApplication(RtkCalculateLauncher.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
    }
}
