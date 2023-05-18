package indi.repo.springboot.spel;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ChenHQ
 * @date 2023/5/18 16:20
 */
@Aspect
@Component
public class StockWarnCollectAop {

    @Pointcut(value = "@annotation(indi.repo.springboot.spel.StockWarnCollect)")
    public void collectStockWarn(){}

    @Around(value = "collectStockWarn()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        Method targetMethod = this.getTargetMethod(pjp);
        StockWarnCollect stockWarnCollect = targetMethod.getAnnotation(StockWarnCollect.class);

        // spel信息
        String studentIdSpel = stockWarnCollect.studentId();
        String timeSpel = stockWarnCollect.time();

        // 客户id、来源解析
        ExpressionParser parser = new SpelExpressionParser();
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] params = discoverer.getParameterNames(targetMethod);
        Object[] args = pjp.getArgs();

        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        Expression expression = parser.parseExpression(studentIdSpel);
        Long studentId = expression.getValue(context, Long.class);
        // 表达式解析出来的学生id

        expression = parser.parseExpression(timeSpel);
        String time = expression.getValue(context, String.class);

        // 业务逻辑处理
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            throw e;
        }
        try {
            if (result != null) {
            }
        } catch (Exception e) {

        }
        return result;
    }
    /**
     * 获取目标方法
     */
    private Method getTargetMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method agentMethod = methodSignature.getMethod();
        return pjp.getTarget().getClass().getMethod(agentMethod.getName(),agentMethod.getParameterTypes());
    }
}
