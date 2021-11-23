package indi.repo.springboot.common.log.spring;

import indi.repo.springboot.common.log.annotation.UpdateEntityLog;
import indi.repo.springboot.common.log.context.BizLogContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.SpelEvaluationException;

import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Log的SPEL表达式解析器
 * Created by jin on 2018/12/28.
 */
@Slf4j
public class LogSpringExpression {

    private SpringExpression springExpression;

    public LogSpringExpression() {
        this.springExpression = new SpringExpression<>();
    }

    public LogSpringExpression(SpringExpression springExpression) {
        this.springExpression = springExpression;
    }


    private Pattern p = Pattern.compile("\\{(.*?)}");

    private static Pattern pe = Pattern.compile("\\$\\{(.*?)}");


    public String matcherValue(String template, UpdateEntityLog entityLog, Class<?> target, Method method, Object[] args) {
        return this.doMatherValue(template, param -> this.getValue(entityLog, target, method, args, param, String.class));
    }

    private String doMatherValue(String template, Function<String, String> paramValueFunction) {
        if (template == null) {
            return "";
        }
        if (SpringExpression.isTemplate(template)) {
            return paramValueFunction.apply(template);
        }
        template = matcher$(template);
        Matcher matcher = p.matcher(template);
        String param;
        String paramValue;
        while (matcher.find()) {
            param = matcher.group();
            paramValue = paramValueFunction.apply(param);
            if (!StringUtils.isEmpty(paramValue)) {
                template = template.replace(param, paramValue);
            }
        }
        return template;
    }

    public static String matcher$(String template) {
        Matcher matcher = pe.matcher(template);
        String param;
        while (matcher.find()) {
            param = matcher.group();
            template = template.replace(param, String.valueOf(BizLogContext.getVariable(param)));
        }
        return template;
    }


    /**
     * 得到注解上的SPEL表达式解析后的值
     *
     * @param entityLog     日志注解
     * @param target     调用对象
     * @param method     调用方法
     * @param args       调用参数
     * @param condition  SPEL语句
     * @param valueClass 值对象类型
     * @param <T>        返回的类型
     * @return 解析后的数据
     * @see
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(UpdateEntityLog entityLog, Class<?> target, Method method, Object[] args, String condition, Class<T> valueClass) {
        try {
            EvaluationContext evaluationContext = springExpression.createEvaluationContext(entityLog, target, method, args);
            AnnotatedElementKey methodKey = new AnnotatedElementKey(method, target);
            return (T) springExpression.condition(condition, methodKey, evaluationContext, valueClass);
        } catch (SpelEvaluationException e) {
            log.warn("spel match error ", e);
        }
        return null;
    }
}
