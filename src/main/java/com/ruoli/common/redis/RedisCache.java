package com.ruoli.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCache
{
    private RedisTemplate redisTemplate;

    @Autowired
    public RedisCache(RedisTemplate redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key,value,timeout,timeUnit);
    }

    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String,T> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }
}
