package com.ruoli.common.exceptions.login;

import com.ruoli.common.exceptions.ServiceException;
import com.ruoli.enums.ServiceExceptionType;

public class CaptchaErrorException extends ServiceException
{
    public CaptchaErrorException()
    {
        super(ServiceExceptionType.CAPTCHA_ERROR);
    }

    @Override
    public String getMessage()
    {
        return "captcha error";
    }
}
