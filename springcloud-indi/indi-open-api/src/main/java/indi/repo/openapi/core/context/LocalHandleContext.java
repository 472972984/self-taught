package indi.repo.openapi.core.context;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date 2021/12/16
 */
public class LocalHandleContext {

    private static final ThreadLocal<HandleContext> HANDLE_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取绑定的上下文信息
     * @param <T>
     * @return
     */
    public static <T extends HandleContext> T getHandleContext() {
        return (T) HANDLE_CONTEXT_THREAD_LOCAL.get();
    }

    /**
     * 绑定上下文信息
     * @param context
     */
    public static void bindHandleContext(HandleContext context) {
        HANDLE_CONTEXT_THREAD_LOCAL.set(context);
    }

    /**
     * 移除
     */
    public static void removeHandleContext() {
        HANDLE_CONTEXT_THREAD_LOCAL.remove();
    }


}
