package indi.repo.springboot.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 操作对象工具
 *
 * @author YangYi
 * @version v1.0
 * @date 2019 /11/25
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * Copy properties.
     *
     * @param source the source
     * @param target the target
     * @throws BeansException the beans exception
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        copyProperties(source, target, null, (String[]) null);
    }

    private static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties)
            throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName()
                        + "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                            if (value != null) {
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }


    /**
     * 复制对象
     *
     * @param <T>       the type parameter
     * @param entity    the entity
     * @param targetCls the target cls
     * @return t t
     */
    public static <T> T copyObject(Object entity, Class<? extends T> targetCls) {
        // 如果entity,直接返回null
        if (entity == null) {
            return null;
        }
        Object target = null;
        try {
            target = targetCls.newInstance();
            copyProperties(entity, target);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) target;
    }

    /**
     * 复制对象
     *
     * @param list      the list
     * @param targetCls the target cls
     * @return list list
     */
    public static <T> List<T> copyList(List list, Class<? extends T> targetCls) {
        List<T> resultList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Object entity : list) {
                resultList.add(copyObject(entity, targetCls));
            }
        }
        return resultList;
    }

}
