package com.ruoli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface SysRoleMapper extends BaseMapper
{
    List<String> selectRoleByUserId(Long userId);
}
