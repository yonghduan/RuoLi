package com.ruoli.entity.common;

import com.ruoli.entity.datasource.SystemUserTable;
import com.ruoli.utils.TimeUtils;
import com.ruoli.utils.web.ServletUtils;
import lombok.Data;

import java.util.Collection;

@Data
public class SuccessfullyLoginUser
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
    private Collection<?> authorities;

    public SuccessfullyLoginUser(SystemUserTable systemUserTable)
    {
        this.userId = systemUserTable.getUserId();
        this.username = systemUserTable.getUsername();
        this.sex = systemUserTable.getSex();
        this.telephoneNumber = systemUserTable.getTelephoneNumber();

        this.browser = ServletUtils.getBrowser();
        this.operateSystem = ServletUtils.getOS();

        loginTime = TimeUtils.getSqlTime();
    }

    public static SuccessfullyLoginUser createSuccessfullyLoginUser(SystemUserTable systemUserTable)
    {
        return new SuccessfullyLoginUser(systemUserTable);
    }
}
