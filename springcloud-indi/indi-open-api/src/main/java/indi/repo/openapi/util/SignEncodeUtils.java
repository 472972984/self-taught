package indi.repo.openapi.util;

import indi.repo.openapi.constant.ApplicationConstant;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ChenHQ
 * @description: 三种签名算法实现
 * @date 2021/12/16 19:38
 */
public class SignEncodeUtils {

    /**
     * UTF-8编码
     */
    public static final String UTF_8_CHARSET = "UTF8";

    public static void main(String[] args) throws Exception {
        //模拟公共参数：
        // "app_key","12345678"   "secret","helloworld"
        Map<String, String> params = new HashMap<>();
        params.put("app_key", "12345678");
        params.put("version", "1.2.0");
        params.put("sign_method", "md5");
        params.put("timestamp", "2021-12-17 12:00:00");


        //模拟上传的参数
        params.put("username", "chenhuiqi");
        params.put("sex", "man");

        String sign = signTopRequest(params, "helloworld", "md5");


        System.out.println("sign = " + sign);
    }


    /**
     * 三种签名算法实现
     * MD5(sign_method=md5)，HMAC_MD5(sign_method=hmac)，HMAC_SHA256（sign_method=hmac-sha256)
     * @param params          公共参数和业务参数map
     * @param secret          商家appKey对应secret
     * @param signMethod      签名算法
     * @return                32位16进制加密字符串
     * @throws Exception
     */
    public static String signTopRequest(Map<String, String> params, String secret, String signMethod) throws Exception {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        //签名的摘要算法，可选值为：hmac，md5，hmac-sha256
        if (ApplicationConstant.SIGN_METHOD_MD5.equals(signMethod)) {
            query.append(secret);
        }
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                query.append(key).append(value);
            }
        }

        // 第三步：使用MD5/HMAC加密
        byte[] bytes;
        if (ApplicationConstant.SIGN_METHOD_HMAC.equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
        } else {
            query.append(secret);
            bytes = encryptMD5(query.toString());
        }

        // 第四步：把二进制转化为大写的十六进制
        return MD5Utils.md5Hex(bytes).toUpperCase();
    }

    /**
     * 将字符串转换成byte数组： 指定UTF8编码和加密方式
     * @param data          待加密字符串
     * @param secret        商家appKey对应secret
     * @return              字节数组
     * @throws IOException
     */
    public static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(UTF_8_CHARSET), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(UTF_8_CHARSET));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    /**
     * 将字符串转换成byte数组： 指定UTF8编码
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] encryptMD5(String data) throws IOException {
        return data.getBytes(UTF_8_CHARSET);
    }

}
