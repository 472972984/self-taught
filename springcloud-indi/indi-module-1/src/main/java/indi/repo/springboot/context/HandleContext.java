package indi.repo.springboot.context;

import java.io.Serializable;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/19
 * @desc:
 */
public interface HandleContext extends Cloneable, Serializable {

    /**
     * 操作id
     * @return
     */
    String getTraceId();

    /**
     * 操作时间
     * @return
     */
    String getDate();

}
