package com.ruoli.entity.common;

import com.ruoli.entity.datasource.SysMenuTable;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class MenuReturnBody
{
    private String name;

    private String path;

    private Boolean hidden;

    private String redirect;

    private String component;

    private String query;

    private Boolean alwaysShow;

    private MetaVo metaVo;

    private List<MenuReturnBody> children;

    public MenuReturnBody(SysMenuTable sysMenuTable)
    {
        this.name = sysMenuTable.getMenuName();
        this.path = sysMenuTable.getPath();
        if(sysMenuTable.getVisible().charValue() == '0')
            this.hidden = false;
        else
            this.hidden = true;
        this.component = sysMenuTable.getComponent();
        this.query = sysMenuTable.getQuery();
        this.alwaysShow = true;
        this.metaVo = new MetaVo(sysMenuTable.getIcon(),sysMenuTable.getIsCache());
        if(sysMenuTable.getChildren() != null)
            children = new ArrayList<>();
        else
            children = null;

        for(Iterator<SysMenuTable> iterator = sysMenuTable.getChildren().iterator();iterator.hasNext();)
        {
            SysMenuTable singleMenu = iterator.next();
            children.add(new MenuReturnBody(singleMenu));
        }
    }
}
