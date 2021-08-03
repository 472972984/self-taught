package indi.repo.springboot.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import indi.repo.springboot.core.context.LocalHandleContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自动填充创建时间/修改时间/创建人/修改人
 *
 * @author
 * @date 2020-07-17
 */
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //插入数据填充
        try {
            String userId = LocalHandleContext.getHandleContext().getUserId() + "";
            setFieldValByName("createBy", userId, metaObject);
            setFieldValByName("createTime", new Date(), metaObject);
        } catch (Exception e) {
            log.error("自动填充插入数据异常：{}",e);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            // 更新填充
            String userId = LocalHandleContext.getHandleContext().getUserId() + "";
            setFieldValByName("updateBy", userId, metaObject);
            setFieldValByName("updateTime", new Date(), metaObject);
        } catch (Exception e) {
            log.error("自动填充更新数据异常：{}",e);
        }
    }
}
