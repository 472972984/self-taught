package indi.repo.springboot.log.core;

import java.util.HashMap;
import java.util.Map;

public class OPLogContext {

    private static final ThreadLocal<Map<String, Object>> local = new ThreadLocal<>();

    private static final String OPLOG_TYPE = "op_type";

    private static final String OPLOG_MODULE = "op_module";

    private static final String OPLOG_MESSAGE = "message";

    private static final String OPLOG_RESULT = "op_result";

    private static final String OPLOG_ACCOUNT = "username";

    private static final String OPLOG_SWITCH = "op_switch";


    public static ThreadLocal<Map<String, Object>> getLocal() {
        return local;
    }

    private static void checkNull() {
        if (local.get() == null) {
            local.set(new HashMap<>());
        }
    }

    public static void openSwitch() {
        checkNull();
        local.get().put(OPLOG_SWITCH, true);
    }

    public static void closeSwitch() {
        checkNull();
        local.get().put(OPLOG_SWITCH, false);
    }

    public static void putMessage(String message) {
        checkNull();
        local.get().put(OPLOG_MESSAGE, message);
    }

    public static void putOpType(OpType opType) {
        checkNull();
        local.get().put(OPLOG_TYPE, opType);
    }

    public static void putModule(OpModule opModule) {
        checkNull();
        local.get().put(OPLOG_MODULE, opModule);
    }

    public static void putResult(boolean success) {
        checkNull();
        local.get().put(OPLOG_RESULT, success);
    }

    public static void putAccount(String account) {
        checkNull();
        local.get().put(OPLOG_ACCOUNT, account);
    }

    public static OpType getOpType() {
        if (local.get() == null) {
            return null;
        }
        return (OpType) local.get().get(OPLOG_TYPE);
    }

    public static OpModule getOpModule() {
        if (local.get() == null) {
            return null;
        }
        OpType opType = getOpType();
        if (opType != null && opType.module() != null) {
            return opType.module();
        }
        return (OpModule) local.get().get(OPLOG_MODULE);
    }

    public static Boolean getOpResult() {
        if (local.get() == null) {
            return null;
        }
        return (Boolean) local.get().get(OPLOG_RESULT);
    }

    public static String getOpMessage() {
        if (local.get() == null) {
            return null;
        }
        return (String) local.get().get(OPLOG_MESSAGE);
    }

    public static String getOpAccount() {
        if (local.get() == null || !local.get().containsKey(OPLOG_ACCOUNT)) {
            return null;
        }
        return (String) local.get().get(OPLOG_ACCOUNT);
    }


    public static Object get(String key) {
        if (local.get() == null || !local.get().containsKey(key)) {
            return null;
        }
        return local.get().get(key);
    }

    public static void put(String key, Object value) {
        if (local.get() != null) {
            local.get().put(key, value);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put(key, value);
            local.set(map);
        }
    }

}
