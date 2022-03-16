package com.ruoli.utils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils
{
    public static String getSqlTime()
    {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(date);
        return currentTime;
    }
}
