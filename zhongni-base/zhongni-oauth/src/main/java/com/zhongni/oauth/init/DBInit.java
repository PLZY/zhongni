package com.zhongni.oauth.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component("dbInit")
@Slf4j
public class DBInit {
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${mysql.database.name}")
    private String dbName;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${database.type}")
    private String dbType;
    @Value("${${database.type}.host}")
    private String host;
    @Value("${${database.type}.port}")
    private String port;

    @PostConstruct
    public void init()
    {
        // 创建一个不指定数据库的连接，如果此系统使用的数据库不存在则直接创建
        try (Connection connection = DriverManager.getConnection("jdbc:" + dbType + "://" + host + ":" + port, username, password);
             Statement statement = connection.createStatement())
        {
            Class.forName(driver);
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + dbName + "` DEFAULT CHARACTER SET = `utf8mb4`;");
        }
        catch (ClassNotFoundException | SQLException e)
        {
            log.error("init db info is Exception : {}", e.getMessage(), e);
        }
    }
}
