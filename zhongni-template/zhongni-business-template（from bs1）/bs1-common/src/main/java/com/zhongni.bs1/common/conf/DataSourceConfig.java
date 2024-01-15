package com.zhongni.bs1.common.conf;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(
        prefix = "spring.datasource.hikari"
)
@Data
public class DataSourceConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${mysql.database.name}")
    private String dbName;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.type}")
    private String datasourceType;

    private Integer minimumIdle;

    private Integer maximumPoolSize;

    private Boolean autoCommit;

    private String connectionTestQuery;

    private Long idleTimeout;

    private String poolName;

    private Long maxLifetime;

    private Long connectionTimeout;

    @DependsOn("dbInit")
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return new HikariDataSource(getConfiguration());
    }

    public HikariConfig getConfiguration() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
//      config.addDataSourceProperty("cachePrepStmts", true);
//      config.addDataSourceProperty("prepStmtCacheSize", 500);
//      config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.setConnectionTestQuery(connectionTestQuery);

        config.setAutoCommit(autoCommit);
        //池中最小空闲链接数量
        config.setMinimumIdle(minimumIdle);
        //池中最大链接数量
        config.setMaximumPoolSize(maximumPoolSize);

        config.setIdleTimeout(idleTimeout);

        config.setPoolName(poolName);

        config.setMaxLifetime(maxLifetime);

        config.setConnectionTimeout(connectionTimeout);
        return config;
    }
}
