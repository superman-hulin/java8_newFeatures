package com.hulin.java8;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Lambda基础语法：
 *    java8中引入了一个新的操作符“->”,该操作符称为箭头操作符或Lambda操作符
 *    操作符左边：
 *      Lambda表达式的参数列表
 *    操作符右边：
 *      表达式中所需执行的功能，即Lambda体
 *    Lambda表达式需要函数式接口的支持（即只有一个抽象方法的接口），操作符左边对应接口方法的参数列表，右边对应对参数的操作
 *    语法格式一：
 *      接口方法无参数，无返回值
 *      ()->System.out.println("hello");
 *    语法格式二：
 *      一个参数 无返回值
 *      (x)-> System.out.println(x)
 *    语法格式三：
 *        若只有一个参数 则()可省略不写
 *          x-> System.out.println(x)
 *     语法格式四：
 *          多个参数 有返回值并且Lambda体中有多条语句
 *          (x,y)->{
 *             System.out.println("函数式接口");
 *             return Integer.compare(x,y);
 *         }
 *     语法格式五：
 *          多个参数 有返回值 Lambda体中只有一条语句 则大括号和return可以省略
 *          (x,y)->Integer.compare(x,y);
 *     语法格式六：
 *          Lambda表达式的参数列表的数据类型可以省略不写，因为jvm编译器通过上下文推断出数据类型，即“类型推断”
 *
 *  Lambda表达式需要“函数式接口”的支持
 *  函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。可以使用注解@FunctionalInterface修饰
 *            该接口可以检查是否是函数式接口
 *
 *
 *
 */
public class LambdaBasic {
    public void test1(){
        /**
         * 匿名内部类方式
         */
        Runnable r=new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        };
        r.run();
        /**
         * Lambda表达式方式
         */
        Runnable r1=()-> System.out.println("hello");
        r1.run();

        /**
         * 注意事项
         *      在jdk1.8前，匿名内部类中方法引用外面的变量时 该变量必须是final
         */
        int num=0; //jdk1.8之前 该变量必须需要显式加fianl时 匿名内部类才能使用该变量，但是不能改变
        Runnable r2=new Runnable() {
            @Override
            public void run() {
                System.out.println("hello"+num);
                //System.out.println("hello"+num++); 改变该值则不行 说明上面的num是自动为我们加上了final
            }
        };
        r2.run();
        Runnable r3=()-> System.out.println("hello"+num);
        //Runnable r3=()-> System.out.println("hello"+num++); 和匿名内部类同理
        r3.run();
    }

    /**
     * 函数式接口中方法有一个参数 无返回值
     */
    public void test2(){
        Consumer<String> con=(x)-> System.out.println(x);
        con.accept("sa");
    }

    /**
     * 函数式接口中方法有多个参数 有返回值
     */
    public void test3(){
        Comparator<Integer> comparator=(x,y)->{
            System.out.println("函数式接口");
            return Integer.compare(x,y);
        };
    }
}
