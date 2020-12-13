package com.hulin.java8.exer;


import com.hulin.java8.Employee;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class TestLambda {
    List<Employee> employees= Arrays.asList(
            new Employee("张三",18,9999.99),
            new Employee("李四",38,5555.99),
            new Employee("王五",50,6666.99),
            new Employee("赵六",16,7777.99)
    );
    /**
     * 练习1：调用Collections.sort()方法，通过定制排序 比较两个员工（先按年龄比，年龄相同按姓名比）
     *      使用Lambda作为参数传递
     */
    public void test(){
        Collections.sort(employees,(e1,e2)->{
            if (e1.getAge()==e2.getAge()){
                return e1.getName().compareTo(e2.getName());
            }else {
                return Integer.compare(e1.getAge(),e2.getAge());
            }
        });
    }

    /**
     * 练习2：
     *      1.声明函数式接口，接口中声明抽象方法
     *      2.声明一个类 类中编写方法使用接口作为参数，将一个字符串转换成大写，并作为方法的返回值
     *      3.再将一个字符串的第2个和第4个索引位置进行截取子串
     */
    public void test2(){
      String result= strHandler("\t\thello  ",str -> str.trim());
        System.out.println(result);
    }

    //字符串处理方法，第一个参数是对谁做处理 第二个参数是做什么样的处理
    public String strHandler(String str,MyFunction mf){
        return mf.getValue(str);
    }

    /**
     * 练习3：
     *      1.声明一个带两个泛型的函数式接口，泛型类型为<T,R> T为参数，R为返回值
     *      2.接口中声明对应抽象方法
     *      3.在类中声明方法，使用接口作为参数，计算两个long型参数的和
     *      4.再计算两个long型参数的乘积
     */
    public void test3(){
        op(100L,200L,(x,y)->x+y);
    }
    public void op(Long l1,Long l2, MyFunction2<Long,Long> mf){
        System.out.println(mf.getValue(l1,l2));
    }
}
