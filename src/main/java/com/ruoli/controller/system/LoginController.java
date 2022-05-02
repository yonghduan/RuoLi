package com.ruoli.controller.system;

import com.ruoli.common.core.AjaxResult;
import com.ruoli.common.exceptions.login.CaptchaErrorException;
import com.ruoli.common.exceptions.login.CaptchaExpireException;
import com.ruoli.common.redis.RedisCache;
import com.ruoli.entity.common.LoginUserBody;
import com.ruoli.entity.common.LoginUserInfo;
import com.ruoli.entity.common.SuccessfullyLoginUser;
import com.ruoli.entity.datasource.SysMenuTable;
import com.ruoli.service.datasource.ISysMenuService;
import com.ruoli.service.datasource.ISysRoleService;
import com.ruoli.service.system.ILoginService;
import com.ruoli.utils.web.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping
public class LoginController
{
    @Autowired
    private ILoginService loginService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysMenuService sysMenuService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginUserBody loginUserBody)
    {
       String token = loginService.handleLogin(loginUserBody);
       AjaxResult ajaxResult = AjaxResult.success();
       ajaxResult.put("token",token);
       return ajaxResult;
    }

    @GetMapping("/getInfo")
    public AjaxResult getInfo()
    {
        SuccessfullyLoginUser successfullyLoginUser = SecurityUtils.getSuccessfullyLoginUser();
        AjaxResult ajaxResult = AjaxResult.success();
        LoginUserInfo loginUserInfo = new LoginUserInfo(successfullyLoginUser);
        ajaxResult.put("user",loginUserInfo);

        Set<String> roleSet = roleService.selectRoleByUserId(successfullyLoginUser.getUserId());
        ajaxResult.put("role",roleSet);
        return ajaxResult;
    }

    @GetMapping("/getRouters")
    public AjaxResult getRouters()
    {
        SuccessfullyLoginUser successfullyLoginUser = SecurityUtils.getSuccessfullyLoginUser();
        AjaxResult ajaxResult = AjaxResult.success();
        List<SysMenuTable> menuList = sysMenuService.selectMenuTreeByUserId(successfullyLoginUser.getUserId());
        ajaxResult.put("menu",sysMenuService.buildMenus(menuList));
        return ajaxResult;
    }




}
