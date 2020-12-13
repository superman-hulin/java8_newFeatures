package com.hulin.java8;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.*;

/**
 *  Lambda入门：为什么要使用Lambda表达式
 */
public class Lambda {
    /**
     * 匿名内部类
     */
    public void inner(){
        Comparator<Integer> comparator=new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                //匿名内部类中有用的只有这一句代码 可使用Lambda表达式简化
                return Integer.compare(o1,o2);
            }
        };
        TreeSet<Integer> treeSet=new TreeSet<>(comparator);
    }

    /**
     * 使用Lambda表达式简化匿名内部类
     */
    public void inner2(){
        Comparator<Integer> comparator=(o1, o2) -> Integer.compare(o1,o2);
        TreeSet<Integer> treeSet=new TreeSet<>(comparator);
    }

    /**
     * 需求：获取当前公司中员工年龄大于35的员工
     */
    List<Employee> employees= Arrays.asList(
        new Employee("张三",18,9999.99),
        new Employee("李四",38,5555.99),
        new Employee("王五",50,6666.99),
        new Employee("赵六",16,7777.99)
    );

    /**
     * 过滤出年龄符合要求的员工
     */
    public List<Employee> filterEmployeesByAge(List<Employee> employees){
        List<Employee> result=new ArrayList<>();
        for(Employee employee:employees){
            if(employee.getAge()>=35){
                result.add(employee);
            }
        }
        return result;
    }

    public void testFilter(){
        List<Employee> list=filterEmployeesByAge(employees);
        for(Employee employee:list){
            System.out.println(employee);
        }
    }

    /**
     * 假设现在又有新需求：获取当前公司中员工工资大于5000的员工信息
     */
    public List<Employee> filterEmployeesBySalary(List<Employee> employees){
        List<Employee> result=new ArrayList<>();
        for(Employee employee:employees){
            if(employee.getSalary()>=5555){
                result.add(employee);
            }
        }
        return result;
    }

    /**
     * 会发现上面的需求存在大量的公共代码，核心变化的代码只有 employee.getSalary()>=5555
     * 则使用策略设计模式来优化上述需求
     */

    /**
     * 优化方式一
     *      定义策略接口 具体逻辑有实现类去实现  这样过滤方法只需写通用的 无须变化
     */
    public List<Employee> filterEmployee(List<Employee> employees,MyPredicate<Employee> myPredicate){
        List<Employee> list=new ArrayList<>();
        for (Employee employee:employees){
            if(myPredicate.test(employee)){
                list.add(employee);
            }
        }
        return list;
    }
    public void testFilter2(){
        /**
         * 这种好处就是如果有不同的过滤需求 只需要实现策略接口 传入具体的实现类即可
         */
       List<Employee> list= filterEmployee(employees,new FilterEmployeeByAge());
        for(Employee employee:list){
            System.out.println(employee);
        }
    }

    /**
     * 优化方式二
     *    优化方式一的缺点是 每需要扩展一个策略 都需要实现接口写一个实现类
     *    并且实现代码极少
     *    则采用匿名内部类
     */
    public void testFilter3(){
        List<Employee> list= filterEmployee(employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary()>=5555;
            }
        });
        for(Employee employee:list){
            System.out.println(employee);
        }
    }

    /**
     * 优化方式三
     *      使用Lambda表达式优化匿名内部类
     */
    public void testFilter4(){
        List<Employee> list= filterEmployee(employees,(e)->e.getSalary()>=5555);
        list.forEach(System.out::println);
    }

    /**
     * 优化方式四
     *      使用StreamAPI对原始集合直接过滤
     */
    public void testFilter5(){
       employees.stream()
                .filter((e)->e.getSalary()>=5555)
                .forEach(System.out::println);

        employees.stream()
                .filter((e)->e.getSalary()>=5555)
                .limit(2) //取满足条件的前两个
                .forEach(System.out::println);
        employees.stream()
                .map(Employee::getName) //遍历出所有员工名字
                .forEach(System.out::println);
    }

}
