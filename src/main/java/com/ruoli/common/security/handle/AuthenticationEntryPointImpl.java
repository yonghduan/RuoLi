package com.ruoli.common.security.handle;

import com.alibaba.fastjson.JSON;
import com.ruoli.common.constant.HttpStatus;
import com.ruoli.common.core.AjaxResult;
import com.ruoli.utils.web.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint
{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
    {
        int code = HttpStatus.UNAUTHORIZED;
        String message = "请求访问：" + request.getRequestURI() + ",认证失败，无法访问系统资源";
        String jsonString = JSON.toJSONString(AjaxResult.error(code,message));
        ServletUtils.renderString(response, jsonString);
    }

}
