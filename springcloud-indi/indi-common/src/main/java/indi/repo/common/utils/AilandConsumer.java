package indi.repo.common.utils;

/**
 * @author Luva.Hua
 * @date 2022/4/15 17:35
 * @description
 */
@FunctionalInterface
public interface AilandConsumer<T> {

    /**
     * Performs this operation on the given argument.
     * @param t
     * @throws Exception
     */
    void accept(T t) throws Exception;
}
