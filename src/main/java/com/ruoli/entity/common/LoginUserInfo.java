package com.ruoli.entity.common;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.Set;

@Data
public class LoginUserInfo
{
    private Long userId;
    private String username;
    private boolean sex;
    private String telephoneNumber;
    private String browser;
    private String operateSystem;


    /**
     * save information of user in login state*/
    private String loginTime;
    private String expireTime;
    private Set<String> authorities;

    public LoginUserInfo(){}

    public boolean getSex()
    {
        return sex;
    }

    public LoginUserInfo(SuccessfullyLoginUser successfullyLoginUser)
    {
        this.userId = successfullyLoginUser.getUserId();
        this.username = successfullyLoginUser.getUsername();
        this.sex = successfullyLoginUser.getSex();
        this.telephoneNumber = successfullyLoginUser.getTelephoneNumber();
        this.browser = successfullyLoginUser.getBrowser();
        this.operateSystem = successfullyLoginUser.getOperateSystem();
        this.loginTime = successfullyLoginUser.getLoginTime().toString();
        this.expireTime = successfullyLoginUser.getExpireTime().toString();
        this.authorities = successfullyLoginUser.getAuthorities();
    }
}
