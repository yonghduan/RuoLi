package com.ruoli.service.datasource;

import com.ruoli.entity.common.MenuReturnBody;
import com.ruoli.entity.datasource.SysMenuTable;

import java.util.List;
import java.util.Set;

public interface ISysMenuService
{
    Set<String> selectMenuPermsByUserId(Long UserId);

    List<SysMenuTable> selectMenuTreeByUserId(Long userId);

    List<MenuReturnBody> buildMenus(List<SysMenuTable> menuList);
}
