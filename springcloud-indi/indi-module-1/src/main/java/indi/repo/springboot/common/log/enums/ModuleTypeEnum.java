package indi.repo.springboot.common.log.enums;


/**
 * @author ChenHQ
 * @title: ModuleTypeEnum
 * 日志模块枚举类
 */
public enum ModuleTypeEnum {

    STUDENT("STUDENT", "学生管理")
   ;


    private String moduleType;

    private String value;

    ModuleTypeEnum(String moduleType, String value) {
        this.moduleType = moduleType;
        this.value = value;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    /**
     * 根据传入的模块类型，获取中文
     */
    public static String getValueByModuleType(String moduleType){
        return ModuleTypeEnum.valueOf(moduleType).getValue();
    }


}
