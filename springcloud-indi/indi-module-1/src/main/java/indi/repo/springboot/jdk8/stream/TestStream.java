package indi.repo.springboot.jdk8.stream;

import indi.repo.springboot.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能说明: 流操作 集合或数组.stream().过滤().映射().终止操作
 *
 * @author: ChenHQ
 * @date: 2021/8/9
 * @desc:
 */
public class TestStream {

    private static List<Student> students = new ArrayList<>();

    static {
        students.add(new Student(1L,"chq","男"));
        students.add(new Student(2L,"zs","男"));
        students.add(new Student(3L,"ssx","女"));
        students.add(new Student(4L,"lb","男"));
        students.add(new Student(5L,"wzj","女"));
        students.add(new Student(6L,"gy","男"));
        students.add(new Student(7L,"hz","男"));
        students.add(new Student(8L,"dc","女"));
        students.add(new Student(8L,"dc","女"));

    }


    public static void main(String[] args) {
        test3();
    }

    /**
     * 筛选操作
     */
    public static void test1() {
        List<Student> studentMan = students.stream().filter(student -> student.getSex().equals("男")).collect(Collectors.toList());
        print(studentMan);
    }

    /**
     * 去重操作
     * 去重复数据是通过流所生成元素的 hashCode() 和 equals() 所以实体类中需要生成对应的方法
     */
    public static void test2() {
        List<Student> collect = students.stream().distinct().collect(Collectors.toList());
        print(collect);
    }


    /**
     * map返回
     */
    public static void test3() {
        students.stream().filter(student -> student.getSex().equals("男")).map(Student::getUsername).forEach(System.out::println);
    }


    /**
     * 输出
     * @param students
     */
    public static void print(List<Student> students) {
        students.stream().forEach(System.out::println);
    }

}
