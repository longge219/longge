package com.longge.business.service.basic;
import java.util.concurrent.CountDownLatch;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
@SpringBootApplication
@EnableDubboConfiguration
public class BasicServiceLauncher {
	
	@Bean 
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }
	
    public static void main(String[] args) throws InterruptedException {
    	ApplicationContext ctx = new SpringApplicationBuilder()
    			.sources(BasicServiceLauncher.class)
		        .web(WebApplicationType.NONE)
		        .run(args);
        CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
        closeLatch.await();
    }

}
