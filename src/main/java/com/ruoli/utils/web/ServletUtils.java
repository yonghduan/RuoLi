package com.ruoli.utils.web;

import eu.bitwalker.useragentutils.UserAgent;
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

    public static UserAgent getUserAgent()
    {
        HttpServletRequest httpServletRequest = getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(httpServletRequest.getHeader("User-Agent"));
        return userAgent;
    }

    public static String getBrowser()
    {
        return getUserAgent().getBrowser().getName();
    }

    public static String getOS()
    {
        return getUserAgent().getOperatingSystem().getName();
    }

}
