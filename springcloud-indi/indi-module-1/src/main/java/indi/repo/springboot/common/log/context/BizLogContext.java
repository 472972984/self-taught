package indi.repo.springboot.common.log.context;


import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author admin
 */
@Slf4j
public class BizLogContext {

    private static final ThreadLocal<Map<Class, Object>> previousHolder = ThreadLocal.withInitial(() -> new HashMap<>());

    private static final ThreadLocal<Map<Class, Object>> currentHolder = ThreadLocal.withInitial(() -> new HashMap<>());

    private static final ThreadLocal<Map<String, Object>> variablesHolder = ThreadLocal.withInitial(() -> new HashMap<>());


    public static void setPreviousModel(Supplier<?> supplier) {
        try {
            Object obj = supplier.get();
            if (obj == null) {
                log.warn("bizLog  previous model can not be null");
                return;
            }
            Map<Class, Object> map = previousHolder.get();
            map.put(obj.getClass(), obj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static Map<Class, Object> getPreviousModel() {
        return previousHolder.get();
    }

    public static void removeModel() {
        previousHolder.remove();
        currentHolder.remove();
    }

    public static void setCurrentModel(Supplier<?> supplier) {
        try {
            Object obj = supplier.get();
            if (obj == null) {
                log.warn("bizLog current model can not be null");
                return;
            }
            Map<Class, Object> map = currentHolder.get();
            map.put(obj.getClass(), obj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    public static Map<Class, Object> getCurrentModel() {
        return currentHolder.get();
    }


    public static void addVariable(String key, Supplier<?> supplier) {
        if (key == null) {
            log.warn("bizLog variable key can not be null");
            return;
        }
        key = "${" + key.trim() + "}";
        Object o = null;
        try {
            o = supplier.get();
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        variablesHolder.get().put(key, o);
    }

    public static Object getVariable(String key) {
        try {
            Map<String, Object> map = variablesHolder.get();
            Object o = map.get(key);
            if (o != null) {
                return o;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    public static void removeVariables() {
        variablesHolder.remove();
    }


    public static <T, D> void cutModelSupplier(Supplier<T> modelMethod, Supplier<D> busMethod) {
        setPreviousModel(modelMethod);
        busMethod.get();
        setCurrentModel(modelMethod);
    }

}
