package com.ruoli.service.datasource.impl;

import com.ruoli.entity.datasource.SysMenuTable;
import com.ruoli.mapper.SysMenuMapper;
import com.ruoli.service.datasource.ISysMenuService;
import com.ruoli.utils.StringUtils;
import com.ruoli.utils.web.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysMenuService implements ISysMenuService
{
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public Set<String>  selectMenuPermsByUserId(Long userId)
    {
        List<String> permsList = sysMenuMapper.selectMenuPermByUserId(userId);
        if(permsList == null)
            return null;

        Set<String> permsSet = new HashSet<>();

        for(String perm : permsList)
        {
            if(StringUtils.isHasText(perm))
            {
                permsSet.add(perm);
            }
        }
        return permsSet;
    }

    @Override
    public List<SysMenuTable> selectMenuTreeByUserId(Long userId)
    {
        List<SysMenuTable> menuList = null;
        if(SecurityUtils.isAdmin(userId))
        {
            menuList = sysMenuMapper.selectMenuTreeAll();
        }
        else
        {
            menuList = sysMenuMapper.selectMenuTreeByUserId(userId);
        }
        return null;
    }
}
