package com.ruoli.service.system.impl;

import com.ruoli.common.exceptions.login.CaptchaErrorException;
import com.ruoli.common.exceptions.login.CaptchaExpireException;
import com.ruoli.common.redis.RedisCache;
import com.ruoli.entity.common.LoginUserBody;
import com.ruoli.entity.datasource.RecordLoginInfoTable;
import com.ruoli.enums.LoginFlag;
import com.ruoli.service.AsyncManager;
import com.ruoli.service.factory.AsyncTaskFactory;
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

    @Autowired
    private AsyncTaskFactory asyncTaskFactory;

    @Override
    public String handleLogin(final LoginUserBody loginUserBody)
    {
        /**
         * 1.verify captcha code,if it is not right,throwing exceptions and note login information
         * 2.verify user and password,if rightly,*/
        verifyCaptcha(loginUserBody);
        /**
         * 验证码正确
         * 验证用户名，密码是否匹配*/

        return "success";
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
                AsyncManager.me().executeScheduledTask(asyncTaskFactory.recordLoginInfo(loginUserBody.getUser(),LoginFlag.CAPTCHA_ERROR,"验证码错误"));
                throw new CaptchaErrorException();
            }
        }
        else
        {
            AsyncManager.me().executeScheduledTask(asyncTaskFactory.recordLoginInfo(loginUserBody.getUser(), LoginFlag.CAPTCHA_EXPIRE,"验证码过期"));
            throw new CaptchaExpireException();
        }

    }

}
