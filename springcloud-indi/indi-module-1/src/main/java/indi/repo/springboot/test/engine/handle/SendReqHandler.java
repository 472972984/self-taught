package indi.repo.springboot.test.engine.handle;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import indi.repo.springboot.test.engine.HandlerType;
import indi.repo.springboot.test.engine.PluginDTO;
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
 * @date 2025/4/8 19:59
 */
@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SendReqHandler extends AbstractHandler {

    public SendReqHandler() throws Exception {
        super(HandlerType.SEND);
    }

    @Override
    protected String doProcess() {
        JSONObject bodyObj = findResByType(HandlerType.MAPPING);
        JSONObject headerObj = findResByType(HandlerType.AUTH);
        Map<String, Object> headerMap = headerObj.getRaw();
        JSONObject res = sendRequest(bodyObj.toString(), headerMap);
        return res.toString();
    }

    @Override
    protected Integer chainOrder() {
        return 10;
    }

    public JSONObject sendRequest(String body, Map<String, Object> header) {
        String code = getCode();
        // 发起请求代码
        log.info("发起请求 code：\r\n {}", code);

        PluginDTO pluginDTO = getPluginDTO();
        Map<String, Object> params = new HashMap<>();
        params.put("domain", pluginDTO.getDomain());
        params.put("path", pluginDTO.getPath());
        params.put("body", body);
        params.put("header", header);
        log.info("发起请求 jSONObject 入参： {}", params);

        SimpleBindings bindings = new SimpleBindings();
        bindings.put("sendParam", params);
        engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
        Object bodyResult;
        try {
            bodyResult = engine.eval(code);
        } catch (ScriptException e) {
            throw new RuntimeException("发起请求执行异常");
        }

        String jsonBody = JSONUtil.toJsonStr(bodyResult);
        log.info("send request 返回：{}", jsonBody);
        return JSONUtil.parseObj(bodyResult);
    }
}
