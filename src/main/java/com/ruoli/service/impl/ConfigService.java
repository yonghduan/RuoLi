package com.ruoli.service.impl;

import com.ruoli.enums.CaptchaType;
import com.ruoli.service.IConfigService;
import com.ruoli.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigService implements IConfigService
{
    private String captchaButton;
    private String captchaType;

    @Value("${ruoli.captchaButton}")
    private void setCaptchaButton(final String captchaButton)
    {
        if(StringUtils.isHasText(captchaButton))
        {
            if(captchaButton.equalsIgnoreCase("off"))
            {
                this.captchaButton = "off";
            }
            else if(captchaButton.equalsIgnoreCase("on"))
            {}
            else
            {
                System.out.println("captchaButton is not rightly configured in application.yml," +
                        "it is set to on by default");
            }
        }
    }

    @Value("${ruoli.captchaType}")
    private void setCaptchaType(final String captchaType)
    {
        if(StringUtils.isHasText(captchaType))
        {
            if(captchaType.equalsIgnoreCase("math"))
            {}
            else if(captchaType.equalsIgnoreCase("char"))
            {
                this.captchaType = "char";
            }
            else
            {
                System.out.println("captchaType is not rightly configured in application.yml,it is" +
                        "set to math by default.");
            }
        }
    }

    public ConfigService()
    {
        captchaButton = "on";
        captchaType = "math";
    }

    @Override
    public boolean getCaptchaButton()
    {
        if(captchaButton.equals("on"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public CaptchaType getCaptchaType()
    {
        if(captchaType.equals("math"))
        {
            return CaptchaType.Math;
        }
        else
        {
            return CaptchaType.Char;
        }
    }
}
