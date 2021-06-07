package indi.repo.multisource.config;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/7
 * @desc:
 */
public enum DBTypeEnum {
    first("first"), second("second");
    private String value;

    DBTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

