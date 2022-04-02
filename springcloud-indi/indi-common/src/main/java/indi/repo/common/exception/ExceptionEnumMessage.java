package indi.repo.common.exception;

/**
 * @author ChenHQ
 * @date: create in 2021/6/20
 */
public interface ExceptionEnumMessage {

    /**
     * 获取错误码
     * @return  获取错误码
     */
    int getErrorCode();

    /**
     * 获取错误信息
     * @return  获取错误信息
     */
    String getErrorMsg();

}
