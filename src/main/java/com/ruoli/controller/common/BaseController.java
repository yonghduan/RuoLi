package com.ruoli.controller.common;

import com.ruoli.entity.common.SuccessfullyLoginUser;
import com.ruoli.utils.web.SecurityUtils;

public class BaseController
{
    public SuccessfullyLoginUser getSuccessfullyLoginUser()
    {
        return SecurityUtils.getSuccessfullyLoginUser();
    }
}
