package com.ruoli.controller.system;

import com.ruoli.common.core.AjaxResult;
import com.ruoli.entity.common.SuccessfullyLoginUser;
import com.ruoli.service.datasource.ISysRoleService;
import com.ruoli.utils.web.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/userProfile")
public class UserProfileController
{
    @Autowired
    private ISysRoleService sysRoleService;

    @GetMapping
    public AjaxResult getUserProfile()
    {
        AjaxResult ajaxResult = AjaxResult.success();
        /**
         * need to set context holder to get SuccessfullyLoginUser from redis*/
        Authentication authentication = SecurityUtils.getAuthentication();
        SuccessfullyLoginUser successfullyLoginUser = (SuccessfullyLoginUser) authentication.getPrincipal();
        ajaxResult.put("username: ",successfullyLoginUser.getUsername());

        ajaxResult.put("sex: ", successfullyLoginUser.getSex() ? "boy" : "girl");
        ajaxResult.put("telephoneNumber: ",successfullyLoginUser.getTelephoneNumber());

        Set<String> roleSet = sysRoleService.selectRoleByUserId(successfullyLoginUser.getUserId());
        ajaxResult.put("role",roleSet);

        return ajaxResult;
    }
}
