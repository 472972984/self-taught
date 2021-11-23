package indi.repo.springboot.common.log.entity;

import lombok.Data;

/**
 * @author ChenHQ
 * @date: create in 2021/11/21
 */
@Data
public class ModifiedField {

    /**
     * 被修改的字段名
     */
    private String fieldName;

    /**
     * 被修改的旧值
     */
    private Object oldValue;
    /**
     * 被修改的新值
     */
    private Object newValue;

    /**
     * 被修改的字段名中文解释
     */
    private String remark;

}