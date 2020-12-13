package com.hulin.java8.stream;


import com.hulin.java8.Employee;
import com.hulin.java8.stream.Employee2.Status;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Stream的三个操作步骤
 *      创建Stream
 *      中间操作
 *      终止操作
 */
public class TestStreamAPI {
    /**
     * 创建Stream
     *      通过Collection系列集合提供的stream()或parallelStream()
     *      通过Arrays中的静态方法stream()获取数组流
     *      通过Stream类中的静态方法of()
     */
    public void test(){
        /**
         * 通过Collection系列集合提供的stream()或parallelStream()创建Stream
         */
        List<String> list=new ArrayList<>();
        //流也可以带泛型
        Stream<String> stream1=list.stream();

        /**
         * 通过Arrays中的静态方法stream()获取数组流创建Stream
         */
        Employee[] employees=new Employee[10];
        //将数组对象转化成流
        Stream<Employee> stream2=Arrays.stream(employees);

        /**
         *通过Stream类中的静态方法of()
         *      参数是可变参数
         */
        Stream<String> stream3=Stream.of("aa","bb");

        /**
         * 创建无限流的方式
         *      迭代
         *      生成
         */
        //迭代  第一个参数是种子 也就是初始值 每次迭代增加2 无限创建流
        Stream<Integer> stream4=Stream.iterate(0,(x)->x+2);
        stream4.forEach(System.out::println);//把流中创建的数字打印出来 由于流是无限的 则数字也是无限
        stream4.limit(10).forEach(System.out::println);//打印前10个

        //生成  参数是Supplier<T> 无限产生对象
        Stream.generate(()->Math.random())
                .forEach(System.out::println);

    }

    /**
     * 中间操作
     *     筛选与切片
     *     filter--接收Lambda，从流中排除某些元素
     *     limit--截断流，使其元素不超过给定数量
     *     skip(n)--跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。与limit(n)互补
     *     distinct--筛选，通过流所生成元素的hashCode()和equals()去除重复元素
     *  多个中间操作可以连接起来形成一个流水线，除非流水线上触发终止操作，否则中间操作不会执行任何的处理
     *  而在终止操作时一次性全部处理，称为“惰性求值”
     */
    List<Employee> employees= Arrays.asList(
            new Employee("张三",18,9999.99),
            new Employee("李四",38,5555.99),
            new Employee("王五",50,6666.99),
            new Employee("赵六",16,7777.99)
    );
    public void test2(){
       Stream<Employee> stream= employees.stream()
                 .filter((e)->e.getAge()>35); //对原始流过滤操作后是产生一个新流 没有终止操作是没有结果的
        Stream<Employee> stream2= employees.stream()
                .filter((e)-> {
                    System.out.println("asd");
                    return e.getAge() > 35;
                }); //这样也不会输出打印 因为没有对流做终止操作
        stream.forEach(System.out::println); //有了终止操作 才会输出结果
    }

    public void test3(){
        employees.stream()
                .filter((e)->e.getSalary()>5000)
                /**
                 * 由于加了limit 所以上述过滤的迭代中不会遍历所有元素，而是找到两个元素满足时即结束迭代
                 */
                .limit(2)
                .forEach(System.out::println);
    }

    public void test4() {
        employees.stream()
                .filter((e)->e.getSalary()>5000)
                .skip(2) //跳过前两个满足条件的元素 取后面满足条件的元素 若流中元素不足2个 则返回空流
                .forEach(System.out::println);
    }

    public void test5(){
        employees.stream()
                .filter((e)->e.getSalary()>5000)
                .skip(2)
                .distinct() //可以将满足上面条件的元素去重 但是前提是元素对象重写了hashCode和equals方法
                .forEach(System.out::println);
    }

    /**
     * 映射操作
     *      map--接收Lambda，将元素转换成其它形式或提取信息，接收一个函数作为参数，该函数
     *           会被应用到每个元素上，并将其映射成一个新的元素
     *      flatMap--接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    public void test6(){
        List<String> list=Arrays.asList("aa","bbb","ccc");
        list.stream()
                .map((str)->str.toUpperCase())
                .forEach(System.out::println);

        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

       Stream<Stream<Character>> streamStream=list.stream()
                .map(TestStreamAPI::filterCharacter);
       streamStream.forEach((stream)->{
           stream.forEach(System.out::println);
       });

       //flatMap可以将流中的流给展平成一个流
     Stream<Character> sm=  list.stream()
               .flatMap(TestStreamAPI::filterCharacter);
        sm.forEach(System.out::println);

    }

    public static Stream<Character> filterCharacter(String str){
        List<Character> list=new ArrayList<>();
        for(Character ch:str.toCharArray()){
            list.add(ch);
        }
        return list.stream();
    }

    /**
     * 排序
     *  sorted()----自然排序(Comparable)
     *  sorted(Comparator com)----定制排序(Comparator)
     */
    public void test7(){
        List<String> list=Arrays.asList("aaa","bbb","ccc");
        list.stream()
                /**
                 * 流中的元素是string类型 而string是实现了Comparable的 则流中的元素具备自然排序的功能
                 */
                .sorted()
                .forEach(System.out::println);

        employees.stream()
                //定制排序
                .sorted((e1,e2)->{
                    if (e1.getAge()==e2.getAge()){
                        return e1.getName().compareTo(e2.getName());
                    }else {
                        return 0;
                    }
                })
                .forEach(System.out::println);
    }

    /**
     * 终止操作
     */
    /**
     * 查找与匹配
     *  allMatch--检查所有元素是否都满足条件
     *  anyMatch--检查是否至少匹配一个元素
     *  noneMatch--检查是否没有匹配所有元素
     *  findFirst--返回第一个元素
     *  findAny--返回当前流中的任意元素
     *  count--返回流中元素的总个数
     *  max--返回流中最大值
     *  min--返回流中最小值
     */
    List<Employee2> employees2= Arrays.asList(
            new Employee2("张三",18,9999.99, Status.FREE),
            new Employee2("李四",38,5555.99,Status.BUSY),
            new Employee2("王五",50,6666.99,Status.VOCATION),
            new Employee2("赵六",16,7777.99,Status.BUSY)
    );
    public void test8(){
      boolean b1= employees2.stream()
              //检查流中所有元素是否都满足该判断条件
                .allMatch((e)->e.getStatus().equals(Status.BUSY));
       boolean b2= employees2.stream()
                //检查流中是否至少存在一个元素满足条件
                .anyMatch((e)->e.getStatus().equals(Status.BUSY));

        boolean b3= employees2.stream()
                //检查流中是否不存在一个元素满足条件，也就是有元素满足
                .noneMatch((e)->e.getStatus().equals(Status.BUSY));
       Optional<Employee2> op= employees2.stream()
                //按工资排序
                .sorted((e1,e2)->Double.compare(e1.getSalary(),e2.getSalary()))
                //返回第一个元素
                .findFirst();
        System.out.println(op.get());

        employees2.stream()
                //将状态为空闲的筛选出来
                .filter((e)->e.getStatus().equals(Status.FREE))
                //在空闲的员工中随便选一个
                .findAny();

       Long count= employees2.stream()
               //流中元素总个数
                .count();
        Optional<Employee2> op1= employees2.stream()
                //返回流中最大元素 但是需要传参 即按什么比的最大元素
                .max((e1,e2)->Double.compare(e1.getSalary(),e2.getSalary()));
        System.out.println(op1.get());

        //返回流中工资最小的 则需要先将流中元素的工资单独提出来，再取最小值
       Optional<Double> op2= employees2.stream()
                .map(Employee2::getSalary)
                .min(Double::compare);
        System.out.println(op2.get());

    }

    /**
     * 归约
     *  reduce(T identity,BinaryOperator)
     *  reduce(BinaryOperator)--可以将流中元素反复结合起来，得到一个值
     */
    public void test9(){
        List<Integer> list=Arrays.asList(1,2,3,4);
       Integer sum= list.stream()
                /**
                 * 第一个参数是起始值，第二个参数是二元运算函数式接口
                 * 执行流程
                 *      先把起始值作为了x，然后取流中元素1作为y 得到1
                 *      再将1作为x 然后再取2 得到3
                 *      再将3作为x 。。。。
                 */
                .reduce(0,(x,y)->x+y);
        System.out.println(sum);

      Optional<Double> op=  employees2.stream()
                .map(Employee2::getSalary)
                .reduce(Double::sum);
        System.out.println(op.get());
      //上面两个reduce的返回值不同的原因是 第一个不可能为空 因为有起始值做运算 而第二个有可能为空

    }

    /**
     * 收集
     * collect--将流转换为其它形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    public void test10(){
        //将所有员工的名字提取出来 并放到集合中
       List<String> list= employees2.stream()
                .map(Employee2::getName)
                /**
                 * 参数是collector（接口） 即收集器 也就是想按照什么方式进行结果的收集操作
                 *      Collector接口中方法的实现决定了如何对流执行收集操作（如收集到List、set、map）
                 *      但是Collectors提供了很多静态方法 可以方便创建常见收集器实例。
                 */
                .collect(Collectors.toList());

        Set<String> set= employees2.stream()
                .map(Employee2::getName)
                .collect(Collectors.toSet());//将名字去重
        set.forEach(System.out::println);

        HashSet<String> hs= employees2.stream()
                .map(Employee2::getName)
                /**
                 * Collectors.toCollection()方法中参数是Supplier<C>接口 可以创建我们想要的其它数据结构中
                 * 这样就会收集到我们想要的其它结构的集合中
                 */
                .collect(Collectors.toCollection(HashSet::new));

        Long count=employees2.stream()
                //统计集合中元素个数
                .collect(Collectors.counting());
        Double avg=employees2.stream()
                //计算工资的平均值 Collectors.averagingDouble()参数是计算元素什么的平均值
                .collect(Collectors.averagingDouble(Employee2::getSalary));
        Double sum=employees2.stream()
                //计算工资的总和
                .collect(Collectors.summingDouble(Employee2::getSalary));
       Optional<Employee2> max= employees2.stream()
                //计算最大值 Collectors.maxBy() 参数是Comparator 即定制排序
                .collect(Collectors.maxBy((e1,e2)->Double.compare(e1.getSalary(),e2.getSalary())));
      Optional<Double> min= employees2.stream()
               .map(Employee2::getSalary)
               //最小值
               .collect(Collectors.minBy(Double::compare));
        /**
         * 分组
         *  根据员工状态分组 返回map类型 即状态：员工集合
         *  map没有forEach方法
         */
    Map<Status,List<Employee2>> map=  employees2.stream()
              .collect(Collectors.groupingBy(Employee2::getStatus));

    /**
     * 多级分组
     */
    Map<Status,Map<String,List<Employee2>>> map2=  employees2.stream()
            .collect(Collectors.groupingBy(Employee2::getStatus,Collectors.groupingBy((e)-> {
                        if (e.getAge() <= 35) {
                            return "青年";
                        } else if (e.getAge() <= 50) {
                            return "中年";
                        } else return "老年";
                    }
                    )
            ));

    /**
     * 分区
     *  true的为一区
     *  false为另一区
     */
    Map<Boolean,List<Employee2>> map3=employees2.stream()
            .collect(Collectors.partitioningBy((e)->e.getSalary()>8000));

    /**
     * 另一种获取平均值、最大值等的方式
     */
    DoubleSummaryStatistics dss=employees2.stream()
            .collect(Collectors.summarizingDouble(Employee2::getSalary));
        dss.getAverage();//平均值
        dss.getCount();//
        dss.getSum();//总和
        dss.getMax();//最大值

        /**
         * 连接字符串
         *  将字符串拼接成一个字符串
         */
       String str= employees2.stream()
                .map(Employee2::getName)
                .collect(Collectors.joining());
                //.collect(Collectors.joining(",")); 以逗号进行连接
                //.collect(Collectors.joining(",","==","===")); 以逗号进行连接,并且可指定首尾

    }

    /**
     * Stream并行流
     */
    public void test11(){
        LongStream.rangeClosed(0,100000L)
                .parallel() //并行流 底层是Fork/join框架·
                .reduce(0,Long::sum);
    }




}