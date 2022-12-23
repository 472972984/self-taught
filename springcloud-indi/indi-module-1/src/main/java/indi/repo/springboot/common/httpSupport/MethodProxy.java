package indi.repo.springboot.common.httpSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author ChenHQ
 * @date 2022/8/31 10:46
 */
public class MethodProxy implements InvocationHandler {


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //如果传进来是一个已实现的具体类（本次演示略过此逻辑)
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                t.printStackTrace();
            }

        } else {
            //如果传进来的是一个接口（核心)
            return run(method, args);
        }

        return null;
    }

    private Object run(Method method, Object[] args) {
        System.out.println(method.getName());
        System.out.println(Arrays.asList(args));

        return "hello";
    }


}
