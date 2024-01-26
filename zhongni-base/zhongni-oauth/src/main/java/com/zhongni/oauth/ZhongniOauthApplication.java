package com.zhongni.oauth;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.net.InetAddress;
import java.net.UnknownHostException;

@MapperScan("com.zhongni.oauth.mapper")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@PropertySource(value = {"classpath:cust-application.properties"})
@Slf4j
public class ZhongniOauthApplication {

	public static void main(String[] args) {
		prerun();
		SpringApplication.run(ZhongniOauthApplication.class, args);
	}

	private static void prerun()
	{
		String localIpAddress = "";
		try
		{
			InetAddress localHost = InetAddress.getLocalHost();
			localIpAddress = localHost.getHostAddress() + "(" + localHost.getHostName() + ")";
			System.setProperty("local-ip", localIpAddress);
			log.info("local host ip address is {}", localIpAddress);
		}
		catch (UnknownHostException e)
		{
			System.setProperty("local-ip", localIpAddress);
			log.error("getHostAddress is UnknownHostException {}", e.getMessage(), e);
		}
	}

}
