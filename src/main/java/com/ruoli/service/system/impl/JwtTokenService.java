package com.ruoli.service.system.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ruoli.common.Constants;
import com.ruoli.common.redis.RedisCache;
import com.ruoli.entity.common.SuccessfullyLoginUser;
import com.ruoli.service.system.IJwtTokenService;
import com.ruoli.utils.IdUtils;
import com.ruoli.utils.StringUtils;
import com.ruoli.utils.TimeUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtTokenService implements IJwtTokenService
{
    private static final int KEEP_LOGIN_TIME = 20;
    private static final int REFRESH_EXPIRE_TIME_LIMIT = 15;

    @Value("${ruoli.secretKey}")
    private String secretKey;

    @Autowired
    private RedisCache redisCache;

    @Override
    public SuccessfullyLoginUser getSuccessfullyLoginUser(HttpServletRequest request)
    {
        String authentication = request.getHeader("Authentication");
        String redisKey = parseTokenInJwtString(authentication);
        return redisCache.getCacheObject(redisKey);
    }

    public String generateToken(SuccessfullyLoginUser successfullyLoginUser)
    {
        generateExpireTime(successfullyLoginUser);
        String uuid = IdUtils.getUUID();
        String token = createJwtTokenForUser(uuid);
        saveItInRedis(uuid,successfullyLoginUser);
        /**
         * save successfullyLoginUser in redis cache*/
        return token;
    }

    public void saveItInRedis(String uuid,final SuccessfullyLoginUser successfullyLoginUser)
    {
        String loginUserKey = Constants.LOGIN_USER_KEY + ": " + uuid;
        successfullyLoginUser.setRedisKey(loginUserKey);
        redisCache.setCacheObject(loginUserKey,successfullyLoginUser,KEEP_LOGIN_TIME, TimeUnit.MINUTES);
    }

    public String createJwtTokenForUser(final String uuid)
    {

        String token = JWT.create()
                .withClaim(Constants.LOGIN_USER_KEY,uuid)
                .sign(Algorithm.HMAC256(secretKey));
        return token;
    }

    public void generateExpireTime(SuccessfullyLoginUser successfullyLoginUser)
    {
        DateTime loginTime = successfullyLoginUser.getLoginTime();
        successfullyLoginUser.setExpireTime(loginTime.plusMinutes(KEEP_LOGIN_TIME));
    }

    public void refreshExpireTime(SuccessfullyLoginUser successfullyLoginUser)
    {
        /**
         * acquire current time,view expire time,
         * if expire time - current time < 15 minutes,set expire time - current time = 20 minutes,
         * else:keep expire time unchanged*/
        DateTime currentTime = new DateTime();
        int minutesOfCurrentTime = currentTime.getMinuteOfDay();

        DateTime expireTime = successfullyLoginUser.getExpireTime();
        int minutesOfExpireTime = currentTime.getMinuteOfDay();

        int difference = minutesOfExpireTime - minutesOfCurrentTime;
        if(difference < REFRESH_EXPIRE_TIME_LIMIT)
        {
            successfullyLoginUser.setExpireTime(currentTime.plusMinutes(KEEP_LOGIN_TIME));
            /**
             * change expire time for object in redis*/
            redisCache.setCacheObject(successfullyLoginUser.getRedisKey(),successfullyLoginUser,KEEP_LOGIN_TIME,TimeUnit.MINUTES);
        }
    }

    public boolean authenticationByToken(String token)
    {
        boolean isAuthenticated = false;
        String redisKey = parseTokenInJwtString(token);
        SuccessfullyLoginUser successfullyLoginUser = getSuccessfullyLoginUser(redisKey);
        if(successfullyLoginUser != null)
        {
            refreshExpireTime(successfullyLoginUser);
            isAuthenticated = true;
        }
        else
            return isAuthenticated;
        return isAuthenticated;
    }

    public String parseTokenInJwtString(String authentication)
    {
        if(!StringUtils.isHasText(authentication))
            return null;
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(authentication);
        Claim uuid = decodedJWT.getClaim(Constants.LOGIN_USER_KEY);
        String token = Constants.LOGIN_USER_KEY + ": " + uuid.asString();
        return token;
    }

    public SuccessfullyLoginUser getSuccessfullyLoginUser(String redisKey)
    {
        SuccessfullyLoginUser successfullyLoginUser = redisCache.getCacheObject(redisKey);
        return successfullyLoginUser;
    }


}
