package com.hulin.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * StreamAPI练习
 */
public class StreamApiExer {
    /**
     * 1. 给定一个数字列表，如何返回一个由每个数的平方构成的列表
     *   即给定[1,2,3,4]  应该返回[1,4,9,16]
     */
    public void test(){
        Integer[] nums=new Integer[]{1,2,3,4};
        Arrays.stream(nums)
                .map((x)->x*x)
                .forEach(System.out::println);
    }

    /**
     * 怎样用map和reduce方法数一数流中有多少个Employee呢
     */
    public void test2(){
        List<Employee2> employees2= Arrays.asList(
                new Employee2("张三",18,9999.99, Employee2.Status.FREE),
                new Employee2("李四",38,5555.99, Employee2.Status.BUSY),
                new Employee2("王五",50,6666.99, Employee2.Status.VOCATION),
                new Employee2("赵六",16,7777.99, Employee2.Status.BUSY)
        );
       Optional<Integer> op= employees2.stream()
                .map((e)->1)
                .reduce(Integer::sum);
    }
}
