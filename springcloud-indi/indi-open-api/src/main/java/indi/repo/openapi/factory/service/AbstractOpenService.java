package indi.repo.openapi.factory.service;

import cn.hutool.core.bean.BeanUtil;
import indi.repo.openapi.base.HdeRequest;
import indi.repo.openapi.common.Result;
import indi.repo.openapi.core.context.ApplicationStartupRunner;
import indi.repo.openapi.core.context.LocalHandleContext;
import indi.repo.openapi.factory.HdeOpenService;

import java.lang.reflect.Method;

/**
 * @author ChenHQ
 * @description: 抽象实现
 * @date 2021/12/24 14:48
 */
public abstract class AbstractOpenService implements HdeOpenService {

    /**
     * 根据反射进行优化
     * 根据接口名称【method：query.student，则方法名：queryStudent】进行区分
     * @param request     请求参数
     * @param <T>
     * @return
     */
    public <T extends Result> T execute(HdeRequest request, Object obj) {
        String method = LocalHandleContext.getHandleContext().getMethod();
        //待执行的方法名
        String methodName = getMethodName(method);
        //待执行的参数class
        Class paramClass = ApplicationStartupRunner.getClassRpcParamMap().get(method);
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod(methodName, paramClass);
            Object o = BeanUtil.copyProperties(request, paramClass);
            Object invoke = declaredMethod.invoke(obj, o);
            return (T)invoke;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("服务器繁忙");
    }


    /**
     * 【query.student】获取对应的方法名 —> queryStudent
     * @param method
     * @return
     */
    private String getMethodName(String method) {
        String[] split = method.split("\\.");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < split.length; i++) {
            String methodName = split[i];
            if (i == 0) {
                sb.append(methodName);
            } else {
                sb.append(methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
            }
        }
        return sb.toString();
    }

}
