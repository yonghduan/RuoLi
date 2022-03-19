package com.ruoli.service.system.impl;

import com.ruoli.common.Constants;
import com.ruoli.common.exceptions.ServiceException;
import com.ruoli.common.exceptions.login.CaptchaErrorException;
import com.ruoli.common.exceptions.login.CaptchaExpireException;
import com.ruoli.common.redis.RedisCache;
import com.ruoli.entity.common.LoginUserBody;
import com.ruoli.entity.common.SuccessfullyLoginUser;
import com.ruoli.entity.datasource.RecordLoginInfoTable;
import com.ruoli.entity.datasource.SystemUserTable;
import com.ruoli.enums.LoginFlag;
import com.ruoli.enums.ServiceExceptionType;
import com.ruoli.service.AsyncManager;
import com.ruoli.service.factory.AsyncTaskFactory;
import com.ruoli.service.impl.ConfigService;
import com.ruoli.service.system.IJwtTokenService;
import com.ruoli.service.system.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Autowired
    private ConfigService configService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private IJwtTokenService jwtTokenService;

    private final static Logger log = LoggerFactory.getLogger(LoginService.class);


    @Override
    public String handleLogin(final LoginUserBody loginUserBody)
    {
        /**
         * 1.verify captcha code,if it is not right,throwing exceptions and note login information
         * 2.verify user and password,if rightly,*/
        boolean captchaButton = configService.getCaptchaButton();
        if(captchaButton)
            verifyCaptcha(loginUserBody);
        /**
         * 验证码正确
         * 验证用户名，密码是否匹配
         * use spring security to view whether password matches username*/
        String user = loginUserBody.getUser();
        String password = loginUserBody.getPassword();

        Authentication authentication = null;
        try
        {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user, password));
        }
        catch (Exception e)
        {
            if(e instanceof BadCredentialsException)
            {
                AsyncManager.me().executeScheduledTask(asyncTaskFactory.recordLoginInfo(user,LoginFlag.PASSWORD_ERROR,"密码错误"));
                throw new ServiceException(ServiceExceptionType.PASSWORD_ERROR);
            }
            else
            {
                String message = e.getMessage();
                AsyncManager.me().executeScheduledTask(asyncTaskFactory.recordLoginInfo(user,LoginFlag.LOGIN_FAIL,message));
                throw new ServiceException(message);
            }
        }
        AsyncManager.me().executeScheduledTask(asyncTaskFactory.recordLoginInfo(user,LoginFlag.LOGIN_SUCCESS,"登录成功"));
        /**
         *login successfully,generating a redis cache to note login state for current user */
        String tokenForUser = createSuccessfullyLoginUser((SystemUserTable) authentication.getPrincipal());
        return tokenForUser;
    }

    public String createSuccessfullyLoginUser(SystemUserTable systemUserTable)
    {
        SuccessfullyLoginUser successfullyLoginUser = SuccessfullyLoginUser.createSuccessfullyLoginUser(systemUserTable);
        String token = jwtTokenService.generateToken(successfullyLoginUser);
        return token;
    }

    public void verifyCaptcha(final LoginUserBody loginUserBody)
    {
        final String uuid = loginUserBody.getUuid();
        final String codeFromUser = loginUserBody.getCode();
        final String captchaCodeKey = Constants.CAPTCHA_CODE_KEY + uuid;
        final String codeFromRedis = redisCache.getCacheObject(captchaCodeKey);
        if(codeFromRedis != null)
        {
            if(!codeFromRedis.equals(codeFromUser))
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
