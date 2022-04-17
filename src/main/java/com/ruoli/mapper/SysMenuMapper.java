package com.ruoli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoli.entity.datasource.SysMenuTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysMenuMapper extends BaseMapper<SysMenuTable>
{
    List<String> selectMenuPermByUserId(@Param("userId") Long userId);
}
