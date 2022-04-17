package com.ruoli.service.datasource;

import java.util.Set;

public interface ISysMenuService
{
    Set<String> selectMenuPermsByUserId(Long UserId);
}
