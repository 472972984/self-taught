package indi.repo.springboot.common.log.enums;

/**
 * @author ChenHQ
 * @title: OptTypeEnum
 * 操作类型枚举类
 */
public enum OptTypeEnum {

    UPDATE("UPDATE","修改"),DELETE("DELETE","删除"),ADD("ADD","新增");

    private String optType;

    private String value;

    OptTypeEnum(String optType, String value) {
        this.optType = optType;
        this.value = value;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    /**
     * 根据传入的操作类型，获取中文
     */
    public static String getValueByOptType(String optType){
        return OptTypeEnum.valueOf(optType).getValue();
    }

}
