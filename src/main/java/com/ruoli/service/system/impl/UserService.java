package com.ruoli.service.system.impl;

import com.ruoli.entity.datasource.SysPostTable;
import com.ruoli.mapper.SysPostMapper;
import com.ruoli.service.system.IUserService;
import com.ruoli.utils.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

public class UserService implements IUserService
{
    @Resource
    private SysPostMapper sysPostMapper;

    @Override
    public String selectPostByUsername(String username)
    {
        List<SysPostTable> postList = sysPostMapper.selectPostByUsername(username);
        if(CollectionUtils.isEmpty(postList))
        {
            return StringUtils.EMPTY;
        }

        
    }
}
