package indi.repo.springboot.common.log.util;

import indi.repo.springboot.common.log.annotation.ImportantField;
import indi.repo.springboot.common.log.compare.FieldBaseEquator;
import indi.repo.springboot.common.log.compare.FieldInfo;
import indi.repo.springboot.common.log.entity.ModifiedField;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ChenHQ
 * @description: 对象比较器
 */
public class OperatorLogUtils {

    /**
     * 比较并返回被修改过的对象属性 ModifiedField
     *
     * @param modifyObject: 修改过后的对象
     * @param oldObject:    旧值对象
     * @return
     */
    public static List<ModifiedField> compareObject(Object modifyObject, Object oldObject) {
        //获取需要比对的字段
        List<String> importFieldName = getImportFieldName(modifyObject);
        //构造比较器
        FieldBaseEquator fieldBaseEquator = new FieldBaseEquator(importFieldName, null);
        //获取值不同的属性
        List<FieldInfo> diffFields = fieldBaseEquator.getDiffFields(modifyObject, oldObject);
        //封装返回对象
        return diffFields.stream().map(diffField -> {
            ModifiedField modifiedField = new ModifiedField();
            modifiedField.setFieldName(diffField.getFieldName());
            modifiedField.setNewValue(diffField.getFirstVal());
            modifiedField.setOldValue(diffField.getSecondVal());

            //获取字段注解中文释义
            modifiedField.setRemark(getRemarkByFieldName(modifyObject, diffField.getFieldName()));
            return modifiedField;
        }).collect(Collectors.toList());
    }


    /**
     * 获取【@ImportantField】注解注释过的属性
     */
    private static List<String> getImportFieldName(Object obj) {
        if (obj == null) {
            throw new RuntimeException("getImportFieldName: 参数不能为空");
        }
        //获取类加载器对象
        Class studentClass = obj.getClass();
        //获取所有的字段
        Field[] declaredFields = studentClass.getDeclaredFields();
        return Stream.of(declaredFields).filter(declaredField ->
                declaredField.isAnnotationPresent(ImportantField.class)).map(Field::getName).collect(Collectors.toList());
    }

    /**
     * 根据传入的对象，和字段名，获取对应注解的中文名称
     */
    private static String getRemarkByFieldName(Object obj, String fieldName) {
        Class<?> aClass = obj.getClass();
        try {
            Field field = aClass.getDeclaredField(fieldName);

            if (field.isAnnotationPresent(ImportantField.class)) {
                ImportantField annotation = field.getAnnotation(ImportantField.class);
                return annotation.title();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
