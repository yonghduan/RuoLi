package com.ruoli.service.system.impl;

import com.ruoli.common.redis.RedisCache;
import com.ruoli.entity.common.SuccessfullyLoginUser;
import com.ruoli.service.system.IJwtTokenService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService implements IJwtTokenService
{
    private static final int KEEP_LOGIN_TIME = 20;

    @Autowired
    private RedisCache redisCache;


    public String generateToken(SuccessfullyLoginUser successfullyLoginUser)
    {
        generateExpireTime(successfullyLoginUser);
        /**
         * save successfullyLoginUser in redis cache*/
        return "token";
    }

    public void generateExpireTime(SuccessfullyLoginUser successfullyLoginUser)
    {
        String loginTime = successfullyLoginUser.getLoginTime();
        DateTime dateTime = new DateTime();
        
    }

    public void refreshExpireTime()
    {
        /**
         * acquire current time,view expire time,
         * if expire time - current time < 15 minutes,set expire time - current time = 20 minutes,
         * else:keep expire time unchanged*/
    }

    public boolean authenticationByToken(String token)
    {
        return true;
    }


}
