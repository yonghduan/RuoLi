package com.ruoli.entity.datasource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

@Data
@TableName("system_user")
public class SystemUserTable extends BaseEntity implements UserDetails
{
    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;
    /** unique restrict in sql language*/
    private String username;
    private String password;
    private boolean  sex;
    private String telephoneNumber;
    private String roleId;
    private boolean isExpired;
    private boolean isLocked;
    private boolean isCredentialExpired;
    private boolean isEnabled;

    public boolean getSex()
    {
        return sex;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities ()
    {
        return null;
    }
    @Override
    public String getUsername()
    {
        return username;
    }
    @Override
    public String getPassword()
    {
        return password;
    }
    @Override
    public boolean isAccountNonExpired()
    {
        return !isExpired;
    }
    @Override
    public boolean isAccountNonLocked()
    {
        return !isLocked;
    }
    @Override
    public boolean isCredentialsNonExpired()
    {
        return !isCredentialExpired;
    }
    @Override
    public boolean isEnabled()
    {
        return isEnabled;
    }


}
