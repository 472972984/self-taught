package indi.repo.springboot.common.manager;


import indi.repo.springboot.common.utils.SpringUtils;
import indi.repo.springboot.entity.SystemLog;
import indi.repo.springboot.mapper.SystemLogMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/8/31
 * @desc:
 */
@Slf4j
public class AsyncFactory {


    /**
     * 异步插入
     * @param
     */
    public static TimerTask insertXfjWxSyncException(final SystemLog systemLog) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    SpringUtils.getBean(SystemLogMapper.class).insert(systemLog);
                } catch (Exception e) {
                    log.error("数据库异常 : {}", e.getMessage());
                }
            }
        };
    }


}
