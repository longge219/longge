package com.longge.gather.mqtt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @description 工程启动类
 * @author jianglong
 * @create 2019-09-09
 **/
@SpringBootApplication(scanBasePackages = {"com.longge"})
public class GatherMqttLauncher {
    public static void main(String[] args) {
        SpringApplication.run(GatherMqttLauncher.class, args);
    }
}
