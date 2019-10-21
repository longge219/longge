package com.longge.flink.task.rtu;
import com.longge.flink.task.rtu.app.listener.ApplicationStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @description RTU数据入库监听
 * @author jianglong
 * @create 2019-10-17
 */
@SpringBootApplication(scanBasePackages = {"com.longge"})
public class RtuDataLauncher {
    public static void main( String[] args ){
        SpringApplication springApplication = new SpringApplication(RtuDataLauncher.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);
    }
}
