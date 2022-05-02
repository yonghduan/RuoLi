package com.ruoli.service.system.impl;

import com.ruoli.entity.common.SuccessfullyLoginUser;
import com.ruoli.mapper.SysMenuMapper;
import com.ruoli.service.datasource.ISysMenuService;
import com.ruoli.service.system.IPermissionService;
import com.ruoli.utils.StringUtils;
import com.ruoli.utils.web.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("ss")
public class PermissionService implements IPermissionService
{
    public final static String ALL_PERMISSION = "*:*:*";

    @Autowired
    private ISysMenuService sysMenuService;


    @Override
    public void setAuthoritiesForUser(SuccessfullyLoginUser successfullyLoginUser)
    {
        Long userId = successfullyLoginUser.getUserId();

        Set<String> authorities = new HashSet<>();
        if(successfullyLoginUser.isAdmin(userId))
        {
            authorities.add("*:*:*");
        }
        else
        {
            authorities.addAll(sysMenuService.selectMenuPermsByUserId(userId));
        }
        successfullyLoginUser.setAuthorities(authorities);

    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

    public static boolean hasPerm(String perm)
    {
        if(StringUtils.isEmpty(perm))
            return false;

        SuccessfullyLoginUser successfullyLoginUser = SecurityUtils.getSuccessfullyLoginUser();

        if(successfullyLoginUser == null || CollectionUtils.isEmpty(successfullyLoginUser.getAuthorities()))
            return false;

        Set<String> permSet = successfullyLoginUser.getAuthorities();
        return isHasPerms(perm,permSet);
    }

    public static boolean isHasPerms(String perm,Set<String> permSet)
    {
        return permSet.contains(ALL_PERMISSION) || permSet.contains(StringUtils.trim(perm));
    }
}
