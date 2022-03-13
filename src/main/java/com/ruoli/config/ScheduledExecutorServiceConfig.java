package com.ruoli.config;

import com.ruoli.common.core.Threads;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class ScheduledExecutorServiceConfig
{
    private final int CORE_POOL_SIZE = 50;
    /**
     *target:produce a scheduleExecutor bean*/
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService produceScheduledExecutorBean()
    {
        return new ScheduledThreadPoolExecutor(CORE_POOL_SIZE,
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build())
        {
            @Override
            protected void afterExecute(Runnable r,Throwable t)
            {
                super.afterExecute(r,t);
                Threads.printException(r,t);
            }
        };
    }
}
