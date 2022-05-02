package com.ruoli.entity.common;

import com.ruoli.entity.datasource.SystemUserTable;
import com.ruoli.utils.TimeUtils;
import com.ruoli.utils.web.ServletUtils;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

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
    private String password;

    /**
     * save information of user in login state*/
    private DateTime loginTime;
    private DateTime expireTime;
    private Set<String> authorities;

    public SuccessfullyLoginUser(SystemUserTable systemUserTable)
    {
        this.userId = systemUserTable.getUserId();
        this.username = systemUserTable.getUsername();
        this.sex = systemUserTable.getSex();
        this.telephoneNumber = systemUserTable.getTelephoneNumber();

        this.browser = ServletUtils.getBrowser();
        this.operateSystem = ServletUtils.getOS();
        this.authorities = null;
        this.password = systemUserTable.getPassword();

        loginTime = new DateTime();
    }

    public boolean getSex()
    {
        return sex;
    }

    public static SuccessfullyLoginUser createSuccessfullyLoginUser(SystemUserTable systemUserTable)
    {
        return new SuccessfullyLoginUser(systemUserTable);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && userId == 1L;
    }
}
