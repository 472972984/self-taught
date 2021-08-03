package indi.repo.springboot.interception.repeat;

import com.alibaba.fastjson.JSON;
import indi.repo.springboot.context.LocalHandleContext;
import indi.repo.springboot.interception.RepeatSubmitInterceptor;
import indi.repo.springboot.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author ChenHQ
 * @date: create in 2021/6/20
 */
@Component
@Slf4j
public class SimpleRepeatInterceptor extends RepeatSubmitInterceptor {

    private static final String separator = ":";

    private static final Integer expireTime = 30;

    @Override
    public Boolean isRepeatSubmit(HttpServletRequest request) {

        RedisTemplate<String, String> redisTemplate = SpringUtils.getBean("redisTemplate");

        String userId = LocalHandleContext.getHandleContext().getUserId();
        if (Objects.isNull(userId)) {
            return false;
        }

        String url = request.getRequestURI();
        String keyRedis = userId + separator + url;

        try {
            String oldParam = redisTemplate.opsForValue().get(keyRedis);
            String nowParam;

            //优先获取body内容
            String body = getBodyParam(request);

            if (StringUtils.isNotBlank(body)) {
                nowParam = body;
            }else {
                Map<String, String[]> map = request.getParameterMap();
                if (CollectionUtils.isEmpty(map)) {
                    return false;
                }else {
                    nowParam = JSON.toJSONString(map);
                }
            }

            if (Objects.nonNull(oldParam)) {
                return oldParam.equals(nowParam);
            }

            redisTemplate.opsForValue().set(keyRedis, nowParam, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("isRepeatSubmit error:{}", e);
            return false;
        }
        return false;
    }


    /**
     * 获取 body 中的参数信息
     * @param request
     * @return
     * @throws IOException
     */
    private String getBodyParam(HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
