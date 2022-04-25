package com.ruoli.service.datasource;

import com.ruoli.entity.datasource.SysDeptTable;

import java.util.List;

public interface IDeptService
{
    List<SysDeptTable> selectDeptByUsername(String username);
}
