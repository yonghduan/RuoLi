package com.ruoli.service.datasource;

import java.util.List;
import java.util.Set;

public interface ISysRoleService
{
    Set<String> selectRoleByUserId(Long userId);
}
