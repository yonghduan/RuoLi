package com.ruoli.service.datasource.impl;

import com.ruoli.entity.common.MenuReturnBody;
import com.ruoli.entity.datasource.SysMenuTable;
import com.ruoli.mapper.SysMenuMapper;
import com.ruoli.service.datasource.ISysMenuService;
import com.ruoli.utils.StringUtils;
import com.ruoli.utils.web.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
        return getChildPerms(menuList,0L);
    }

    public List<SysMenuTable> getChildPerms(List<SysMenuTable> list,long parentId)
    {
        List<SysMenuTable> returnList = new ArrayList<SysMenuTable>();
        for(Iterator<SysMenuTable> iterator = list.iterator();iterator.hasNext();)
        {
            SysMenuTable sysMenu = iterator.next();
            if(sysMenu.getParentId().longValue() == parentId)
            {
                recursionFn(list,sysMenu);
                returnList.add(sysMenu);
            }
        }
        return returnList;
    }

    private void recursionFn(List<SysMenuTable> list,SysMenuTable singleMenu)
    {
        List<SysMenuTable> childList = getChildMenuList(list,singleMenu);
        singleMenu.setChildren(childList);
        for(SysMenuTable sm : childList)
        {
            if(isHasChild(list,sm))
            {
                recursionFn(list,sm);
            }
        }
    }

    private List<SysMenuTable> getChildMenuList(List<SysMenuTable> menuList,SysMenuTable singleMenu)
    {
        List<SysMenuTable> childList = new ArrayList<>();
        Iterator<SysMenuTable> iterator = menuList.iterator();
        while(iterator.hasNext())
        {
            SysMenuTable sysMenu = iterator.next();
            if(sysMenu.getParentId().longValue() == singleMenu.getMenuId().longValue() )
                childList.add(sysMenu);
        }
        return childList;
    }

    private boolean isHasChild(List<SysMenuTable> menuList,SysMenuTable singleMenu)
    {
        return getChildMenuList(menuList,singleMenu).size() > 0;
    }

    @Override
    public List<MenuReturnBody> buildMenus(List<SysMenuTable> menuList)
    {
        List<MenuReturnBody> returnMenu = new ArrayList<>();
        for(Iterator<SysMenuTable> iterator = menuList.iterator();iterator.hasNext();)
        {
            SysMenuTable singleMenu = iterator.next();
            returnMenu.add(new MenuReturnBody(singleMenu));
        }
        return returnMenu;
    }
}
