package indi.repo.springboot.exception.enums;

import indi.repo.springboot.exception.ExceptionEnumMessage;

public enum DemoExcepEnum implements ExceptionEnumMessage {

    /**
     * 错误枚举值
     */
    ERROR_TEST(10001, "测试错误枚举值"),
    REPEAT_SUBMIT(10002, "请勿重复提交信息");

    /**
     * 错误枚举值
     */
    private Integer errorCode;
    /**
     * 错误枚举信息
     */
    private String errorMsg;


    DemoExcepEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }
}
