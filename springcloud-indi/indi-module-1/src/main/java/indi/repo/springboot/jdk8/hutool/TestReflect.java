package indi.repo.springboot.jdk8.hutool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;

import java.io.*;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author ChenHQ
 * @date 2022/7/23 12:54
 */
public class TestReflect {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws Exception {

//        MyHttpInterface instance = (MyHttpInterface) new Invoker().getInstance(MyHttpInterface.class);
//        System.out.println(instance.doGet("http://www.baidu.com"));

//        System.out.println(replaceLast("chenhuiqi",'i','I'));
//
//        FIFOCache<String, String> cache = CacheUtil.newFIFOCache(16, 1000 * 60);
//
//        cache.put("1","chq");
//        cache.put("2","chq2");
//        cache.put("2","chq3");
//
//        System.out.println("cache = " + cache);


        System.out.println(FileUtil.isEmpty(new File("/Users/admin/Downloads/chenhuiqi.txt")));


    }

    private static String replaceLast(String in, char oldChar, char newChar) {
        int mem = in.lastIndexOf(oldChar);

        return mem > -1 ? in.substring(0, mem).concat(in.substring(mem).replace(oldChar, newChar)) : in;
    }


    @SuppressWarnings("unchecked")
    private <T> T getBean(final Object o) {

        return (T) Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), (proxy, method, args) -> {

            //InvocationHandler.invoke 函数表达式，返回
            System.out.println("当前要执行的方法名称为:" + method.getName());
            System.out.println("当前的参数信息为:" + Arrays.asList(args));

            System.out.println("方法执行前 before....");
            Object invoke = method.invoke(o, args);
            System.out.println("方法执行后 after....");

            return invoke;
        });
    }


    private static void generatorDataset() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/admin/Downloads/random_500M/random_500M_ver_guest.csv"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/admin/Downloads/random_500M/my_random_500M_ver_guest.csv"));
//        CsvWriter csvWriter = CsvUtil.getWriter(new File("/Users/admin/Downloads/random_hor/random_500M/my_random_500M_hor_host.csv"), StandardCharsets.UTF_8, true);

        String line;
        int row = 0;
        int max = 2000000;
        List<String> list = new LinkedList<>();
        list.add("id");
        while ((line = reader.readLine()) != null) {
            if (row == 0) {
                String[] split = line.split(",");
                int length = split.length;
                int j = length - 1;
                for (int i = 1; i <= j; i++) {
                    list.add("x" + i);
                }
                list.add("y");
                String headerLine = list.stream().collect(Collectors.joining(","));
                writer.write(headerLine);
                writer.newLine();
            } else {
                if (row == max) {
                    break;
                }
                int i = line.lastIndexOf(".0");
                line = row + "," + line.substring(0, i);
                writer.write(line);
                writer.newLine();
            }
            row++;
        }
        System.out.println("总行数：" + row);
        writer.close();
        reader.close();
    }

    private static void datasetHost100() {
        final int max = 2200000;

        CsvWriter csvWriter = CsvUtil.getWriter(new File("/Users/admin/Downloads/chenhuiqi100-host-new.csv"), StandardCharsets.UTF_8, true);

        int i = 1;

        csvWriter.writeLine("id", "age", "sex", "height", "y");

        while (true) {
            csvWriter.writeLine(String.valueOf(i), String.valueOf(rangeParamValue()), String.valueOf(rangeParamValue()), String.valueOf(rangeParamValue()), String.valueOf(yLabel()));
            i++;
            if (i >= max) {
                break;
            }
        }

        csvWriter.close();
        System.out.println("当前行数： " + i);
    }


    private static void datasetGuest100() {
        final int max = 2200000 + 1100000;

        CsvWriter csvWriter = CsvUtil.getWriter(new File("/Users/admin/Downloads/chenhuiqi100-guest-new.csv"), StandardCharsets.UTF_8, true);

        int i = 1100000;

        csvWriter.writeLine("id", "age", "sex", "height", "y");

        while (true) {
            csvWriter.writeLine(String.valueOf(i), String.valueOf(rangeParamValue()), String.valueOf(rangeParamValue()), String.valueOf(rangeParamValue()), String.valueOf(yLabel()));
            i++;
            if (i >= max) {
                break;
            }
        }

        csvWriter.close();
        System.out.println("当前行数： " + i);
    }


    public static float rangeParamValue() {
        if (RANDOM.nextBoolean()) {
            return (float) (0 + Math.random());
        } else {
            return (float) (0 - Math.random());
        }
    }

    private static Integer yLabel() {
        return RANDOM.nextBoolean() ? 1 : 0;
    }


}
