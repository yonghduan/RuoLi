package com.ruoli.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoli.common.exceptions.ServiceException;
import com.ruoli.entity.datasource.SystemUserTable;
import com.ruoli.mapper.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{
    @Resource
    private SystemUserMapper systemUserMapper;
    @Override
    public UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException
    {
        /**
         * 通过用户名查找数据库，如果不存在抛出异常，
         * 如果存在，查看用户其他信息*/
        QueryWrapper<SystemUserTable> queryWrapper = new QueryWrapper<SystemUserTable>().eq("username",var1);
        UserDetails userDetails = systemUserMapper.selectOne(queryWrapper);
        if(userDetails == null)
            throw new ServiceException("user: " + var1 + " not found!");
        else
            return userDetails;
    }
}
