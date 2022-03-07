package com.ruoli.controller.system;

import com.ruoli.common.core.AjaxResult;
import com.ruoli.common.exceptions.login.CaptchaErrorException;
import com.ruoli.common.exceptions.login.CaptchaExpireException;
import com.ruoli.common.redis.RedisCache;
import com.ruoli.entity.common.LoginUserBody;
import com.ruoli.service.system.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController
{
    @Autowired
    private ILoginService loginService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginUserBody loginUserBody)
    {
       loginService.handleLogin(loginUserBody);
    }


}
