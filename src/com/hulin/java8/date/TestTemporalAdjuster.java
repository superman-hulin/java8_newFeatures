package com.hulin.java8.date;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * 日期的操纵
 * TemporalAdjuster：接口，时间校正器
 * 有时我们可能需要获取例如：将日期调整到“下个周日”等操作
 * TemporalAdjusters:该类通过静态方法提供了大量的常用TemporalAdjuster的实现
 *
 */
public class TestTemporalAdjuster {
    public void test(){
        //当前时间
        LocalDateTime ldt=LocalDateTime.now();
        //将当前时间中的日改为10
        LocalDateTime ldt1=ldt.withDayOfMonth(10);
        /**
         * 该方法的参数是时间校正器(函数式接口)
         * TemporalAdjusters类中的静态方法都是返回一个TemporalAdjuster接口的具体实现
         * 该具体实现是以Lambda表达式形式
         * 将Lambda表达式作为参数传入ldt.with方法 在该方法中实际调用该Lambda表达式
         * 在当前时间上的下一个周日
         */
        LocalDateTime ldt2=ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        //自定义TemporalAdjuster的实现:下一个工作日
      LocalDateTime ldt3=  ldt.with((temporal)->{
            LocalDateTime localDateTime=(LocalDateTime) temporal;
            //返回周几
           DayOfWeek dow= localDateTime.getDayOfWeek();
           if(dow.equals(DayOfWeek.FRIDAY)){
              return localDateTime.plusDays(3);
           }else if(dow.equals(DayOfWeek.SATURDAY)){
              return localDateTime.plusDays(2);
           }else return localDateTime.plusDays(1);
        });
    }
}
