package indi.repo.springboot.common.log.compare;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 基于属性的比对器
 * @author admin
 */
public class FieldBaseEquator extends AbstractEquator {
    public FieldBaseEquator() {
    }

    /**
     * 指定包含或排除某些字段
     *
     * @param includeFields 包含字段，若为 null 或空集，则不指定
     * @param excludeFields 排除字段，若为 null 或空集，则不指定
     */
    public FieldBaseEquator(List<String> includeFields, List<String> excludeFields) {
        super(includeFields, excludeFields);
    }

    /**
     * 获取不同的属性
     */
    @Override
    public List<FieldInfo> getDiffFields(Object first, Object second) {
        Assert.notNull(first,"参数不能null");
        Assert.notNull(second,"参数不能null");

        if (first == second) {
            return Collections.emptyList();
        }
        // 先尝试判断是否为简单数据类型
        if (isSimpleField(first, second)) {
            return compareSimpleField(first, second);
        }

        return getFieldInfos(first, second);
    }

    private List<FieldInfo> getFieldInfos(Object first, Object second) {

        List<FieldInfo> diffField = new LinkedList<>();
        Class<?> clazzFirst = first.getClass();
        Class<?> clazzSecond = second.getClass();
        // 获取所有字段
        Field[] fields = clazzFirst.getDeclaredFields();

        // 遍历所有的字段
        for (Field firstField : fields) {
            String fieldName = firstField.getName();

            try {
                Field secondField = clazzSecond.getDeclaredField(fieldName);
                firstField.setAccessible(true);
                secondField.setAccessible(true);
                // 开启访问权限，否则获取私有字段会报错
                Object firstVal = first == null ? null : firstField.get(first);
                Object secondVal = second == null ? null : secondField.get(second);
                if (AbstractEquator.getWrapper().contains(firstField.getType())) {
                    // 封装字段信息
                    FieldInfo fieldInfo = new FieldInfo(fieldName, firstField.getType(), firstVal, secondVal);
                    boolean eq = isFieldEquals(fieldInfo);
                    if (!eq) {
                        // 记录不相等的字段
                        diffField.add(fieldInfo);
                    }
                } else {
                    diffField.addAll(getDiffFields(firstVal, secondVal));
                }
            } catch (IllegalAccessException e) {
                // 只要调用了 firstField.setAccessible(true) 就不会报这个异常
                throw new IllegalStateException("获取属性进行比对发生异常: " + fieldName, e);
            } catch (NoSuchFieldException e) {
            }
        }
        return diffField;
    }


}
