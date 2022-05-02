package com.ruoli.controller.system;

import com.ruoli.common.core.AjaxResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/user")
public class SysUserController
{
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('system:user:list')")
    public AjaxResult list()
    {
        return AjaxResult.success();
    }
}
