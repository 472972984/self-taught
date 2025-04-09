package indi.repo.springboot.test.engine.handle;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import indi.repo.springboot.test.engine.HandlerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author ChenHQ
 * @date 2025/4/8 19:27
 */
@Slf4j
@Component
@Qualifier("MAPPING")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MappingHandler extends AbstractHandler {

    private Supplier<JSONObject> supplier;

    public void supplier(Supplier<JSONObject> supplier) {
        this.supplier = supplier;
    }

    public MappingHandler() throws Exception {
        super(HandlerType.MAPPING);
    }

    @Override
    protected String doProcess() {
        final JSONObject res = bodyMapping();
        this.res = res;
        return res.toString();
    }

    @Override
    protected Integer chainOrder() {
        return 1;
    }

    /**
     * 对象映射
     * 入参：资产信息
     */
    private JSONObject bodyMapping() {
        // 1、拿到入参信息
        JSONObject jSONObject = supplier.get();

        String code = getCode();
        // 对象映射代码
        log.info("对象映射转换code：\r\n {}", code);

        Map<String, Object> params = new HashMap<>();
        params.put("jSONObject", jSONObject);
        log.info("对象映射 jSONObject 入参： {}", jSONObject);

        SimpleBindings bindings = new SimpleBindings();
        bindings.put("params", params);
        engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        Object bodyResult;
        try {
            bodyResult = engine.eval(code);
        } catch (ScriptException e) {
            log.error("对象映射执行异常: ", e);
            throw new RuntimeException("对象映射执行异常");
        }
        String jsonBody = JSONUtil.toJsonStr(bodyResult);
        log.info("对象映射返回：{}", jsonBody);
        return JSONUtil.parseObj(bodyResult);
    }
}
