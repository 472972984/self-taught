package indi.repo.openapi.core.resolver;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import indi.repo.openapi.base.HdeDefaultRequest;
import indi.repo.openapi.base.HdeRequest;
import indi.repo.openapi.core.context.ApplicationStartupRunner;
import indi.repo.openapi.core.context.LocalHandleContext;
import indi.repo.openapi.util.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author ChenHQ
 * 自定义参数解析器
 * @date 2021/12/21 15:27
 */
public class CustomResolver implements HandlerMethodArgumentResolver {


    /**
     * 是否支持参数解析
     *
     * @return true 是  false 否
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == HdeRequest.class;
    }

    /**
     * 参数解析
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = ServletUtil.getRequest();
        String method = request.getMethod();
        Object attribute = createAttribute();
        if (HttpMethod.GET.toString().equalsIgnoreCase(method)) {
            String alias = getAlias(parameter);

            //拿到obj, 先从ModelAndViewContainer中拿，若没有则new1个参数类型的实例
            Object obj = (mavContainer.containsAttribute(alias)) ? mavContainer.getModel().get(alias) : attribute;

            //获得WebDataBinder，这里的具体WebDataBinder是ExtendedServletRequestDataBinder
            WebDataBinder binder = binderFactory.createBinder(webRequest, obj, alias);

            Object target = binder.getTarget();

            if (target != null) {
                //绑定参数
                bindParameters(webRequest, binder, alias, obj.getClass());
                //JSR303 验证
                validateIfApplicable(binder, parameter);
                if (binder.getBindingResult().hasErrors()) {
                    if (isBindExceptionRequired(binder, parameter)) {
                        throw new BindException(binder.getBindingResult());
                    }
                }
            }
            mavContainer.addAttribute(alias, target);

            return target;
        } else if (HttpMethod.POST.toString().equalsIgnoreCase(method)) {
            String bodyParam = getBodyParam(request);
            System.out.println(bodyParam);

            if (StringUtils.isNotBlank(bodyParam)) {
                Object o = JSON.parseObject(bodyParam, attribute.getClass());
                return o;
            } else {
                return attribute;
            }
        } else {
            throw new RuntimeException("请求方法仅支持【POST|GET】");
        }
    }

    /**
     * 获取 body 中的参数信息
     *
     * @return json数据
     * @throws IOException
     */
    private String getBodyParam(HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }


    /**
     * 创建参数对象
     *
     * @return 参数对象
     */
    private Object createAttribute() {
        String method = LocalHandleContext.getHandleContext().getMethod();
        Class paramClass = ApplicationStartupRunner.getClassParamMap().get(method);
        return BeanUtils.instantiateClass(Objects.nonNull(paramClass) ? paramClass : HdeDefaultRequest.class);
    }


    /**
     * 绑定参数
     *
     * @param request req
     * @param binder  binder
     * @param alias   参数别名
     * @param aClass  参数class
     */
    private void bindParameters(NativeWebRequest request, WebDataBinder binder, String alias, Class<?> aClass) {
        ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
        MockHttpServletRequest newRequest = new MockHttpServletRequest();

        //遍历所有的request参数
        Enumeration<String> enu = servletRequest.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement();
            //如果类对象中存在该字段
            Field field = getDeclaredField(aClass, paramName);
            if (Objects.nonNull(field)) {
                newRequest.setParameter(paramName, request.getParameter(paramName));
            }
        }
        ((ExtendedServletRequestDataBinder) binder).bind(newRequest);
    }

    /**
     * JSR303 验证
     */
    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation annot : annotations) {
            if (annot.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = AnnotationUtils.getValue(annot);
                binder.validate(hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
                break;
            }
        }
    }

    /**
     * 参数校验
     */
    protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = Objects.requireNonNull(parameter.getMethod()).getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));

        return !hasBindingResult;
    }

    /**
     * 对象参数的简称
     *
     * @return 对象参数的简称
     */
    private String getAlias(MethodParameter parameter) {
        //对象参数的简称
        //如果简称为空，取对象简称的首字母小写开头
        String simpleName = parameter.getParameterType().getSimpleName();
        return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param clazz     : 子类
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        Field field;

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
            }
        }
        return null;
    }

}

