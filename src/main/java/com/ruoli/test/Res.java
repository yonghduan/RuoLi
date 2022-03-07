package com.ruoli.test;

import com.ruoli.config.RuoLiConfig;

public class Res extends RuoLiConfig
{
    private Integer code;
    private String message;

    public Res(Integer code,String message)
    {
        this.code = code;
        this.message = message;
    }
}
