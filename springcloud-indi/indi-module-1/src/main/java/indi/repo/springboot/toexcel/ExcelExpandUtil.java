package indi.repo.springboot.toexcel;

import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author ChenHQ
 * @date 2025/11/3 16:33
 */
public class ExcelExpandUtil {

    public static List<Map<String, Object>> expandObjects(List<?> dataList) {
        return expandObjects(dataList, 1, 4);
    }

    public static List<Map<String, Object>> expandObjects(List<?> dataList, int currentDepth, int maxDepth) {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object obj : dataList) {
            if (obj == null) {
                continue;
            }

            Map<String, Object> flatMap = new LinkedHashMap<>();
            expandObject("", obj, flatMap, currentDepth, maxDepth);
            result.add(flatMap);
        }

        return result;
    }

    private static void expandObject(String prefix, Object obj, Map<String, Object> flatMap,
                                     int currentDepth, int maxDepth) {
        if (obj == null || currentDepth > maxDepth) {
            return;
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = getAllFields(clazz);

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            field.setAccessible(true);
            String fieldName = getFieldName(field);
            String fullKey = prefix.isEmpty() ? fieldName : prefix + "." + fieldName;

            try {
                Object value = field.get(obj);

                if (value == null) {
                    flatMap.put(fullKey, "");
                } else if (isSimpleType(value.getClass())) {
                    flatMap.put(fullKey, value);
                } else if (value instanceof Collection) {
                    // 处理集合
                    handleCollection(fullKey, (Collection<?>) value, flatMap, currentDepth, maxDepth);
                } else if (value instanceof Map) {
                    // 处理Map
                    handleMap(fullKey, (Map<?, ?>) value, flatMap, currentDepth, maxDepth);
                } else {
                    // 递归处理嵌套对象
                    expandObject(fullKey, value, flatMap, currentDepth + 1, maxDepth);
                }

            } catch (IllegalAccessException e) {
                // 忽略无法访问的字段
            }
        }
    }

    private static void handleCollection(String prefix, Collection<?> collection,
                                         Map<String, Object> flatMap, int currentDepth, int maxDepth) {
        if (collection.isEmpty()) {
            flatMap.put(prefix, "");
            return;
        }

        int index = 0;
        for (Object item : collection) {
            String itemPrefix = prefix + "[" + index + "]";
            if (isSimpleType(item.getClass())) {
                flatMap.put(itemPrefix, item);
            } else {
                expandObject(itemPrefix, item, flatMap, currentDepth + 1, maxDepth);
            }
            index++;
            if (index >= 10) { // 限制最大展开数量
                break;
            }
        }
    }

    private static void handleMap(String prefix, Map<?, ?> map, Map<String, Object> flatMap,
                                  int currentDepth, int maxDepth) {
        if (map.isEmpty()) {
            flatMap.put(prefix, "");
            return;
        }

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = String.valueOf(entry.getKey());
            String mapPrefix = prefix + "[" + key + "]";
            Object value = entry.getValue();

            if (value == null || isSimpleType(value.getClass())) {
                flatMap.put(mapPrefix, value);
            } else {
                expandObject(mapPrefix, value, flatMap, currentDepth + 1, maxDepth);
            }
        }
    }

    private static boolean isSimpleType(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz == String.class ||
                Number.class.isAssignableFrom(clazz) ||
                clazz == Boolean.class ||
                clazz == Character.class ||
                clazz == Date.class ||
                clazz == LocalDate.class ||
                clazz == LocalDateTime.class;
    }

    private static Field[] getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null && clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields.toArray(new Field[0]);
    }

    private static String getFieldName(Field field) {
        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
        if (excelProperty != null && excelProperty.value().length > 0) {
            return excelProperty.value()[0]; // 取第一个作为字段名
        }
        return field.getName();
    }
}