package com.ruoli.service.system;

import com.ruoli.entity.common.SuccessfullyLoginUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public interface IPermissionService
{
    void setAuthoritiesForUser(SuccessfullyLoginUser successfullyLoginUser);

    Set<? extends GrantedAuthority> getAuthorities();

    
}
