package com.ruoli.common.security.filter;

import com.ruoli.entity.common.SuccessfullyLoginUser;
import com.ruoli.service.system.IJwtTokenService;
import com.ruoli.utils.web.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    @Autowired
    private IJwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    {
        SuccessfullyLoginUser successfullyLoginUser = jwtTokenService.getSuccessfullyLoginUser(request);
        if(successfullyLoginUser != null && SecurityUtils.getAuthentication() == null)
        {
            jwtTokenService.refreshExpireTime(successfullyLoginUser);

        }
    }
}
