package indi.repo.springboot.common.manager;


import lombok.extern.slf4j.Slf4j;

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
     * 异步插入同步网新异常数据错误
     * @param wxSyncExceptionDo
     */
/*    public static TimerTask insertXfjWxSyncException(final XfjWxSyncExceptionDo wxSyncExceptionDo) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    SpringUtils.getBean(XfjWxSyncExceptionMapper.class).insert(wxSyncExceptionDo);
                } catch (Exception e) {
                    log.error("数据库索引约束异常 : {}", e.getMessage());
                }
            }
        };
    }*/


}
