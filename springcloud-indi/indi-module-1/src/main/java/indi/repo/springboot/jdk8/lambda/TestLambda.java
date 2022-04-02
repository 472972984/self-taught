package indi.repo.springboot.jdk8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/8/9
 */
public class TestLambda {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
        test5();
    }


    /**
     * 无参数，无返回值
     * -> lambda操作符，也叫箭头操作符
     * -> 左边：lambda形参列表 (其实就是接口中的抽象方法的形参列表)
     * -> 右边：lambda体(其实就是重写的抽象方法的方法体)
     */
    public static void test1() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("true = " + true);
            }
        };
        runnable.run();

        Runnable runnableLambda = () -> System.out.println("true = " + true);
        runnableLambda.run();
    }

    /**
     * 一个参数，无返回值
     */
    public static void test2() {
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("s = " + s);
            }
        };
        consumer.accept("需要一个参数没有返回值，原写法");

        Consumer consumer2 = (s) -> {
            System.out.println("s = " + s);
        };
        consumer2.accept("需要一个参数没有返回值，lambda写法");

        //若只需要一个参数，小括号可以去掉
        Consumer consumer3 = s -> System.out.println("s = " + s);
        consumer3.accept("需要一个参数没有返回值，lambda写法");
    }

    /**
     * 两个或以上的参数，多条执行语句，并且可以有返回值
     */
    public static void test3() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        System.out.println(comparator.compare(0, 2));

        Comparator<Integer> comparator2 = Comparator.naturalOrder();
        System.out.println(comparator2.compare(0, 2));

        Comparator<Integer> comparator3 = (o1, o2) -> o1.compareTo(o2);
        System.out.println(comparator3.compare(0, 2));


    }

    /**
     * 函数接口作为入参
     */
    public static void test4() {

        happyTime(20d, new Consumer<Double>() {
            @Override
            public void accept(Double aDouble) {
                System.out.println("价格为：" + aDouble);
            }
        });

        happyTime(20d, (mon) -> {
            System.out.println("价格为：" + mon);
        });

        /************************************************/

        List<Integer> result = selectCountEq5(Arrays.asList(1, 2, 3, 5, 6), new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer == 5;
            }
        });

        System.out.println(result);


    }

    public static void happyTime(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }


    public static List<Integer> selectCountEq5(List<Integer> list, Predicate<Integer> predicate) {
        List<Integer> result = new ArrayList<>();
        for (Integer integer : list) {
            if (predicate.test(integer)) {
                System.out.println("当前数据：" + integer);
            } else {
                result.add(integer);
            }
        }
        return result;
    }


    /**
     * 方法引用与构造器引用
     * 当要传递给Lambda体的操作，已经有实现的方法了，可以使用方法引用 ! 方法引用可以看作是Lambda表达式深层次的表达，
     * 换句话说，方法引用就是Lambda表达式，也就是函数式接口的一个实例，通过方法名字来指向一个方法，可以认为是Lambda表达式的一个语法糖。
     *
     * 对象 : : 非静态方法
     */
    public static void test5() {
        Consumer<String> consumer = s -> System.out.println(s);
        consumer.accept("lambda表达式");

        Consumer<String> consumer2 = System.out::println;
        consumer2.accept("方法引用");
    }


}
