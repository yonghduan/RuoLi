package com.ruoli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoli.entity.datasource.SysDeptTable;

import java.util.List;

public interface SysDeptMapper extends BaseMapper<SysDeptTable>
{
    List<SysDeptTable> selectDeptByUsername(String username);
}
