package com.ruoli.service.datasource.impl;

import com.ruoli.entity.datasource.SysDeptTable;
import com.ruoli.mapper.SysDeptMapper;
import com.ruoli.service.datasource.IDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptService implements IDeptService
{
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDeptTable> selectDeptByUsername(String username)
    {
        List<SysDeptTable> deptList = sysDeptMapper.selectDeptByUsername(username);
        return deptList;
    }
}
