package com.zhongni.bs1.starter;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

@MapperScan("com.zhongni.bs1.mapper")
@ComponentScan(basePackages={"com.zhongni"})
@EnableScheduling
@EnableMPP
@Slf4j
@PropertySource(value = {"classpath:cust-business.properties", "classpath:cust-email.properties"})
// 排除DataSourceAutoConfiguration自动配置类，后续自动创建数据库时会在配置类中进行配置
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ZhongniApplication {

	public static void main(String[] args) {
		prerun();
		SpringApplication.run(com.zhongni.bs1.starter.ZhongniApplication.class, args);
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
