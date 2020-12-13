package com.hulin.java8.annotation;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.lang.reflect.Method;

/**
 * java8对注解处理提供了两点改进：可重复的注解及可用于类型的注解
 */
public class TestAnnotation {
    /**
     * 当将注解MyAnnotation成为可重复注解的条件满足后
     *      1.@Repeatable(MyAnnotations.class)
     *      2.增加容器类 MyAnnotations
     * 则可下列重复操作
     */
    @MyAnnotation("hello")
    @MyAnnotation("world")
    public void show(){

    }

    public void test() throws NoSuchMethodException {
       Class<TestAnnotation> clazz= TestAnnotation.class;
       Method m1=clazz.getMethod("show");
       MyAnnotation[] mas=m1.getAnnotationsByType(MyAnnotation.class);
       for (MyAnnotation myAnnotation:mas){
           System.out.println(myAnnotation.value());
       }
    }
}
