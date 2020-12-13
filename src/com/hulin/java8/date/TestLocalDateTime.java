package com.hulin.java8.date;

import java.time.*;

/**
 * 测试LocalDateTime用法 和LocalDate、LocalTime使用方法一致
 */
public class TestLocalDateTime {
    public void test(){

        //获取当前时间
        LocalDateTime ldt=LocalDateTime.now();
        System.out.println(ldt);
        //指定时间
        LocalDateTime ldt1=  LocalDateTime.of(2015,10,29,13,22,33);

        /**
         *时间运算：可在时间对象基础上加减年份、月份、日、时、分和秒
         *          LocalDate对象则是加减年、月、日
         * 注意：在原时间对象上不管做什么操作 都返回新的日期对象
         */
        LocalDateTime ldt2=ldt.plusYears(2);
        LocalDateTime ldt3=ldt.minusMonths(2);

        //获取该日期上的分量
        ldt.getYear(); //返回年 int
        ldt.getMonth(); //返回对象Month
        ldt.getMonthValue(); //直接返回月份值 也可用上述返回的Month对象get到月份值
        ldt.getDayOfMonth(); //返回日
        ldt.getHour(); //小时
        ldt.getMinute(); //分钟
    }

    public void test2(){
        /**
         * Instant对象：时间戳对象(以Unix元年：1970年1月1日00:00:00开始算的毫秒值)
         */
        //默认获取UTC时区 即以英国格林尼治时间来算 返回的是日期和时区
        Instant instant=Instant.now();
        //使用atOffset方法增加偏移量 即和UTC时区隔8小时
       OffsetDateTime odt= instant.atOffset(ZoneOffset.ofHours(8));
       //返回时间戳
       instant.toEpochMilli();
       //在Unix元年时间基础上增加1000秒
       Instant.ofEpochSecond(1000);
    }

    public void test3(){
        /**
         * Duration 计算两个时间之间的间隔
         * Period 计算两个日期之间的间隔
         */
        Instant instant=Instant.now();
        Instant instant1=Instant.now();
        Duration duration =Duration.between(instant,instant1);
        duration.toMillis(); //毫秒差

        LocalDate ld=LocalDate.of(2015,1,1);
        LocalDate ld2=LocalDate.now();
        Period period=Period.between(ld,ld2);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());

    }
}
