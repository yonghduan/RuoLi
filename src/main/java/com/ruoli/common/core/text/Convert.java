package com.ruoli.common.core.text;

public class Convert
{
    public static String toStr(Object value,String defaultValue)
    {
        if(value == null)
            return defaultValue;

        if(value instanceof String)
            return (String)value;

        return value.toString();
    }

    public static String toStr(Object value)
    {
        return toStr(value, null);
    }
}
