package indi.repo.springboot.common.exception;

import lombok.Data;

/**
 * @author ChenHQ
 * @date: create in 2021/6/20
 */
@Data
public class BaseException extends RuntimeException {

    /**
     * 参数校验失败
     */
    public static final Integer ERROR_PARAM_CODE = 100001;

    private final Integer errorCode;

    private final String errorMsg;

    public BaseException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BaseException(ExceptionEnumMessage enumMessage) {
        this(enumMessage.getErrorCode(), enumMessage.getErrorMsg());
    }
}
