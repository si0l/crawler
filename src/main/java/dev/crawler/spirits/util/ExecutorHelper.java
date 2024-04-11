package dev.crawler.spirits.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import dev.crawler.spirits.config.PoolProperties;

public class ExecutorHelper {

    public static ThreadPoolTaskExecutor taskExecutor(PoolProperties poolProperties) {

        int poolSize = poolProperties.getType() == PoolType.AUTO ? Runtime.getRuntime().availableProcessors()
                : poolProperties.getSize();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setThreadNamePrefix("Crawler-");
        executor.initialize();

        return executor;
    }
}