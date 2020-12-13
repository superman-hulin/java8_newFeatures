package com.hulin.java8.defaultFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 每次使用Lambda表达式 还需要自己定义函数式接口来支持 这样太麻烦
 * jdk已经为我们内置提供了四大核心函数式接口
 *      Consumer<T>:消费型接口
 *          void accept(T t);
 *      Supplier<T>: 供给型接口
 *          T get(); //调用该方法 返回所需的内容
 *      Function<T,R>: T代表参数 R代表返回值 函数型接口
 *          R apply(T t);
 *      Predicate<T>: 断言型接口 用于做一些判断操作
 *          boolean test(T t);
 */
public class DefaultFunction {

    /**
     * 测试 Consumer<T>
     */
    public void test1(){
        happy(10000,x-> System.out.println("吃饭"+x));
    }
    //拿着参数 做什么样的消费 有去无回型
    public void happy(double money, Consumer<Double> consumer){
        consumer.accept(money);
    }

    /**
     * 测试 Supplier<T>
     */
    public void test2(){
       List<Integer> list= getNumList(10,()->(int)(Math.random()*100));
    }
    //产生指定个数的整数,并放入集合中
    //产生多少个数 怎么产生
    public List<Integer> getNumList(int num, Supplier<Integer> supplier){
        List<Integer> list=new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    /**
     * 测试 Function<T,R>
     */
    public void test3(){
       String result= strHandler("\t\t hello ",(x)->x.trim());
        System.out.println(result);
     }
    //用于处理字符串
    public String strHandler(String str, Function<String,String> function){
        return function.apply(str);
    }

    /**
     * 测试Predicate<T>
     */
    public void test4(){
        List<String> list= Arrays.asList("hello","ok");
        filterStr(list,s->s.length()>3); //Lambda体中 只有一条语句 return省略
    }
    //将满足条件的字符串放入集合中
    public List<String> filterStr(List<String> list, Predicate<String> predicate){
        List<String> strList=new ArrayList<>();
        for(String str:list){
            if(predicate.test(str)){
                strList.add(str);
            }
        }
        return strList;
    }
}
