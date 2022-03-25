package com.ruoli.controller.system;

import com.ruoli.common.core.AjaxResult;
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
        return ajaxResult;
    }
}
