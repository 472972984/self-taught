package indi.repo.studentservice.module;

import com.github.dadiyang.equator.AbstractEquator;
import com.github.dadiyang.equator.FieldInfo;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author ChenHQ
 * @date 2022/3/31 18:43
 */
public class MyFieldBaseEquator extends AbstractEquator {
    public MyFieldBaseEquator() {
    }

    /**
     * 指定包含或排除某些字段
     *
     * @param includeFields 包含字段，若为 null 或空集，则不指定
     * @param excludeFields 排除字段，若为 null 或空集，则不指定
     */
    public MyFieldBaseEquator(List<String> includeFields, List<String> excludeFields) {
        super(includeFields, excludeFields);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FieldInfo> getDiffFields(Object first, Object second) {
        if (first == second) {
            return Collections.emptyList();
        }
        // 先尝试判断是否为简单数据类型
        if (isSimpleField(first, second)) {
            return compareSimpleField(first, second);
        }
        Object obj = first == null ? second : first;
        Class<?> clazz = obj.getClass();
        List<FieldInfo> diffField = new LinkedList<>();
        // 获取所有字段
        Field[] fields = clazz.getDeclaredFields();
        // 遍历所有的字段
        for (Field field : fields) {
            String fieldName = field.getName();
            try {
                // 开启访问权限，否则获取私有字段会报错
                field.setAccessible(true);
                Object firstVal = first == null ? null : field.get(first);
                Object secondVal = second == null ? null : field.get(second);
                if (WRAPPER.contains(field.getType())) {
                    // 封装字段信息
                    FieldInfo fieldInfo = new FieldInfo(fieldName, field.getType(), firstVal, secondVal);
                    boolean eq = isFieldEquals(fieldInfo);
                    if (!eq) {
                        // 记录不相等的字段
                        diffField.add(fieldInfo);
                    }
                } else {
                    diffField.addAll(getDiffFields(firstVal, secondVal));
                }
            } catch (IllegalAccessException e) {
                // 只要调用了 field.setAccessible(true) 就不会报这个异常
                throw new IllegalStateException("获取属性进行比对发生异常: " + fieldName, e);
            }
        }
        return diffField;
    }

    /**
     * 如果简单数据类型的对象则直接进行比对
     *
     * @param first  对象1
     * @param second 对象2
     * @return 不同的字段信息，相等返回空集，不等则 FieldInfo 的字段名为对象的类型名称
     */
    List<FieldInfo> compareSimpleField(Object first, Object second) {
        boolean eq = Objects.equals(first, second);
        if (eq) {
            return Collections.emptyList();
        } else {
            Object obj = first == null ? second : first;
            Class<?> clazz = obj.getClass();
            // 不等的字段名称使用类的名称
            return Collections.singletonList(new FieldInfo(clazz.getSimpleName(), clazz, first, second));
        }
    }

    private static final List<Class<?>> WRAPPER = Arrays.asList(Byte.class, Short.class,
            Integer.class, Long.class, Float.class, Double.class, Character.class,
            Boolean.class, String.class);

    /**
     * 判断是否为原始数据类型
     *
     * @param first  对象1
     * @param second 对象2
     * @return 是否为原始数据类型
     */
    boolean isSimpleField(Object first, Object second) {
        Object obj = first == null ? second : first;
        Class<?> clazz = obj.getClass();
        return clazz.isPrimitive() || WRAPPER.contains(clazz);
    }
}
