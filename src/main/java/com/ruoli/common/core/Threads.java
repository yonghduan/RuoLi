package com.ruoli.common.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Threads
{
    private static final Logger logger = LoggerFactory.getLogger(Threads.class);

    public static void printException(Runnable r,Throwable t)
    {
        if(t == null && r instanceof Future<?>)
        {
            if((((Future<?>)r).isDone()))
            {
                try
                {
                    Future<?> future = (Future<?>) r;
                    future.get();
                }
                catch (ExecutionException ee)
                {
                    t = ee.getCause();
                }
                catch(CancellationException ce)
                {
                    t = ce.getCause();
                }
                catch (InterruptedException ie)
                {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if(t != null)
        {
            logger.error(t.getMessage(),t);
        }
    }
}
