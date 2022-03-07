package com.ruoli.common.exceptions.login;

import com.ruoli.common.exceptions.ServiceException;
import com.ruoli.enums.ServiceExceptionType;

public class CaptchaExpireException extends ServiceException
{
    public CaptchaExpireException()
    {
        super(ServiceExceptionType.CAPTCHA_EXPIRE);
    }

    @Override
    public String getMessage()
    {
        return "captcha expired";
    }
}
