package com.zhongni.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ZhongniGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhongniGatewayApplication.class, args);
	}

}
