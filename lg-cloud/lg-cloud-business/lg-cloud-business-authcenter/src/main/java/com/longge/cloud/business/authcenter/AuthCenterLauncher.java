package com.longge.cloud.business.authcenter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringCloudApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients("com.longge.authcenter.feigin.client")
public class AuthCenterLauncher {
	public static void main(String[] args) {
		SpringApplication.run(AuthCenterLauncher.class, args);
	}
}
