package com.ruoli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoli.entity.datasource.SysPostTable;

import java.util.List;

public interface SysPostMapper extends BaseMapper<SysPostTable>
{
    List<SysPostTable> selectPostByUsername(String username);
}
