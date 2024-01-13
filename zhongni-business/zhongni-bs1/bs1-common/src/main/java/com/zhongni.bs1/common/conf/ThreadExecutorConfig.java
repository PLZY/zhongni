package com.zhongni.bs1.common.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lvxt
 */
@Configuration
public class ThreadExecutorConfig {

    @Value("${business.threadpool.keepalive}")
    private Integer businessThreadPoolKeepAlive;
    @Value("${business.threadpool.corethread}")
    private Integer businessThreadPoolCoreThread;
    @Value("${business.threadpool.maxthread}")
    private Integer businessThreadPoolMaxThread;
    @Value("${business.threadpool.queuecapacity}")
    private Integer businessThreadPoolQueueCapacity;

    @Value("${asynctask.threadpool.keepalive}")
    private Integer asyncTaskThreadPoolKeepAlive;
    @Value("${asynctask.threadpool.corethread}")
    private Integer asyncTaskThreadPoolCoreThread;
    @Value("${asynctask.threadpool.maxthread}")
    private Integer asyncTaskThreadPoolMaxThread;
    @Value("${asynctask.threadpool.queuecapacity}")
    private Integer asyncTaskThreadPoolQueueCapacity;

    @Bean(name = "businessThreadPool")
    public ThreadPoolTaskExecutor businessThreadPool() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setKeepAliveSeconds(businessThreadPoolKeepAlive);
        //核心线程池数
        pool.setCorePoolSize(businessThreadPoolCoreThread);
        // 最大线程
        pool.setMaxPoolSize(businessThreadPoolMaxThread);
        //队列容量
        pool.setQueueCapacity(businessThreadPoolQueueCapacity);
        //队列满，线程被拒绝执行策略
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        pool.setThreadNamePrefix("zhongni-bs1-");
        return pool;
    }


    @Bean(name = "asyncTaskThreadPool")
    public ThreadPoolTaskExecutor asyncTaskThreadPool() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setKeepAliveSeconds(asyncTaskThreadPoolKeepAlive);
        //核心线程池数
        pool.setCorePoolSize(asyncTaskThreadPoolCoreThread);
        // 最大线程
        pool.setMaxPoolSize(asyncTaskThreadPoolMaxThread);
        //队列容量
        pool.setQueueCapacity(asyncTaskThreadPoolQueueCapacity);
        //队列满，线程被拒绝执行策略
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        pool.setThreadNamePrefix("zhongni-bs1-asyncTask-");
        return pool;
    }
}
