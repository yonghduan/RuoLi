package com.ruoli.controller.system;

import com.ruoli.common.core.AjaxResult;
import com.ruoli.common.exceptions.ServiceException;
import com.ruoli.controller.common.BaseController;
import com.ruoli.entity.common.LoginUserInfo;
import com.ruoli.entity.common.SuccessfullyLoginUser;
import com.ruoli.entity.datasource.SystemUserTable;
import com.ruoli.service.datasource.IDeptService;
import com.ruoli.service.datasource.ISysRoleService;
import com.ruoli.service.system.IUserService;
import com.ruoli.utils.StringUtils;
import com.ruoli.utils.web.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/system/user/profile")
public class UserProfileController extends BaseController
{
    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public AjaxResult getUserProfile()
    {
        AjaxResult ajaxResult = AjaxResult.success();
        /**
         * need to set context holder to get SuccessfullyLoginUser from redis*/
        Authentication authentication = SecurityUtils.getAuthentication();
        SuccessfullyLoginUser successfullyLoginUser = getSuccessfullyLoginUser();
        ajaxResult.put("username: ",successfullyLoginUser.getUsername());

        ajaxResult.put("sex: ", successfullyLoginUser.getSex() ? "boy" : "girl");
        ajaxResult.put("telephoneNumber: ",successfullyLoginUser.getTelephoneNumber());

        Set<String> roleSet = sysRoleService.selectRoleByUserId(successfullyLoginUser.getUserId());
        ajaxResult.put("role",roleSet);
        ajaxResult.put("post",userService.selectPostByUsername(successfullyLoginUser.getUsername()));
        ajaxResult.put("dept",deptService.selectDeptByUsername(successfullyLoginUser.getUsername()));


        return ajaxResult;
    }

    @PutMapping
    public AjaxResult updateProfile(@RequestBody LoginUserInfo loginUserInfo)
    {
        /**
         * test if modified username and telephone is unique
         * */
        String usernameModified = loginUserInfo.getUsername();
        String originUsername = getSuccessfullyLoginUser().getUsername();
        if(!originUsername.equals(usernameModified))
        {
            if(StringUtils.isNotEmpty(usernameModified) && !userService.checkIfFieldUnique("username",usernameModified))
            {
                return AjaxResult.error("用户名:" + usernameModified + "为空或者该用户名已存在");
            }
        }

        String modifiedTelephone = loginUserInfo.getTelephoneNumber();
        String originTelephone  = getSuccessfullyLoginUser().getTelephoneNumber();
        if(StringUtils.isNotEmpty(modifiedTelephone) && !modifiedTelephone.equals(originTelephone)
                && userService.checkIfFieldUnique("telephone_number",modifiedTelephone))
        {
            return AjaxResult.error("电话：" + modifiedTelephone + "已经存在");
        }

        SystemUserTable systemUserTable = new SystemUserTable();
        systemUserTable.setUserId(getSuccessfullyLoginUser().getUserId());
        systemUserTable.setUsername(usernameModified);
        systemUserTable.setTelephoneNumber(modifiedTelephone);
        systemUserTable.setSex(loginUserInfo.getSex());
        int isUpdateSuccess = userService.updateUserProfile(systemUserTable);

        /**
         * update cache*/
       if(isUpdateSuccess >= 0)
       {
           SuccessfullyLoginUser successfullyLoginUser = getSuccessfullyLoginUser();
           successfullyLoginUser.setUsername(usernameModified);
           successfullyLoginUser.setTelephoneNumber(modifiedTelephone);
           successfullyLoginUser.setSex(loginUserInfo.getSex());

           return AjaxResult.success("更新用户信息成功");
       }

       return AjaxResult.error("更新用户信息异常，请联系管理员");
    }

    @PutMapping("updatePwd")
    public AjaxResult updatePwd(@RequestBody Map<String,Object> mapParam )
    {
        String originPwd = getSuccessfullyLoginUser().getPassword();
        String oldPwd = (String)mapParam.get("oldPwd");
        if(!bCryptPasswordEncoder.matches(oldPwd,originPwd))
        {
            return AjaxResult.error("旧密码错误");
        }

        int isSuccess = userService.updateUserPwd(getSuccessfullyLoginUser().getUserId(),(String)mapParam.get("newPwd"));
        if(isSuccess >= 0)
        {
            getSuccessfullyLoginUser().setPassword(bCryptPasswordEncoder.encode((String)mapParam.get("newPwd")));
            return AjaxResult.success("更新密码成功");
        }

        return AjaxResult.error("更新密码异常，请联系管理员");
    }
}
