/**
 *
 */
package indi.repo.springboot.module.dto;

import cn.hutool.core.thread.ThreadUtil;
import indi.repo.common.utils.IdHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Luva
 * @date 2022-02-23
 */
@Getter
@Setter
public class FutureResponse<T> {

    private static final Map<String, FutureResponse> RESPONSES = new ConcurrentHashMap<>();

    private static final long DEFAULT_TIMEOUT_SECONDS = 20;

    private T response;
    private CountDownLatch latch;
    private long start;
    private long end;
    private long timeoutSeconds;
    private final String requestId;

    public String currentRequestId() {
        return requestId;
    }

    public FutureResponse(String requestId) {
        this(DEFAULT_TIMEOUT_SECONDS, requestId);
    }

    public FutureResponse(long timeoutSeconds, String requestId) {
        this.start = System.currentTimeMillis();
        this.timeoutSeconds = timeoutSeconds;

        this.latch = new CountDownLatch(1);
        this.requestId = requestId;

        RESPONSES.put(requestId, this);
    }

    public static void response(String requestId, Object response) {
        if (RESPONSES.containsKey(requestId)) {
            FutureResponse futureResponse = RESPONSES.get(requestId);
            futureResponse.response = response;
            futureResponse.latch.countDown();
        }
    }

    public T getResponse() throws RuntimeException {
        try {
            // 这段代码表示：等待20秒，直到 latch 计数器锁🔒 为0
            boolean await = this.latch.await(this.timeoutSeconds, TimeUnit.SECONDS);
            if (!await) {
                throw new RuntimeException("超时20秒");
            }
        } catch (InterruptedException e) {
            RESPONSES.remove(this.requestId);
            throw new RuntimeException("获取返回值失败");
        } finally {
            this.end = System.currentTimeMillis();
        }
        return (T) RESPONSES.remove(this.requestId).response;
    }

    public static String getRequestId() {
        return UUID.randomUUID().toString();
    }


    public static void main(String[] args) {

        String requestId = IdHelper.getMillisecond();
        FutureResponse<Boolean> futureResponse = new FutureResponse(requestId);

        ThreadUtil.execute(() -> {
            FutureResponse.response(requestId, true);
        });

        System.out.println(futureResponse.getResponse());

    }


}
