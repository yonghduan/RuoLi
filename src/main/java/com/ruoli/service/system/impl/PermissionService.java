package com.ruoli.service.system.impl;

import com.ruoli.entity.common.SuccessfullyLoginUser;
import com.ruoli.service.system.IPermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionService implements IPermissionService
{
    @Override
    public void setAuthorities(SuccessfullyLoginUser successfullyLoginUser)
    {
        Long userId = successfullyLoginUser.getUserId();

    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

}
