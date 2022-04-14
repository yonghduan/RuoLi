package com.ruoli.controller.system;

import com.ruoli.common.core.AjaxResult;
import com.ruoli.entity.common.SuccessfullyLoginUser;
import com.ruoli.utils.web.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userProfile")
public class UserProfileController
{
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
        return ajaxResult;
    }
}
