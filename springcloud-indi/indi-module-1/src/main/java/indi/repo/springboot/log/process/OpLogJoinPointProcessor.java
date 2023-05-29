package indi.repo.springboot.log.process;

import indi.repo.springboot.common.log.spring.LogSpringExpression;
import indi.repo.springboot.log.InternalOpModule;
import indi.repo.springboot.log.InternalOpType;
import indi.repo.springboot.log.annotation.OpLog;
import indi.repo.springboot.log.core.OPLogContext;
import indi.repo.springboot.log.core.OpModule;
import indi.repo.springboot.log.core.OpType;
import indi.repo.springboot.log.model.OpLogRecord;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author ChenHQ
 * @date 2023/5/18 13:59
 */

@RequiredArgsConstructor
public class OpLogJoinPointProcessor {

    private final CurrentUserResolver userResolver;

    private final OpLogStorage opLogStorage;


    private final LogSpringExpression logSpringExpression;


    public Object process(ProceedingJoinPoint jp) throws Throwable {

        try {
            Object result;
            try {
                result = jp.proceed();
            } catch (Exception ce) {
                //单独记录commonException
                OPLogContext.putResult(false);
                OPLogContext.putMessage(ce.getMessage());
                OPLogContext.openSwitch();
                processOPLog(jp);
                throw ce;
            }

            //执行opLog插入操作
            processOPLog(jp);

            return result;
        } finally {
            // 清除threadLocal，防止内存泄漏
            if (OPLogContext.getLocal().get() != null) {
                OPLogContext.getLocal().remove();
            }
        }
    }

    /**
     * 处理操作日志
     */
    private void processOPLog(ProceedingJoinPoint joinPoint) throws ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    OpLog opLog = method.getAnnotation(OpLog.class);
                    if (opLog == null) {
                        return;
                    }

                    OpType type = OPLogContext.getOpType();
                    OpModule module = OPLogContext.getOpModule();
                    String message = OPLogContext.getOpMessage();
                    Boolean result = OPLogContext.getOpResult();
                    String account = OPLogContext.getOpAccount();
                    //如果context没有指定值，则从注解里面拿
//                    String describe = opLog.describe().trim();
                    result = result == null || result;
                    //OpType，OpModule目前不支持在注解里面写
                    type = type == null ? InternalOpType.OTHER : type;
                    module = module == null ? InternalOpModule.OTHER : module;
                    message = StringUtils.isBlank(message) ? opLog.message() : message;
                    message = StringUtils.isBlank(message) ? type.bizName() : message;
                    message = result && StringUtils.isNotBlank(opLog.messageSuffix()) ? message + "，" + opLog.messageSuffix() : message;
                    //操作人身份解析由上层实现
                    IUser iUser = userResolver.getCurrentUser();
                    if (iUser != null && StringUtils.isBlank(account)) {
                        account = userResolver.getCurrentUser().getAccountName();
                    }
                    if (StringUtils.isBlank(account)) {
                        // quick fix: bugID 190474
                        // TODO: 无账号信息选择性继续
                        //  return;
                    }
                    OpLogRecord opLogRecord = new OpLogRecord();
                    //todo 其实这边用spring的el表达式解析器更好，有空再改
//                    opLogRecord.setDesc(this.analyzeParams(describe));
                    opLogRecord.setMessage(this.analyzeParams(message));

                    opLogRecord.setOpModule(module.bizName());
                    opLogRecord.setOpType(type.bizName());
                    opLogRecord.setSuccess(result);
                    opLogRecord.setUsername(account);
                    opLogRecord.setCreateTime(new Date());
                    opLogRecord.setIp(userResolver.getRequestIp());
                    opLogRecord.setTenantId(userResolver.getTenantId());
                    opLogStorage.put(opLogRecord);
                }
            }
        }
    }


    /**
     * 处理参数中的变量  "{xxx}"
     */
    private String analyzeParams(String str) {
        if (str == null) {
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        String[] split1 = str.split("\\{");
        for (String aSplit1 : split1) {
            if (aSplit1.contains("}")) {
                String[] split = aSplit1.split("\\}");
                list.add(split[0]);
            }
        }
        for (String params : list) {
            Object obj = OPLogContext.get(params);
            if (obj != null) {
                str = str.replace("{" + params + "}", obj.toString());
            }
        }
        return str;
    }
}