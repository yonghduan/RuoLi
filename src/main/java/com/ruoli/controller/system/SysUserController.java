package com.ruoli.controller.system;

import com.ruoli.common.core.AjaxResult;
import com.ruoli.entity.datasource.SystemUserTable;
import com.ruoli.service.system.IUserService;
import com.ruoli.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/system/user")
public class SysUserController
{
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('system:user:list')")
    public AjaxResult list()
    {
        return AjaxResult.success();
    }

    @GetMapping("/export")
    @PreAuthorize("@ss.hasPerm('system:user:export')")
    public void export(HttpServletResponse response)
    {
        List<SystemUserTable> userList = userService.selectUserList();
        ExcelUtil<SystemUserTable> util = new ExcelUtil<>(SystemUserTable.class);
        util.exportExcel(response,userList,"用户数据");
    }


}
