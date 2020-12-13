package com.hulin.java8.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * java8之后 新增了一些时间的相关类 增强了很多功能
 * 其中一个重要功能是 一些时间类都是线程安全的
 * 测试SimpleDateFormat 日期格式转换类 的线程不安全性
 */
public class TestSimpleDateFormat {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");

        //创建线程池
        ExecutorService pool= Executors.newFixedThreadPool(10);
        //创建日期转换的任务
        Callable<Date> callable=new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return sdf.parse("20161218");
            }
        };
        List<Future<Date>> result=new ArrayList<>();
        //循环将10个任务放入线程池执行 并将每个任务返回结果放入list
        for(int i=0;i<10;i++){
            result.add(pool.submit(callable));
        }
        for(Future<Date> future:result){
            System.out.println(future.get());
        }

        /**
         * 以上会报错，因为SimpleDateFormat并非线程安全的
         * 解决方案一：使用ThreadLocal上锁
         */
        Callable<Date> callable1=new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return DateFormatThreadLocal.convert("20161218");
            }
        };
        //创建线程池
        ExecutorService pool1= Executors.newFixedThreadPool(10);
        List<Future<Date>> result1=new ArrayList<>();
        //循环将10个任务放入线程池执行 并将每个任务返回结果放入list
        for(int i=0;i<10;i++){
            result1.add(pool1.submit(callable1));
        }
        for(Future<Date> future:result1){
            System.out.println(future.get());
        }
        pool1.shutdown();

        /**
         * 解决方案二：
         * 使用jdk8后线程安全的日期类（不可变对象）
         * Date--->LocalDate
         * LocalDate:日期 如年月日
         * LocalTime：时间 如10:12:02
         * LocalDateTime:日期加时间 如年月日 时分秒
         * SimpleDateFormat-->DateTimeFormatter
         */
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyyMMdd");
        Callable<LocalDate> callable2=new Callable<LocalDate>() {
            @Override
            public LocalDate call() throws Exception {
                return LocalDate.parse("20161218",dtf);
            }
        };
        //创建线程池
        ExecutorService pool2= Executors.newFixedThreadPool(10);
        List<Future<LocalDate>> result2=new ArrayList<>();
        //循环将10个任务放入线程池执行 并将每个任务返回结果放入list
        for(int i=0;i<10;i++){
            result2.add(pool2.submit(callable2));
        }
        for(Future<LocalDate> future:result2){
            System.out.println(future.get());
        }
        pool2.shutdown();
    }

    /**
     * 格式化时间/日期：DateTimeFormatter
     */
    public void test(){
        //直接使用提供好的一些格式
      DateTimeFormatter dtf= DateTimeFormatter.ISO_DATE_TIME;
      LocalDateTime ldt=  LocalDateTime.now();
      String strDate=ldt.format(dtf);

      //自定义格式
      DateTimeFormatter dtf2= DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String strDate2=dtf2.format(ldt);
        //将字符串转回日期 不传格式参数的话 沿用默认格式DateTimeFormatter.ISO_LOCAL_DATE_TIME转
        //可能会报错 因为默认转换格式不一定对
        LocalDateTime.parse(strDate);
        //指定转换格式
        LocalDateTime.parse(strDate,dtf2);

    }

}
