package indi.repo.springboot.test.engine.handle;

import cn.hutool.json.JSONObject;
import indi.repo.springboot.test.engine.CustomScriptEngine;
import indi.repo.springboot.test.engine.HandlerType;
import indi.repo.springboot.test.engine.PluginDTO;
import indi.repo.springboot.test.engine.ScriptEnginePool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * @author ChenHQ
 * @date 2025/4/8 19:03
 */
@Slf4j
@Getter
public abstract class AbstractHandler implements Serializable, Comparable<AbstractHandler> {

    protected final CustomScriptEngine engine;

    protected JSONObject res;

    @Setter
    protected PluginDTO pluginDTO;

    @Getter
    protected HandlerType handlerType;

    /**
     * 下一处理节点
     */
    @Setter
    protected AbstractHandler next;

    /**
     * 下一处理节点
     */
    @Setter
    private AbstractHandler pre;

    public String doStart() {
        String result = doProcess();
        if (next != null) {
            result = next.doStart();
        }
        return result;
    }

    protected String getCode() {
        final Optional<PluginDTO.PluginDetailDTO> optional = pluginDTO.getDetailDTOList().stream().filter(detailDTO -> detailDTO.getHandlerType() == handlerType).findFirst();
        return optional.map(PluginDTO.PluginDetailDTO::getCode).orElse(null);
    }

    protected JSONObject findResByType(HandlerType type) {
        if (this.handlerType == type) {
            return this.res;
        } else {
            return this.pre.findResByType(type);
        }
    }

    protected abstract String doProcess();

    protected AbstractHandler(HandlerType handlerType) throws Exception {
        this.engine = ScriptEnginePool.borrowObject();
        this.handlerType = handlerType;
    }

    /**
     * 控制校验链执行顺序
     */
    protected abstract Integer chainOrder();

    @Override
    public int compareTo(AbstractHandler o) {
        return chainOrder() - o.chainOrder();
    }

    @PreDestroy
    public void destroy() {
        ScriptEnginePool.returnObject(engine);
    }


    public static class HandlerBuilder {

        private AbstractHandler head;

        private AbstractHandler tail;

        /**
         * 构造者模式创建处理链
         *
         * @param handler 处理链
         */
        public HandlerBuilder addLastHandler(AbstractHandler handler) {
            if (Objects.isNull(head)) {
                head = handler;
                head.setPre(null);
            } else {
                this.tail.setNext(handler);
                handler.setPre(tail);
            }
            tail = handler;
            return this;
        }

        public AbstractHandler build() {
            return head;
        }
    }


    /**
     import java.util.HashMap;
     import java.util.Map;
     import cn.hutool.json.JSONObject;

     // ‼️1、代码拆分 —— 【对象映射代码片段】
     public static Map<String, Object> mappingObj(JSONObject jsonObject) {
     Map<String, Object> map = new HashMap<>();

     // 用户自定义编写的代码
     // ————————>>>
     map.put("productionId", jsonObject.getStr("assetId"));
     map.put("platformId", jsonObject.getStr("dataAssetPlatformId"));
     map.put("assetType", jsonObject.getStr("assetType"));
     map.put("productionName", jsonObject.getStr("assetName"));
     map.put("providerOrg", jsonObject.getStr("providerOrg"));
     map.put("source", jsonObject.getStr("source"));
     // .....
     // <<<————————

     return map;
     }

     mappingObj(params.asset)



     // 2、代码拆分 —— 【添加请求头】
     import java.util.HashMap;
     import java.util.Map;
     import cn.hutool.json.JSONObject;
     public static Map<String, String> buildHeader(String domain, String body) {
     Map<String, Object> header = new HashMap<>();

     // 用户自定义编写的代码
     // ————————>>>
     header.put("Content-Type", "application/json");
     header.put("token", "123456");
     // <<<————————


     return header;
     }
     buildHeader(headerParam.domain, headerParam.body)



     // 3、发送请求
     import cn.hutool.http.HttpRequest;
     import cn.hutool.http.HttpResponse;
     import cn.hutool.http.HttpUtil;
     import java.util.HashMap;
     import java.util.Map;

     public static String sendRequest(String domain, String path, String body, Map<String, String> headers) {
     String url = domain + path;
     HttpRequest request = HttpUtil.createPost(url);
     request.addHeaders(headers);
     request.body(body);
     HttpResponse execute = request.execute();
     return execute.body();
     }
     sendRequest(sendParam.domain, sendParam.path, sendParam.body, sendParam.header);

     */

}
