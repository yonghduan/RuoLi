package com.ruoli.service;

import com.ruoli.utils.SpringUtils;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AsyncManager
{
    private final int OPERATE_DELAY_TIME = 10;

    private final ScheduledExecutorService scheduledExecutorService = SpringUtils.getBean("scheduledExecutorService");

    private AsyncManager(){}

    private static final AsyncManager me = new AsyncManager();

    public static AsyncManager me()
    {
        return me;
    }

    public void executeScheduledTask(final TimerTask timerTask)
    {
        scheduledExecutorService.schedule(timerTask,OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

}
