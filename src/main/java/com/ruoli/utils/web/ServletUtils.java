package com.ruoli.utils.web;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils
{
    public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) requestAttributes;
    }

    public static HttpServletRequest getRequest()
    {
        return getRequestAttributes().getRequest();
    }


}
