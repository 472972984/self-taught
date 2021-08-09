package indi.repo.springboot.common.utils;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description: 反射工具类
 */
@UtilityClass
public final class ReflectUtils {

    /**
     * 将对象里的字段拼装成url 例如 ：?sid=CHN821&hotelno=5180481&
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String convertToStr(T t) {
        List<Field> fields = getAllFields(t);
        String url = fields.stream()
                .map(field -> {
                    field.setAccessible(true);
                    String fileName = field.getName();
                    Object value = ReflectionUtils.getField(field, t);
                    if (Objects.isNull(value)) {
                        return null;
                    } else {
                        return fileName + "=" + value.toString();
                    }
                })
                .filter(field1 ->
                        Objects.nonNull(field1))
                .collect(
                        Collectors.joining("&"));
        return "?" + url;
    }

    /**
     * 反射-set值.
     *
     * @param t         操作对象
     * @param fieldName 字段名称
     * @param value     设置的值
     * @param <T>       实体泛型
     */
    public static <T> void setField(T t, String fieldName, Object value) {
        Field field = ReflectionUtils.findField(t.getClass(), fieldName);
        if (field != null) {
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, t, value);
        }
    }

    /**
     * 获取值,支持子对象属性获取，支持多层，不限制层数，例如：子对象名.子对象名.属性.
     *
     * @param t         操作对象
     * @param fieldName 字段名
     * @param <T>       实体泛型
     */
    public static <T> Object getField(T t, String fieldName) {
        Field field = ReflectionUtils.findField(t.getClass(), fieldName);
        if (field != null) {
            ReflectionUtils.makeAccessible(field);
            return ReflectionUtils.getField(field, t);
        }
        // 检查一下字段中是否包含".",如果包含是取子对象的属性值
        String containPoint = ".";
        if (StringUtils.indexOf(fieldName, containPoint) != -1) {
            // 取出"."前面的内容
            String topFieldName = StringUtils.substring(
                    fieldName, 0, StringUtils.indexOf(fieldName, containPoint));
            // 取出"."后面的内容
            String lastFieldName = StringUtils.substring(
                    fieldName, StringUtils.indexOf(fieldName, containPoint) + 1);
            // 反射获取子对象的属性对象,判断对象中是否包含该属性
            Field topField = ReflectionUtils.findField(t.getClass(), topFieldName);
            // 如果包含该属性
            if (topField != null) {
                ReflectionUtils.makeAccessible(topField);
                // 取出子对象的值
                Object topObj = ReflectionUtils.getField(topField, t);
                // 把子对象的值和"."后面的内容传入getField方法中递归，该方法支持多重对象嵌套值检索的方式
                return getField(topObj, lastFieldName);
            }
        }
        return null;
    }

    /**
     * 获取字符串值.
     *
     * @param t         操作对象
     * @param fieldName 字段名
     * @param <T>       实体泛型
     */
    public static <T> String getStringField(T t, String fieldName, String defaultValue) {
        Object fieldValue = getField(t, fieldName);
        if (fieldValue != null) {
            return Objects.toString(fieldValue, defaultValue);
        }
        return defaultValue;
    }

    /**
     * 获取的值为long类型.
     *
     * @param t         操作对象
     * @param fieldName 字段名
     * @param <T>       实体泛型
     */
    public static <T> Long getLongField(T t, String fieldName) {
        Object field = getField(t, fieldName);
        if (field == null) {
            return -1L;
        }
        String val = Objects.toString(field, "");
        return StringUtils.isNotBlank(val) && StringUtils.isNumeric(val)
                ? Long.parseLong(val) : -1L;
    }

    /**
     * 获取的值为long类型.
     *
     * @param t         操作对象
     * @param fieldName 字段名
     * @param <T>       实体泛型
     */
    public static <T> Long getLongField(T t, String fieldName, Long defaultValue) {
        Object field = getField(t, fieldName);
        if (field == null) {
            return defaultValue;
        }
        String val = Objects.toString(field, "");
        return StringUtils.isNotBlank(val) && StringUtils.isNumeric(val)
                ? Long.valueOf(val) : defaultValue;
    }

    /**
     * 获取的值为BigDecimal类型.
     *
     * @param t         操作对象
     * @param fieldName 字段名
     * @param <T>       实体泛型
     */
    public static <T> BigDecimal getBigDecimalField(T t, String fieldName) {
        Object field = getField(t, fieldName);
        if (field == null) {
            return BigDecimal.ZERO;
        }
        String val = Objects.toString(field, "");
        return StringUtils.isNotBlank(val)
                ? BigDecimal.valueOf(Double.valueOf(val)) : BigDecimal.ZERO;
    }

    /**
     * 反射调用方法.
     *
     * @param t          操作对象
     * @param methodName 方法名
     * @param <T>        实体泛型
     */
    public static <T> Object invokeMethod(T t, String methodName) {
        Method method = ReflectionUtils.findMethod(t.getClass(), methodName);
        if (method != null) {
            ReflectionUtils.makeAccessible(method);
            return ReflectionUtils.invokeMethod(method, t);
        }
        return null;
    }

    /**
     * 反射调用方法.
     *
     * @param t          操作对象
     * @param methodName 方法名
     * @param <T>        实体泛型
     */
    public static <T> Object invokeMethod(T t, String methodName, Object... args) {
        Method method = ReflectionUtils.findMethod(t.getClass(), methodName);
        if (method == null) {
            return null;
        }
        ReflectionUtils.makeAccessible(method);
        return ReflectionUtils.invokeMethod(method, t, args);
    }

    /**
     * 反射调用方法.
     *
     * @param t      操作对象
     * @param method 方法对象
     * @param <T>    实体泛型
     */
    public static <T> Object invokeMethod(T t, Method method, Object... args) {
        if (t == null || method == null) {
            return null;
        }
        ReflectionUtils.makeAccessible(method);
        return ReflectionUtils.invokeMethod(method, t, args);
    }

    /**
     * 获得对象的所有属性，包括父类的.
     *
     * @param object 目标对象
     * @return 字段集合
     */
    public static List<Field> getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = Lists.newArrayList();
        while (clazz != null) {
            fieldList.addAll(Lists.newArrayList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }
}
