package com.ruoli.utils;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

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
