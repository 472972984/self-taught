package indi.repo.studentservice.common;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import indi.repo.module.ChainResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static indi.repo.studentservice.common.ApplicationConstant.THREAD_LOCAL_SIGN_PARAM;

/**
 * @author ChenHQ
 * 第三方链客户端
 * @date 2022/2/25 3:45 PM
 */
@Slf4j
@Component
public class OtherChainClient {

    /**
     * 调用hde服务
     * @param url    回调地址
     * @throws Exception
     */
    public ChainResponse<Object> postHdeRequest(String url, String method, JSONObject data, String appKey, String appSecret) throws Exception {

        SortedMap<String, Object> sortedMap = new TreeMap<>();
        data.forEach(sortedMap::put);
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("app-key", appKey);
        headerParams.put("version", "1.1.0");
        headerParams.put("sign-method", "MD5");
        headerParams.put("timestamp", LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss"));
        if (StringUtils.isEmpty(method)) {
            throw new RuntimeException("调用第三方链接口异常：method不可为空");
        }
        headerParams.put("method", method);
        headerParams.put("jsonParam", JSONUtil.toJsonStr(sortedMap));

        String sign = extractedSign(headerParams, appSecret);
        headerParams.put("sign", sign);
        log.info("调用第三方链接口:{},method:{} data:{}", url, method, data);
        // 调用安全岛接口，创建关联用户

        String json = HttpClientUtil.doPostJson(url, headerParams, data.toString());
        log.info("[推送订单信息给第三方链信息返回：{}]", json);

        return JSONUtil.toBean(json, new TypeReference<ChainResponse<Object>>() {
        }, true);
    }


    /**
     * 获取sign值
     */
    private String extractedSign(Map<String, String> map, String secret) {
        try {
            return SignEncodeUtils.signTopRequest(map, secret, map.get("sign-method"));
        } catch (Exception e) {
            log.error("md5加密异常：{}", e.getMessage());
            throw new RuntimeException("调用第三方链接口异常：md5加密异常");
        } finally {
            try {
                MDC.remove(THREAD_LOCAL_SIGN_PARAM);
            } catch (IllegalArgumentException e) {
                log.error("remove computeSignature error:{}", e.getMessage());
            }
        }
    }

}
