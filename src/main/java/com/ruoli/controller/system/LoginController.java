package com.ruoli.controller.system;

import com.ruoli.common.core.AjaxResult;
import com.ruoli.common.exceptions.login.CaptchaErrorException;
import com.ruoli.common.exceptions.login.CaptchaExpireException;
import com.ruoli.common.redis.RedisCache;
import com.ruoli.entity.common.LoginUserBody;
import com.ruoli.service.system.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController
{
    @Autowired
    private ILoginService loginService;

    @PostMapping
    public AjaxResult login(@RequestBody LoginUserBody loginUserBody)
    {
       loginService.handleLogin(loginUserBody);
       return AjaxResult.success();
    }




}
