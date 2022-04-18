package com.ruoli.service.datasource.impl;

import com.ruoli.mapper.SysRoleMapper;
import com.ruoli.service.datasource.ISysRoleService;
import com.ruoli.utils.StringUtils;
import javafx.scene.effect.SepiaTone;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleService implements ISysRoleService
{
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public Set<String> selectRoleByUserId(Long userId)
    {
        List<String> roleList = sysRoleMapper.selectRoleByUserId(userId);
        Set<String> roleSet = new HashSet<>();
        for(String role : roleList)
        {
            if(StringUtils.isHasText(role))
            {
                roleSet.add(role.trim());
            }
        }
        return roleSet;
    }
}
