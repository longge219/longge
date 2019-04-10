package com.longge.gather.gnss;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @description 采集服务启动类
 * @author jianglong
 * @create 2019-02-28
 */
@SpringBootApplication(scanBasePackages = {"com.longge"})
public class GatherGnssLauncher {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(GatherGnssLauncher.class);
        springApplication.run(args);
    }
}
