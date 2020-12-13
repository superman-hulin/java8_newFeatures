package com.hulin.java8.optional;

import com.hulin.java8.Employee;

import java.util.Optional;

/**
 * Optional容器类的常用方法
 * Optional.of(T t) 创建一个Optional 实例
 * Optional.empty() 创建一个空的Optional实例
 * Optional.ofNullable(T t) 若t不为null，创建Optional实例，否则创建空实例
 * isPresent() 判断是否包含值
 * orElse(T t) 如果调用对象包含值，返回该值 否则返回t
 * orElseGet(Supplier s) 如果调用对象包含值 返回该值 否则返回s获取的值
 * map(Function f) 如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
 * flatMap(Function mapper) 与map类似，要求返回值必须是Optional
 */
public class TestOptional {

    public void test(){
        /**
         * 创建容器实例 参数为放入容器的对象
         * 实际中该参数是别处传过来的值
         * 当执行Optional.of(null)时 会报空指针异常，即我们可以比以前更快定位到哪个对象为空。
         */
       Optional<Employee> op= Optional.of(new Employee());
       Employee emp= op.get();
    }

    /**
     * 有时候就是想要构建一个空的Optional 则方式有两种
     */
    public void test1(){
        /**
         * 方式一：创建一个空的Optional实例
         */
       Optional<Employee> op= Optional.empty();
        op.get();//报 无值的异常 而非空指针异常
        /**
         * 方式二: 使用ofNullable传入一个null
         */
        Optional<Employee> op2=Optional.ofNullable(null);
        op2.get();//报 无值的异常 而非空指针异常 也可以快速定位到哪个地方空指针

        /**
         * 在get值之前 先判断容器中是否有值
         */
        if(op2.isPresent()){
            System.out.println(op2.get());
        }

        /**
         * op.orElse(T t) 如果op容器中没值 则以t去填充值
         */
        Employee emp=op.orElse(new Employee());

        /**
         * 和上面方法的区别在于 该参数是一个函数式接口 用于产生对象
         * 好处是可以编写更多的逻辑 如满足什么条件返回某个对象 满足另一条件时返回另一对象
         */
        op.orElseGet(()->new Employee());
    }

    /**
     * map(Function f) 如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
     */
    public void test2(){
        Optional<Employee> op=Optional.ofNullable(new Employee());
        //对容器中的对象应用map函数
       Optional<String> str= op.map((e)->e.getName());
        System.out.println(str.get());

        //将函数式接口返回的值 再放入Optional中 进一步防止返回的值为空
        Optional<String> str2= op.flatMap((e)->Optional.ofNullable(e.getName()));
        System.out.println(str2.get());

    }
}
