package indi.repo.springboot.common.log.spring;

import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * A method-based {@link org.springframework.expression.EvaluationContext} that
 * provides explicit support for method-based invocations.
 *
 * <p>Expose the actual method arguments using the following aliases:
 * <ol>
 * <li>pX where X is the index of the argument (p0 for the first argument)</li>
 * <li>aX where X is the index of the argument (a1 for the second argument)</li>
 * <li>the name of the parameter as discovered by a configurable {@link ParameterNameDiscoverer}</li>
 * </ol>
 *
 * @author Stephane Nicoll
 * @author Juergen Hoeller
 * @since 4.2
 */
public class MethodBasedEvaluationContext extends StandardEvaluationContext {

    private final Method method;

    private final Object[] arguments;

    private final ParameterNameDiscoverer parameterNameDiscoverer;

    private boolean argumentsLoaded = false;


    public MethodBasedEvaluationContext(Object rootObject, Method method, Object[] arguments,
                                        ParameterNameDiscoverer parameterNameDiscoverer) {

        super(rootObject);
        this.method = method;
        this.arguments = arguments;
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }


    @Override
    public Object lookupVariable(String name) {
        Object variable = super.lookupVariable(name);
        if (variable != null) {
            return variable;
        }
        if (!this.argumentsLoaded) {
            lazyLoadArguments();
            this.argumentsLoaded = true;
            variable = super.lookupVariable(name);
        }
        return variable;
    }

    /**
     * Load the param information only when needed.
     */
    protected void lazyLoadArguments() {
        // Shortcut if no args need to be loaded
        if (ObjectUtils.isEmpty(this.arguments)) {
            return;
        }

        // Expose indexed variables as well as parameter names (if discoverable)
        String[] paramNames = this.parameterNameDiscoverer.getParameterNames(this.method);
        int paramCount = (paramNames != null ? paramNames.length : this.method.getParameterTypes().length);
        int argsCount = this.arguments.length;

        for (int i = 0; i < paramCount; i++) {
            Object value = null;
            if (argsCount > paramCount && i == paramCount - 1) {
                // Expose remaining arguments as vararg array for last parameter
                value = Arrays.copyOfRange(this.arguments, i, argsCount);
            }
            else if (argsCount > i) {
                // Actual argument found - otherwise left as null
                value = this.arguments[i];
            }
            setVariable("a" + i, value);
            setVariable("p" + i, value);
            if (paramNames != null) {
                setVariable(paramNames[i], value);
            }
        }
    }

}