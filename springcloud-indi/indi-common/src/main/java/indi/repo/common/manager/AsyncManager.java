package indi.repo.common.manager;


import indi.repo.common.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author: ChenHQ
 * @date: 2021/8/31
 */
@Slf4j
public class AsyncManager {

    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private static final ScheduledExecutorService EXECUTOR = SpringUtils.getBean("scheduledExecutorService");

    private static final AsyncManager INSTANCE = new AsyncManager();

    private AsyncManager() {
    }


    /**
     * 单例模式
     */
    public static AsyncManager getInstance() {
        return INSTANCE;
    }

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public void execute(TimerTask task) {
        EXECUTOR.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }


    /**
     * 执行任务 —— 带返回值信息
     *
     * @param callable 任务
     */
    public <T> T executeFuture(Callable<T> callable) {
        try {
            ScheduledFuture<T> schedule = EXECUTOR.schedule(callable, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
            return (T)schedule.get();
        } catch (Exception e) {
            log.error("【executeFuture：error】：{}",e.getMessage());
        }
        return null;
    }

    /**
     * 停止任务线程池
     */
    public void shutdown() {
        Threads.shutdownAndAwaitTermination(EXECUTOR);
    }

}
