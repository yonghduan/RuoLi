package com.ruoli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoli.entity.datasource.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface SysMenuMapper extends BaseMapper<SysMenu>
{
    List<String> selectMenuPermByUserId(@Param("userId") Long userId);
}
