package indi.repo.springboot.core.context;

import lombok.Data;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/19
 * @desc:
 */
@Data
public class DefaultHandleContext implements HandleContext {

    private static final long serialVersionUID = 24952334547972807L;

    private String traceId;

    private String optDate;

    private String userId;

    @Override
    public String getTraceId() {
        return this.traceId;
    }

    @Override
    public String getDate() {
        return this.optDate;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }
}
