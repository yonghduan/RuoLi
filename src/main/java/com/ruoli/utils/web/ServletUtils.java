package com.ruoli.utils.web;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    /**
     * make json string to client*/
    public static String renderString(HttpServletResponse response,String string)
    {
        try
        {
           response.setStatus(200);
           response.setContentType("application/json");
           response.setCharacterEncoding("utf-8");
           response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
