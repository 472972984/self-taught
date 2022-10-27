package indi.repo.springboot.jdk8.fork;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ChenHQ
 * @date 2022/10/27 16:22
 */
public class ForkJoinPoolUtil<T> extends RecursiveAction {

    //二分开始位置
    private Integer start;

    //二分结束位置
    private Integer end;

    //判断达到该步长则开始执行任务
    private Integer sign;

    //执行任务的消费方法函数体
    private Consumer<List<T>> consumer;

    //消费的数据
    private List<T> data;

    /**
     * @param start    二分开始位置
     * @param end      二分结束位置
     * @param sign     判断达到该步长则开始执行任务
     * @param consumer 执行任务的消费方法函数体
     * @param data     消费的数据
     * @param <T>
     * @return
     */
    public static <T> ForkJoinPoolUtil getInstance(Integer start, Integer end, Integer sign, Consumer<List<T>> consumer, List<T> data) {
        return new ForkJoinPoolUtil(start, end, sign, consumer, data);
    }

    private ForkJoinPoolUtil(Integer start, Integer end, Integer sign, Consumer<List<T>> consumer, List<T> data) {
        this.start = start;
        this.end = end;
        this.sign = sign;
        this.consumer = consumer;
        this.data = data;
    }

    @Override
    protected void compute() {
        System.out.println("compute start ----" + Thread.currentThread().getName() + "                    " + "start：" + start + " end：" + end);
        if (end - start <= sign) {
            consumer.accept(data.subList(start, end));
        } else {
            int middle = (end + start) / 2;
            ForkJoinPoolUtil taskLeft = new ForkJoinPoolUtil(start, middle, sign, consumer, data);
            ForkJoinPoolUtil taskRight = new ForkJoinPoolUtil(middle, end, sign, consumer, data);
            invokeAll(taskLeft, taskRight);
        }
        System.out.println("compute end ----" + Thread.currentThread().getName() + "                    " + "start：" + start + " end：" + end);
    }

    public static void main(String[] args) {
        Consumer<List<Integer>> consumer;
        int sign = 2;

        consumer = list -> list.stream().map(e -> Thread.currentThread().getName() + "----------(" + e + ")----------").forEach(System.out::println);

        List<Integer> voList = IntStream.range(0, 10).boxed().map(e -> e.intValue()).collect(Collectors.toList());

        ForkJoinPool pool = new ForkJoinPool(3);
        ForkJoinPoolUtil<List<Integer>> poolUtil = ForkJoinPoolUtil.getInstance(0, voList.size(), sign, consumer, voList);
        pool.invoke(poolUtil);
        try {
            poolUtil.get();
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("线程中断: " + e);
        }
    }
}


