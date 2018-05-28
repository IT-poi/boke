package com.cuit.boke.utils;

import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public final static DateTimeFormatter FORMATTER_DEF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public final static DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");


}
