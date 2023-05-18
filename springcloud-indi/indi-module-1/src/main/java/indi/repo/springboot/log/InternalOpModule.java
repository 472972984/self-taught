package indi.repo.springboot.log;

import indi.repo.springboot.log.core.OpModule;

public enum InternalOpModule implements OpModule {

    OTHER("其他操作", null),

    LOGIN("登录", null),

    LOGOUT("退出", null),

    ROLE_MANAGEMENT("角色管理", InternalRoleTypeEnum.ADMIN),

    ORG("组织架构", InternalRoleTypeEnum.ADMIN),

    SAFE_SETTING("认证安全", InternalRoleTypeEnum.ADMIN),

    ASSETS_MANAGER("资产管理", InternalRoleTypeEnum.ADMIN),

    CERTIFICATE_MANAGER("凭证管理", InternalRoleTypeEnum.ADMIN),

    DATASET_REVIEW("数据集审核", InternalRoleTypeEnum.SDO),

    KEY_MANAGER("公钥注册", InternalRoleTypeEnum.TRADER),

    PRIVATE_KEY_CONFIG("私钥配置", InternalRoleTypeEnum.TRADER),

    DATASET_UPLOAD("文件数据集上传", InternalRoleTypeEnum.TRADER),

    UPLOADED_DATASET("上传文件列表", InternalRoleTypeEnum.TRADER),

    DATASOURCE_MANAGE("数据源管理", InternalRoleTypeEnum.TRADER),

    DATASOURCE_TABLE("数据源任务", InternalRoleTypeEnum.TRADER),

    APPLICATION_PROGRAMMING_INTERFACE("API接口", InternalRoleTypeEnum.TRADER),

    CONTRACT_PROF_REVIEW("合约认证", InternalRoleTypeEnum.TRADER),

    CONTRACT_REVIEW("数据集使用授权", InternalRoleTypeEnum.TRADER),

    RESULT_REVIEW("结果集审核", InternalRoleTypeEnum.TRADER),

    RESULT_DOWNLOAD("结果集下载", InternalRoleTypeEnum.TRADER),

    MY_CONTRACT("我的合约", InternalRoleTypeEnum.TRADER),

    PERSONAL_CENTER("个人中心", InternalRoleTypeEnum.TRADER),;

    private final String bizName;

    private final InternalRoleTypeEnum roleType;

    InternalOpModule(String bizName, InternalRoleTypeEnum roleType) {
        this.bizName = bizName;
        this.roleType = roleType;
    }

    @Override
    public String bizName() {
        return bizName;
    }


    public InternalRoleTypeEnum getRoleType() {
        return roleType;
    }

    public static OpModule getByName(String bizName) {
        for (InternalOpModule module : InternalOpModule.values()) {
            if (module.bizName.equals(bizName)) {
                return module;
            }
        }
        return null;
    }

}
