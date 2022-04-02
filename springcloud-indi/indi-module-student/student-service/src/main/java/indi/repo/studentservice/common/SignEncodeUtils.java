package indi.repo.studentservice.common;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author ChenHQ
 * 三种签名算法实现
 * @date 2021/12/16 19:38
 */
@Slf4j
public class SignEncodeUtils {

    public static final String JSON_PARAM = "jsonParam";

    /**
     * 三种签名算法实现
     * MD5(sign_method=md5)，HMAC_MD5(sign_method=hmac)，HMAC_SHA256（sign_method=hmac-sha256)  TODO:目前仅支持 MD5
     * TODO：调用该方法后请手动处理 MDC 操作，防止内存溢出
     *
     * @param params     公共参数和业务参数map
     * @param secret     商家appKey对应secret
     * @param signMethod 签名算法
     * @return 32位16进制加密字符串
     */
    public static String signTopRequest(Map<String, String> params, String secret, String signMethod) {
        // 第一步：检查参数是否已经排序
        JSONObject jsonObject = JSONUtil.parseObj(params.get(JSON_PARAM));
        if (!jsonObject.isEmpty()) {
            params.remove(JSON_PARAM);
            SortedMap<String, Object> sortedMap = new TreeMap<>();
            jsonObject.forEach(sortedMap::put);
            params.put(JSON_PARAM, JSONUtil.toJsonStr(sortedMap));
        }
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder signature = new StringBuilder();
        //签名的摘要算法，可选值为：hmac，md5，hmac-sha256
        if (ApplicationConstant.SIGN_METHOD_MD5.equalsIgnoreCase(signMethod)) {
            signature.append(secret);
        }
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                signature.append(key).append(value);
            }
        }

        // 第三步：使用MD5/HMAC加密
        signature.append(secret);

        // 第四步：使用AES加密， 把二进制转化为大写的十六进制
        String result = AESUtils.encrypt(signature.toString()).toUpperCase();
        log.info("stringToComputeSignature:[{}]", result);
        log.debug("method:【{}】sign-method:【{}】stringToComputeSignature:【{}】computeSignature:【{}】", params.get("method"), signMethod, signature, result);
        String computeSignature = signature.toString().replaceAll(secret, "{your_secret}");
        MDC.put(ApplicationConstant.THREAD_LOCAL_SIGN_PARAM, computeSignature);
        return result;
    }

}
