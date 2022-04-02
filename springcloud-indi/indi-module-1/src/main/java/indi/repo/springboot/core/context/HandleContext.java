package indi.repo.springboot.core.context;

import java.io.Serializable;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/19
 */
public interface HandleContext extends Cloneable, Serializable {

    /**
     * 操作id
     * @return 操作id
     */
    String getTraceId();

    /**
     * 操作时间
     * @return 操作时间
     */
    String getDate();

    /**
     * 当前用户id
     * @return 当前用户id
     */
    String getUserId();

    /**
     * 当前用户名称
     * @return 当前用户名称
     */
    String getUsername();
}
