package com.ruoli.entity.datasource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@TableName("sys_menu")
@Data
public class SysMenuTable extends BaseEntity
{
    @TableId(type = IdType.AUTO)
    private Long menuId;
    private String menuName;
    private Long parentId;
    private Integer orderNum;
    private String path;
    private String component;
    private String query;
    private Integer isFrame;
    private Integer isCache;
    private Character menuType;
    private Character visible;
    private Character status;
    private String perms;
    private String icon;

    private List<SysMenuTable> children = new ArrayList<SysMenuTable>();


}
