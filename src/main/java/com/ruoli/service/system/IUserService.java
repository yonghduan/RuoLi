package com.ruoli.service.system;

import com.ruoli.entity.datasource.SystemUserTable;

public interface IUserService
{
    String selectPostByUsername(String username);

    boolean checkIfFieldUnique(String columnName,String username);

    void updateUserProfile(SystemUserTable systemUserTable);
}
