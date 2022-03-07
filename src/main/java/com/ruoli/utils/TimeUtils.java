package com.ruoli.utils;

import java.sql.Time;
import java.util.Date;

public class TimeUtils
{
    public static java.sql.Time getSqlTime()
    {
        Date date = new Date();
        Time time = new Time(date.getTime());
        return time;
    }
}
