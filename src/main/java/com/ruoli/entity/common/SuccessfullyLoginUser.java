package com.ruoli.entity.common;

import com.ruoli.entity.datasource.SystemUserTable;
import com.ruoli.utils.TimeUtils;
import com.ruoli.utils.web.ServletUtils;
import lombok.Data;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Collection;

@Data
public class SuccessfullyLoginUser implements Serializable
{
    private static final long serialVersionID = 1L;

    private Long userId;
    private String username;
    private boolean sex;
    private String telephoneNumber;
    private String browser;
    private String operateSystem;
    private String redisKey;

    /**
     * save information of user in login state*/
    private DateTime loginTime;
    private DateTime expireTime;
    private Collection<?> authorities;

    public SuccessfullyLoginUser(SystemUserTable systemUserTable)
    {
        this.userId = systemUserTable.getUserId();
        this.username = systemUserTable.getUsername();
        this.sex = systemUserTable.getSex();
        this.telephoneNumber = systemUserTable.getTelephoneNumber();

        this.browser = ServletUtils.getBrowser();
        this.operateSystem = ServletUtils.getOS();

        loginTime = new DateTime();
    }

    public static SuccessfullyLoginUser createSuccessfullyLoginUser(SystemUserTable systemUserTable)
    {
        return new SuccessfullyLoginUser(systemUserTable);
    }
}
