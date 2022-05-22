package com.ruoli.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class DateUtils
{
    public static final String parseDateToStr(final String format,final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date toDate(LocalDateTime temporalAccessor)
    {
        ZonedDateTime zonedDateTime = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    public static Date toDate(LocalDate temporalAccessor)
    {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }
}
