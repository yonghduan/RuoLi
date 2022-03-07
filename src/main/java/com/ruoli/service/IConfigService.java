package com.ruoli.service;

import com.ruoli.enums.CaptchaType;

public interface IConfigService
{
    public boolean getCaptchaButton();

    public CaptchaType getCaptchaType();
}
