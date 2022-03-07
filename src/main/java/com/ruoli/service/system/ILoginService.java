package com.ruoli.service.system;

import com.ruoli.entity.common.LoginUserBody;

public interface ILoginService
{
    public String handleLogin(LoginUserBody loginUserBody);
}
