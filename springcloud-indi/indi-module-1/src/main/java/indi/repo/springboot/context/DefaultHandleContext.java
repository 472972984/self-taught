package indi.repo.springboot.context;

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

    @Override
    public String getTraceId() {
        return this.traceId;
    }

    @Override
    public String getDate() {
        return this.optDate;
    }
}
