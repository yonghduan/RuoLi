package com.ruoli.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoli.entity.datasource.SysPostTable;
import com.ruoli.entity.datasource.SystemUserTable;
import com.ruoli.mapper.SysPostMapper;
import com.ruoli.mapper.SystemUserMapper;
import com.ruoli.service.system.IUserService;
import com.ruoli.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService
{
    @Resource
    private SysPostMapper sysPostMapper;

    @Resource
    private SystemUserMapper systemUserMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String selectPostByUsername(String username)
    {
        List<SysPostTable> postList = sysPostMapper.selectPostByUsername(username);
        if(CollectionUtils.isEmpty(postList))
        {
            return StringUtils.EMPTY;
        }
        return postList.stream().map(SysPostTable::getPostName).collect(Collectors.joining(","));
        
    }

    @Override
    public boolean checkIfFieldUnique(String columnName,String fieldValue)
    {
        QueryWrapper<SystemUserTable> queryWrapper = new QueryWrapper<SystemUserTable>();
        queryWrapper.eq(columnName,fieldValue);
        queryWrapper.last("limit 1");
        int fieldCount = systemUserMapper.selectCount(queryWrapper);
        if(fieldCount <= 0)
            return true;
        else
            return false;
    }

    @Override
    public int updateUserProfile(SystemUserTable systemUserTable)
    {
        String modifiedUsername = systemUserTable.getUsername();
        boolean sex = systemUserTable.getSex();
        String modifiedTelephone = systemUserTable.getTelephoneNumber();

      /*  UpdateWrapper<SystemUserTable> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",systemUserTable.getUserId())
                .set("username",modifiedUsername).set("telephone_number",modifiedTelephone)
                .set("sex",sex);
        systemUserMapper.update(null,updateWrapper);*/
        UpdateWrapper<SystemUserTable> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",systemUserTable.getUserId());
        int isUpdateSuccess = systemUserMapper.update(systemUserTable,updateWrapper);
        return isUpdateSuccess;
    }

    @Override
    public int updateUserPwd(double userId,String newPassword)
    {
           String encodedPwd = bCryptPasswordEncoder.encode(newPassword);
           UpdateWrapper<SystemUserTable> updateWrapper = new UpdateWrapper<>();
           updateWrapper.eq("user_id",userId).set("password",encodedPwd);
           int isSuccess = systemUserMapper.update(null,updateWrapper);
           return isSuccess;
    }

    @Override
    public List<SystemUserTable> selectUserList()
    {
        List<SystemUserTable> userList = systemUserMapper.selectList(null);
        return userList;
    }


}
