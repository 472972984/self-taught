package indi.repo.springboot.log;


import indi.repo.springboot.log.core.OpModule;
import indi.repo.springboot.log.core.OpType;

public enum InternalOpType implements OpType {

    OTHER("其他操作", InternalOpModule.OTHER),

    LOGIN("登录", InternalOpModule.LOGIN),

    LOGOUT("退出", InternalOpModule.LOGOUT),

    DELETE_ROLE("删除角色", InternalOpModule.ROLE_MANAGEMENT),

    ADD_ROLE("添加角色", InternalOpModule.ROLE_MANAGEMENT),

    UPDATE_ROLE("编辑角色", InternalOpModule.ROLE_MANAGEMENT),

    ADD_ORG("新增组织", InternalOpModule.ORG),

    DELETE_ORG("删除组织", InternalOpModule.ORG),

    ADD_USER("新增用户", InternalOpModule.ORG),

    UPDATE_USER("编辑用户", InternalOpModule.ORG),

    DELETE_USER("删除用户", InternalOpModule.ORG),

    LOCK_USER("锁定用户", InternalOpModule.ORG),

    UNLOCK_USER("重启用户", InternalOpModule.ORG),

    MODIFY_LOGIN_SETTING("修改配置", InternalOpModule.SAFE_SETTING),

    CREATE_CERTIFICATE("新增凭证", InternalOpModule.CERTIFICATE_MANAGER),

    DOWNLOAD_CERTIFICATE("下载凭证", InternalOpModule.CERTIFICATE_MANAGER),

    UPDATE_CERTIFICATE("更新凭证", InternalOpModule.CERTIFICATE_MANAGER),

    DELETE_CERTIFICATE("废除凭证", InternalOpModule.CERTIFICATE_MANAGER),

    DELETE_ASSETS("删除资产", InternalOpModule.ASSETS_MANAGER),

    ASSETS_BIND_ORG("绑定组织", InternalOpModule.ASSETS_MANAGER),

    PRIVATE_KEY_HOSTING("私钥托管", InternalOpModule.PRIVATE_KEY_CONFIG),

    PRIVATE_KEY_DOWNLOAD("下载私钥", InternalOpModule.PRIVATE_KEY_CONFIG),

    PUBLIC_KEY_REGISTER("注册公钥", InternalOpModule.PRIVATE_KEY_CONFIG),

    PASS_DATASET_REVIEW("审核通过", InternalOpModule.DATASET_REVIEW),

    REFUSE_DATASET_REVIEW("审核拒绝", InternalOpModule.DATASET_REVIEW),

    CREATE_KEY("初始化公钥", InternalOpModule.KEY_MANAGER),

    UPDATE_KEY("更新公钥", InternalOpModule.KEY_MANAGER),

    UPLOAD_COMMAND("生成上传命令", InternalOpModule.DATASET_UPLOAD),

    UPLOAD_MINIO("确认数据上传完成", InternalOpModule.DATASET_UPLOAD),

    UPLOAD_SYNC("数据同步进内网", InternalOpModule.DATASET_UPLOAD),

    UPLOAD_CLEAN("销毁暂存数据", InternalOpModule.DATASET_UPLOAD),

    UPLOAD_GIVE_UP("取消上传", InternalOpModule.DATASET_UPLOAD),

    VIEW_UPLOADED_DATASET("查看数据集详情", InternalOpModule.UPLOADED_DATASET),

    DATASET_UPDATE("编辑数据集", InternalOpModule.DATASET_UPLOAD),

    DELETED_UPLOADED_DATASET("删除数据集", InternalOpModule.UPLOADED_DATASET),

    UPDATE_UPLOADED_DATASET_SCHEMA("修改数据表结构", InternalOpModule.UPLOADED_DATASET),

    UPDATE_UPLOADED_DATASET_FILE("更新数据集", InternalOpModule.UPLOADED_DATASET),

    DOWNLOAD_DEBUG_DATA("下载调试数据", InternalOpModule.UPLOADED_DATASET),

    UPDATE_DEBUG_DATA("更新调试数据", InternalOpModule.UPLOADED_DATASET),

    DATASOURCE_TABLE_DATASET_DATASOURCE_ADD("新增数据源", InternalOpModule.DATASOURCE_MANAGE),

    DATASOURCE_TABLE_DATASET_DATASOURCE_UPDATE("编辑数据源", InternalOpModule.DATASOURCE_MANAGE),

    DATASOURCE_TABLE_DATASET_DATASOURCE_DELETE("删除数据源", InternalOpModule.DATASOURCE_MANAGE),

    DATASOURCE_TABLE_DETAIL_VIEW("查看数据源任务详情", InternalOpModule.DATASOURCE_TABLE),

    DATASOURCE_UPDATE("编辑数据集", InternalOpModule.DATASOURCE_TABLE),

    DATASOURCE_TABLE_DATASET_DATASOURCETABLE_ADD("新增数据源任务", InternalOpModule.DATASOURCE_TABLE),

    DATASOURCE_TABLE_DATASET_DATASOURCETABLE_UPDATE("修改数据表结构", InternalOpModule.DATASOURCE_TABLE),

    DATASOURCE_TABLE_DATASET_DATASOURCETABLE_DELETE("删除数据源任务", InternalOpModule.DATASOURCE_TABLE),

    DATASOURCE_TABLE_DATASET_DATASOURCETABLE_EXECUTE("启动数据源任务（手动更新）", InternalOpModule.DATASOURCE_TABLE),

    DATASOURCE_DOWNLOAD_DEBUG_DATA("下载调试数据", InternalOpModule.DATASOURCE_TABLE),

    DATASOURCE_UPDATE_DEBUG_DATA("更新调试数据", InternalOpModule.DATASOURCE_TABLE),

    CREATE_API("新建API接口", InternalOpModule.APPLICATION_PROGRAMMING_INTERFACE),

    DELETE_API("删除API接口", InternalOpModule.APPLICATION_PROGRAMMING_INTERFACE),

    UPDATE_API("编辑API接口", InternalOpModule.APPLICATION_PROGRAMMING_INTERFACE),

    PASS_CONTRACT_PROF_REVIEW("通过合约审批", InternalOpModule.CONTRACT_PROF_REVIEW),

    BATCH_PASS_CONTRACT_PROF_REVIEW("批量通过合约认证", InternalOpModule.CONTRACT_PROF_REVIEW),

    REFUSE_CONTRACT_PROF_REVIEW("拒绝合约审批", InternalOpModule.CONTRACT_PROF_REVIEW),

    BATCH_REFUSE_CONTRACT_PROF_REVIEW("批量拒绝合约认证", InternalOpModule.CONTRACT_PROF_REVIEW),

    PASS_CONTRACT_REVIEW("通过使用授权", InternalOpModule.CONTRACT_REVIEW),

    REFUSE_CONTRACT_REVIEW("拒绝使用授权", InternalOpModule.CONTRACT_REVIEW),

    PASS_RESULT_REVIEW("通过结果集审批", InternalOpModule.RESULT_REVIEW),

    BATCH_PASS_RESULT_REVIEW("批量通过结果集审批", InternalOpModule.RESULT_REVIEW),

    REFUSE_RESULT_REVIEW("拒绝结果集审批", InternalOpModule.RESULT_REVIEW),

    BATCH_REFUSE_RESULT_REVIEW("批量拒绝结果集审批", InternalOpModule.RESULT_REVIEW),

    DOWNLOAD_SYNC("同步结果集至DMZ域", InternalOpModule.RESULT_DOWNLOAD),

    DOWNLOAD_MINIO("结果集下载完成", InternalOpModule.RESULT_DOWNLOAD),

    DOWNLOAD_CLEAN("销毁暂存结果集数据", InternalOpModule.RESULT_DOWNLOAD),

    DOWNLOAD_RESUME("恢复下载流程", InternalOpModule.RESULT_DOWNLOAD),

    DOWNLOAD_GIVE_UP("放弃恢复下载流程", InternalOpModule.RESULT_DOWNLOAD),

    APPLY_CONTRACT("新增合约", InternalOpModule.MY_CONTRACT),

    UPDATE_CONTRACT("修改合约", InternalOpModule.MY_CONTRACT),

    CREATE_DEBUG_SANDBOX("创建TEE", InternalOpModule.MY_CONTRACT),

    ENTER_DEBUG_SANDBOX("进入调试TEE", InternalOpModule.MY_CONTRACT),

    COMPLETE_DEBUG_SANDBOX("完成调试", InternalOpModule.MY_CONTRACT),

    RESET_DEBUG_SANDBOX("重置调试TEE", InternalOpModule.MY_CONTRACT),

    EXECUTE_SANDBOX("手动执行合约", InternalOpModule.MY_CONTRACT),

    FINISH_CONTRACT("提前终止合约", InternalOpModule.MY_CONTRACT),

    UPDATE_PERSONAL_INFORMATION("修改个人信息", InternalOpModule.PERSONAL_CENTER),
    ;

    private final String bizName;

    private final OpModule opModule;

    InternalOpType(String bizName, OpModule opModule) {
        this.bizName = bizName;
        this.opModule = opModule;
    }

    @Override
    public OpModule module() {
        return opModule;
    }

    @Override
    public String bizName() {
        return bizName;
    }


    public static InternalOpType getByName(String bizName) {
        for (InternalOpType type : InternalOpType.values()) {
            if (type.bizName.equals(bizName)) {
                return type;
            }
        }
        return null;
    }

}
