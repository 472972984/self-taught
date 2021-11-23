package indi.repo.springboot.common.log.spring;

import indi.repo.springboot.common.log.annotation.UpdateEntityLog;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jin on 2018/12/24.
 */
public class SpringExpression<T> {

    private final SpelExpressionParser parser = new SpelExpressionParser();

    private final static String ExpressionPrefix = "#";

    // shared param discoverer since it caches data internally
    private final ParameterNameDiscoverer paramNameDiscoverer =
            new DefaultParameterNameDiscoverer();

    private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<>(64);

    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    /**
     * Create the suitable {@link EvaluationContext} for the specified event handling on the
     * specified method.
     */
    public EvaluationContext createEvaluationContext(UpdateEntityLog updateEntityLog, Class<?> targetClass,
                                                     Method method, Object[] args) {

        Method targetMethod = getTargetMethod(targetClass, method);
        LogObject root = new LogObject(updateEntityLog, args);
        return new MethodBasedEvaluationContext(root, targetMethod, args, this.paramNameDiscoverer);
    }

    public static boolean isTemplate(String template){
        if(template == null){
            return false;
        }
        return template.trim().startsWith(ExpressionPrefix);
    }

    /**
     * Specify if the condition defined by the specified expression matches.
     */
    public T condition(String conditionExpression, AnnotatedElementKey elementKey,
                       EvaluationContext evalContext, Class<T> clazz) {
        return getExpression(this.conditionCache, elementKey, conditionExpression)
                .getValue(evalContext, clazz);
    }

    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = this.targetMethodCache.get(methodKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            if (targetMethod == null) {
                targetMethod = method;
            }
            this.targetMethodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }

    /**
     * Return the {@link Expression} for the specified SpEL value
     * <p>Parse the expression if it hasn't been already.
     * @param cache the cache to use
     * @param elementKey the element on which the expression is defined
     * @param expression the expression to parse
     */
    private Expression getExpression(Map<ExpressionKey, Expression> cache,
                                     AnnotatedElementKey elementKey, String expression) {

        ExpressionKey expressionKey = createKey(elementKey, expression);
        Expression expr = cache.get(expressionKey);
        if (expr == null) {
            expr = parser.parseExpression(expression);
            cache.put(expressionKey, expr);
        }
        return expr;
    }

    private ExpressionKey createKey(AnnotatedElementKey elementKey, String expression) {
        return new ExpressionKey(elementKey, expression);
    }

    protected static class ExpressionKey implements Comparable<ExpressionKey> {

        private final AnnotatedElementKey element;

        private final String expression;

        protected ExpressionKey(AnnotatedElementKey element, String expression) {
            this.element = element;
            this.expression = expression;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ExpressionKey)) {
                return false;
            }
            ExpressionKey otherKey = (ExpressionKey) other;
            return (this.element.equals(otherKey.element) &&
                    ObjectUtils.nullSafeEquals(this.expression, otherKey.expression));
        }

        @Override
        public int hashCode() {
            return this.element.hashCode() + (this.expression != null ? this.expression.hashCode() * 29 : 0);
        }

        @Override
        public String toString() {
            return this.element + (this.expression != null ? " with expression \"" + this.expression : "\"");
        }

        @Override
        public int compareTo(ExpressionKey other) {
            int result = this.element.toString().compareTo(other.element.toString());
            if (result == 0 && this.expression != null) {
                result = this.expression.compareTo(other.expression);
            }
            return result;
        }
    }
}