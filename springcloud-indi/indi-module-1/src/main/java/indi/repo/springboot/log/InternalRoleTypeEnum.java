package indi.repo.springboot.log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum InternalRoleTypeEnum {

    ADMIN(1, "系统管理员", "ROLE_ADMIN"),

    SDO(2, "SDO账号", "ROLE_SDO"),

    TRADER(3, "数据交易方", "ROLE_TRADER");

    /**
     * 对应flyway脚本的主键id
     */
    private final Integer roleTypeId;

    /**
     * 中文名称
     */
    private final String roleName;

    /**
     * spring security会用到
     */
    private final String roleCode;

    InternalRoleTypeEnum(Integer roleTypeId, String roleName, String roleCode) {
        this.roleTypeId = roleTypeId;
        this.roleName = roleName;
        this.roleCode = roleCode;
    }

    @JsonValue
    public Integer getRoleTypeId() {
        return roleTypeId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    @JsonCreator
    public static InternalRoleTypeEnum getByCode(Integer roleTypeId) {
        InternalRoleTypeEnum[] var1 = values();
        for (InternalRoleTypeEnum item : var1) {
            if (Objects.equals(item.roleTypeId, roleTypeId)) {
                return item;
            }
        }
        return null;
    }

}
