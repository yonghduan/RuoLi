package com.ruoli.common.core;

import org.springframework.stereotype.Component;

import java.util.HashMap;


public class AjaxResult extends HashMap<String,Object>
{
    private static final String CODE_KEY = "code";
    private static final String MSG_KEY = "message";

    public AjaxResult(Integer code,String message)
    {
        super.put(CODE_KEY,code);
        super.put(MSG_KEY,message);
    }

    public AjaxResult(){}

    public void setSuccess()
    {
        super.put(CODE_KEY,200);
        super.put(MSG_KEY,"successfully");
    }

    public void setError()
    {
        super.put(CODE_KEY,1);
        super.put(MSG_KEY,"error");
    }

    public void setError(String message)
    {
        super.put(CODE_KEY,1);
        super.put(MSG_KEY,message);
    }


    public static AjaxResult success()
    {
        AjaxResult ajaxResult = new AjaxResult(200,"successfully");
        return ajaxResult;
    }

    public static AjaxResult error()
    {
        AjaxResult ajaxResult = new AjaxResult(1,"error");
        return ajaxResult;
    }

    public static AjaxResult error(final String message)
    {
        AjaxResult ajaxResult = new AjaxResult(1,message);
        return ajaxResult;
    }

    public static AjaxResult error(int code,String message)
    {
        AjaxResult ajaxResult = new AjaxResult(code,message);
        return ajaxResult;
    }

}
