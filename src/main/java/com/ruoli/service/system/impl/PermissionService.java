package com.ruoli.service.system.impl;

import com.ruoli.entity.common.SuccessfullyLoginUser;
import com.ruoli.mapper.SysMenuMapper;
import com.ruoli.service.datasource.ISysMenuService;
import com.ruoli.service.system.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionService implements IPermissionService
{
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

}
