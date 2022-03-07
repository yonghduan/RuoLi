package com.ruoli.service.system.impl;

import com.ruoli.common.exceptions.login.CaptchaErrorException;
import com.ruoli.common.exceptions.login.CaptchaExpireException;
import com.ruoli.common.redis.RedisCache;
import com.ruoli.entity.common.LoginUserBody;
import com.ruoli.entity.datasource.RecordLoginInfoTable;
import com.ruoli.service.system.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.Date;

@Service
public class LoginService implements ILoginService
{
    @Autowired
    private RedisCache redisCache;

    @Override
    public String handleLogin(final LoginUserBody loginUserBody)
    {
        /**
         * 1.verify captcha code,if it is not right,throwing exceptions and note login information
         * 2.verify user and password,if rightly,*/
        verifyCaptcha(loginUserBody);
    }

    public void verifyCaptcha(final LoginUserBody loginUserBody)
    {
        final String uuid = loginUserBody.getUuid();
        final String codeFromUser = loginUserBody.getCode();
        final String codeFromRedis = redisCache.getCacheObject(uuid);
        if(codeFromRedis != null)
        {
            if(codeFromRedis != codeFromUser)
            {
                /**
                 * record login information in this thread*/
                throw new CaptchaErrorException();
            }
        }
        else
        {
            throw new CaptchaExpireException();
        }
    }

}
