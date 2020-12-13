package com.hulin.java8.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * ZoneDate、ZonedTime、ZonedDateTime
 */
public class TestZoneDateTime {
    public void test(){
        //查看支持多少时区
        Set<String> set=ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);

        //为当前时间指定时区 则返回该时区的当前时间
       LocalDateTime ldt= LocalDateTime.now(ZoneId.of("Europe/Tallinn"));

       LocalDateTime ldt1=LocalDateTime.now();
       //为当前时间构建带时区显式 注意该时间并不是该指定时区的时间 而是当前时区时间 只是在末尾加上了该指定时区
       ZonedDateTime zdt=ldt1.atZone(ZoneId.of("Europe/Tallinn"));

       //构建指定时区的当前时间 并加上时区显式
        LocalDateTime ldt2=LocalDateTime.now(ZoneId.of("Europe/Tallinn"));
        ZonedDateTime zdt2=ldt1.atZone(ZoneId.of("Europe/Tallinn"));
    }
}
