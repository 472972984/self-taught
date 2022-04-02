package indi.repo.openapi.core.context;

import java.io.Serializable;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date 2021/12/16
 */
public interface HandleContext extends Cloneable, Serializable {

    /**
     * 唯一请求id
     * @return
     */
    String getTraceId();

    /**
     * 当前版本号
     * @return
     */
    String getVersion();

    /**
     * 当前摘要算法
     * @return
     */
    String getSignMethod();

    /**
     * 获取商家appkey
     * @return
     */
    String getAppKey();

    /**
     * appkey对应密钥
     * @return
     */
    String getSecret();

    /**
     * 获取调用时间戳
     * @return
     */
    String getTimestamp();

    /**
     * 获取
     * @return
     */
    String getSign();

    /**
     * 对称加密秘钥
     * @return
     */
    String getRandomKey();

    /**
     * 加密字段集合
     * @return
     */
    String getEncryptParm();

    /**
     * 映射method
     * @return
     */
    String getMethod();
}
