package com.ruoli.entity.common;

public class LoginUserBody
{
    private String user;
    private String password;
    private String uuid;
    private String code;

    public LoginUserBody(final String user,final String password,final String uuid,final String code)
    {
        this.user = user;
        this.password = password;
        this.uuid = uuid;
        this.code = code;
    }

    public void setUser(final String user)
    {
        this.user = user;
    }

    public void setPassword(final String password)
    {
        this.password = password;
    }

    public void setUuid(final String uuid)
    {
        this.uuid = uuid;
    }

    public void setCode(final String code)
    {
        this.code = code;
    }

    public String getUser(){return user;}
    public String getPassword(){return password;}
    public String getCode(){return code;}
    public String getUuid(){return uuid;}
}
