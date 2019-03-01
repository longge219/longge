package com.longge.gather.gnss;
import com.longge.gather.gnss.app.listener.ApplicationStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
/**
 * @description 采集服务启动类
 * @author jianglong
 * @create 2019-02-28
 */
@SpringBootApplication(scanBasePackages = {"com.longge"})
@EnableAspectJAutoProxy
public class GatherGnssLauncher {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(GatherGnssLauncher.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
    }
}
