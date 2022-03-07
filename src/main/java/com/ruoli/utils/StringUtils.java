package com.ruoli.utils;

public class StringUtils
{
    /**
    * 判断字符串是否有内容
    * */
    public static boolean isHasText(final String str)
    {
        return org.springframework.util.StringUtils.hasText(str);
    }
}
