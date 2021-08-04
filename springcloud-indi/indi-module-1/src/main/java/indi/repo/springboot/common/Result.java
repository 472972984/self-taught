package indi.repo.springboot.common;

import indi.repo.springboot.common.constant.ApplicationConstant;
import lombok.Data;
import org.slf4j.MDC;

import java.io.Serializable;

/**
 * @author ChenHQ
 * @date: create in 2021/6/20
 */
@Data
public class Result<T> implements Serializable {

    //接口正常code
    private static final Integer HTTP_OK = 200;

    //接口异常code
    private static final Integer HTTP_ERROR = 400;

    /**
     * 请求流水号
     * @mock 16bbcf9042334a478ff868aaccb935a0
     */
    private String traceId;

    /**
     * 接口成功与否
     * @mock true
     */
    private Boolean success;

    /**
     * code码
     * @mock 200
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回对象
     */
    private T data;

    private Result(T data, Integer code, String message, Boolean success) {
        this.traceId = MDC.get(ApplicationConstant.MDC_TRACE);
        this.data = data;
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public static <T> Result<T> ok(Integer code, String message, T data) {
        return new Result(data, code, message, true);
    }

    public static <T> Result<T> ok() {
        return ok(HTTP_OK, null, null);
    }


    public static <T> Result<T> ok(Integer code, String message) {
        return ok(code, message, null);
    }

    public static <T> Result<T> ok(T data) {
        return ok(HTTP_OK, null, data);
    }


    public static <T> Result<T> fail(Integer code, String message, T data) {
        return new Result(data, code, message, false);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return fail(code, message, null);
    }

    public static <T> Result<T> fail(String message) {
        return fail(HTTP_ERROR, message);
    }

}

