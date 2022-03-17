package com.ruoli.service.system.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{
    public UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException
    {
        /**
         * 通过用户名查找数据库，如果不存在抛出异常，
         * 如果存在，查看用户其他信息*/

    }
}
