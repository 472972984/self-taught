package indi.repo.springboot.common.log.aop;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.repo.common.utils.SpringUtils;
import indi.repo.springboot.common.log.annotation.UpdateEntityLog;
import indi.repo.springboot.common.log.entity.ModifiedField;
import indi.repo.springboot.common.log.spring.LogSpringExpression;
import indi.repo.springboot.common.log.util.OperatorLogUtils;
import indi.repo.springboot.common.manager.AsyncFactory;
import indi.repo.springboot.common.manager.AsyncManager;
import indi.repo.springboot.core.context.LocalHandleContext;
import indi.repo.springboot.entity.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ChenHQ
 * @date: create in 2021/11/23
 */
@Aspect
@Configuration
@Slf4j
public class UpdateEntityLogAop {

    private LogSpringExpression logSpringExpression = new LogSpringExpression();

    @Pointcut("@annotation(indi.repo.springboot.common.log.annotation.UpdateEntityLog)")
    public void updatePoint() {
    }


    @Around("updatePoint() && @annotation(updateEntityLog)")
    public Object test(ProceedingJoinPoint joinPoint, UpdateEntityLog updateEntityLog) throws Throwable {

        Object[] args = joinPoint.getArgs();

        Object result = null;

        SystemLog systemLog = null;
        Throwable t = null;
        try {
            try {
                Object target = joinPoint.getTarget();
                Class<?> targetClass = target.getClass();
                Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
                //解析后的id
                String id = logSpringExpression.matcherValue(updateEntityLog.id(), updateEntityLog, targetClass, method, args);

                Object mapperObj = SpringUtils.getBean(updateEntityLog.modifyClass());
                Object preObj = ((BaseMapper) mapperObj).selectById(id);

                //要求被修改的对象必须在方法第一个参数位上
                List<ModifiedField> modifiedFields = OperatorLogUtils.compareObject(args[0], preObj);
                System.out.println("modifiedFields = " + modifiedFields);
                //创建日志对象
                systemLog = SystemLogInstance(modifiedFields,updateEntityLog,id);
            } catch (BeansException e) {
                log.error("【记录修改日志异常：{}】",e.getMessage());
            }

            //执行目标方法
            result = joinPoint.proceed(args);

        } catch (Throwable e) {
            //业务出现异常处理
            t = e;
            throw e;
        }finally {
            //异步入库
            if (Objects.nonNull(systemLog) && Objects.isNull(t)) {
                AsyncManager.getInstance().execute(AsyncFactory.insertXfjWxSyncException(systemLog));
            }
        }
        return result;
    }

    /**
     * 构建操作日志对象
     * @return
     */
    private SystemLog SystemLogInstance(List<ModifiedField> modifiedFields, UpdateEntityLog updateEntityLog, String id) {

        String userId = LocalHandleContext.getHandleContext().getUserId();
        String username = LocalHandleContext.getHandleContext().getUsername();

        List<String> contentList = OperatorLogUtils.getContentList(modifiedFields);
        String fieldContent = OperatorLogUtils.getModifiedFieldContent(modifiedFields);

        return SystemLog.builder()
                .detailContent("修改了【id = " + id + "】" + fieldContent)
                .contentJson(JSON.toJSONString(contentList))
                .moduleType(updateEntityLog.type())
                .createTime(new Date())
                .optType(updateEntityLog.optType().getValue())
                .userId(userId)
                .username(username)
                .build();
    }

}
