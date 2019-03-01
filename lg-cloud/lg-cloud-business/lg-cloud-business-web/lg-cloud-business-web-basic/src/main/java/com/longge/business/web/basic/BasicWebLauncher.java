package com.longge.business.web.basic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@EnableDubboConfiguration
@EnableSwagger2
@SpringBootApplication
public class BasicWebLauncher {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(BasicWebLauncher.class, args);
    }

}
