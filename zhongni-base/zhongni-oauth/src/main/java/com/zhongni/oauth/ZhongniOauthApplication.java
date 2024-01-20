package com.zhongni.oauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@MapperScan("com.zhongni.oauth.mapper")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableWebSecurity
@PropertySource(value = {"classpath:cust-application.properties"})
public class ZhongniOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhongniOauthApplication.class, args);
	}

}
