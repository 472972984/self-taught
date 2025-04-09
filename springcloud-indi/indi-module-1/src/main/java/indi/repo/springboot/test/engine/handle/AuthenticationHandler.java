package indi.repo.springboot.test.engine.handle;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import indi.repo.springboot.test.engine.HandlerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ChenHQ
 * @date 2025/4/8 19:52
 */
@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuthenticationHandler extends AbstractHandler {

    public AuthenticationHandler() throws Exception {
        super(HandlerType.AUTH);
    }

    @Override
    protected String doProcess() {
        JSONObject mappingRes = findResByType(HandlerType.MAPPING);
        JSONObject res = authenticationHeader(mappingRes.toString());
        this.res = res;
        return res.toString();
    }

    @Override
    protected Integer chainOrder() {
        return 5;
    }


    public JSONObject authenticationHeader(String body) {
        String code = getCode();
        // 鉴权代码
        log.info("构建 header 鉴权 code：\r\n {}", code);

        String domain = getPluginDTO().getDomain();
        Map<String, Object> params = new HashMap<>();
        params.put("domain", domain);
        params.put("body", body);
        log.info("构建 header 鉴权 jSONObject 入参： {}", params);

        SimpleBindings bindings = new SimpleBindings();
        bindings.put("headerParam", params);
        engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        Object bodyResult;
        try {
            bodyResult = engine.eval(code);
        } catch (ScriptException e) {
            throw new RuntimeException("构建 header 鉴权执行异常");
        }

        String jsonBody = JSONUtil.toJsonStr(bodyResult);
        log.info("构建 header 鉴权返回：{}", jsonBody);

        return JSONUtil.parseObj(bodyResult);
    }
}
