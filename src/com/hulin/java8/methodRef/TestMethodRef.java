package com.hulin.java8.methodRef;

import com.hulin.java8.Employee;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 方法引用：若Lambda体中的内容有方法已经实现了 我们可以使用“方法引用”
 *          （可以理解为方法引用是Lambda表达式的另外一种表现形式）
 *  主要有三种语法格式：
 *      对象::实体方法名
 *      类::静态方法名
 *      类::实例方法名
 *  构造器引用
 *      格式：ClassName::new
 *      注意：需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致
 *  数组引用
 *      格式：Type::new
 */
public class TestMethodRef {
    public void test1(){
        Consumer<String> consumer=(x)-> System.out.println(x);
        /**
         * Lambda体中的内容已经有方法实现了，即System.out对象的println方法
         * 则可以使用方法引用
         * 注意：Lambda表达式对应的接口中抽象方法的参数列表和返回值类型 要与方法引用中
         *      方法的参数列表和返回值类型保持一致
         */
        PrintStream ps=System.out;
        Consumer<String> consumer1=ps::println;
        Consumer<String> consumer2=(x)->ps.println(x);
        Consumer<String> consumer3=System.out::println;
        consumer3.accept("abdcd");
    }

    public void test2(){
        Employee emp=new Employee();
        Supplier<String> sup=()->emp.getName();

        Supplier<Integer> sup2=emp::getAge;
       Integer num= sup2.get();

    }

    public void test3(){
        Comparator<Integer> com=(x,y)->Integer.compare(x,y);
        Comparator<Integer> com1=Integer::compare;

    }

    /**
     * 类::实例方法名
     *  如果第一个参数是实例方法的调用者，第二个参数是调用方法的参数时，就可以使用类::实例方法名
     */
    public void test4(){
        BiPredicate<String,String> bp=(x,y)->x.equals(y);
        BiPredicate<String,String> bp2=String::equals;
    }

    public void test5(){
        Supplier<Employee> sup=()->new Employee();
        sup.get();
        /**
         * 构造器引用 该处是调用无参构造器
         * 因为Supplier接口的抽象方法是无参的 则Employee::new就是调用无参构造器
         */
        Supplier<Employee> sup2=Employee::new;
    }

    public void test6(){
       Function<Integer,Employee> function=x->new Employee(x);
        /**
         * 构造器引用 该处是调用有一个参数的构造器
         * 因为Function接口的抽象方法是有一个参的 则Employee::new就是调用一个参的构造器
         */
        Function<Integer,Employee> function1=Employee::new;


    }

    public void test7(){
        Function<Integer,String[]> function=x->new String[x];
        String[] strs=function.apply(10);
        System.out.println(strs.length);

        Function<Integer,String[]> function2=String[]::new;
        String[] strs2=function2.apply(20);
        System.out.println(strs2.length);
    }
}
