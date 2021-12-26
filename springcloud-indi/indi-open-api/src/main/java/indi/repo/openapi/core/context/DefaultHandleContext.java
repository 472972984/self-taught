package indi.repo.openapi.core.context;

import lombok.Data;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date 2021/12/16
 * @desc:
 */
@Data
public class DefaultHandleContext implements HandleContext {

    private static final long serialVersionUID = 24952334547972807L;

    /**
     * 请求id
     */
    private String traceId;

    /**
     * 商家key
     * app_key
     */
    private String appKey;

    /**
     * 版本号
     */
    private String version;

    /**
     * 摘要算法
     * sign_method
     */
    private String signMethod;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 调用方提交上来的：摘要算法签名值
     */
    private String sign;

    /**
     * 商家secret
     */
    private String secret;

    /**
     * TODO: 暂未接入
     * 对称加密秘钥，加密时上传该字段 random_key
     */
    private String randomKey;

    /**
     * TODO: 暂未接入
     * encrypt_parm 加密字段集合，例如：foo,bar,foorBar
     */
    private String encryptParm;

    /**
     * 映射method
     */
    private String method;

}
