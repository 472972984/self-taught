package indi.repo.springboot.jdk8.stream;

import cn.hutool.core.date.DateUtil;
import indi.repo.springboot.entity.Student;
import indi.repo.springboot.entity.SystemLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 功能说明: 流操作 集合或数组.stream().过滤().映射().终止操作
 *
 * @author: ChenHQ
 * @date: 2021/8/9
 */
public class TestStream {

    private static List<Student> students = new ArrayList<>();
    private static List<SystemLog> systemLogs = new ArrayList<>();

    static {
        students.add(new Student(1L, "chq", "男"));
        students.add(new Student(2L, "zs", "男"));
        students.add(new Student(3L, "ssx", "女"));
        students.add(new Student(4L, "lb", "男"));
        students.add(new Student(5L, "wzj", "女"));
        students.add(new Student(6L, "gy", "男"));
        students.add(new Student(7L, "hz", "男"));
        students.add(new Student(8L, "dc", "女"));
        students.add(new Student(1L, "dc2", "女"));

        //------------------------------------------------------

        systemLogs.add(new SystemLog(1L, DateUtil.parseDate("2023-04-03 00:00:00")));
        systemLogs.add(new SystemLog(2L, DateUtil.parseDate("2023-04-02 00:00:00")));
        systemLogs.add(new SystemLog(3L, DateUtil.parseDate("2023-04-01 00:00:00")));
        systemLogs.add(new SystemLog(4L, DateUtil.parseDate("2023-04-05 00:00:00")));

    }


    public static void main(String[] args) {

        System.out.println(students.stream().map(Student::getUsername).collect(Collectors.joining(",")));
        String msg = "chq,zs,ssx,lb,wzj,gy,hz,dc,dc2,";
        System.out.println(msg.substring(0, msg.length() - 1));

        test4();

    }

    /**
     * 分组取出指定字段
     */
    public static void test4() {
        Map<Long, List<String>> map = students.stream()
                .collect(Collectors.groupingBy(Student::getId, Collectors.mapping(Student::getUsername, Collectors.toList())));
        System.out.println("map = " + map);
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
     *
     * @param students
     */
    public static void print(List<Student> students) {
        students.stream().forEach(System.out::println);
    }

    /**
     * 流操作选择输出最大id
     */
    public static void maxId() {
        long max = students.stream().mapToLong(Student::getId).reduce(Long::max).getAsLong();
        System.out.println(max);
        System.out.println(students.stream().anyMatch(student -> Objects.equals(max, student.getId())));

        System.out.println(students.stream().count() == students.size());

        List<Student> list = new ArrayList<>();
        list.add(new Student(10L, "test", "男"));

        System.out.println(Stream.concat(list.stream(), students.stream()).count());

        students.stream().filter(student -> Objects.equals(student.getId(), 8L)).forEach(System.out::println);

        students.stream().limit(2).forEach(System.out::println);

        students.stream().mapToLong(student -> student.getId()).forEach(System.out::println);

        System.out.println(students.stream().collect(Collectors.averagingLong(Student::getId)));

        System.out.println(students.stream().map(Student::getUsername).collect(Collectors.joining("-")));

        System.out.println(students.stream().map(Student::getUsername).collect(Collectors.joining("-", "《", "》")));

    }

}
