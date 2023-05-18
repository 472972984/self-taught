package indi.repo.springboot.log.model;

import lombok.Data;

import java.util.Date;

@Data
public class OpLogRecord {

    private String opType;

    private String opModule;

    private String desc;

    private String message;

    private boolean success;

    private String username;

    private Date createTime;

    private String ip;

    private String tenantId;

}
