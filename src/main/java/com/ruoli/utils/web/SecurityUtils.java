package com.ruoli.utils.web;

import com.ruoli.entity.common.SuccessfullyLoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils
{
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static SuccessfullyLoginUser getSuccessfullyLoginUser()
    {
        return (SuccessfullyLoginUser) getAuthentication().getPrincipal();
    }

    public static boolean isAdmin(Long userId)
    {
        return SuccessfullyLoginUser.isAdmin(userId);
    }
}
