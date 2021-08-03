package indi.repo.springboot.utils;

import indi.repo.springboot.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;

import static indi.repo.springboot.exception.BaseException.ERROR_PARAM_CODE;


/**
 * @author ChenHQ
 * @date: create in 2021/6/27
 */
@Slf4j
public class HibernateValidatorUtils {

    /**
     * 快速失败校验
     */
    private static Validator failFastValidator = Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory().getValidator();


    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private HibernateValidatorUtils() {
    }

    /**
     * 快速失败模式
     * @param obj
     * @param <T>
     */
    public static <T> void fastFailValidate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = failFastValidator.validate(obj);
        //获取失败result
        if (constraintViolations.size() > 0) {
            ConstraintViolation<T> next = constraintViolations.iterator().next();
            String errorMsgs = next.getPropertyPath().toString() + ":" + next.getMessage();
            throw new BaseException(ERROR_PARAM_CODE, errorMsgs);
        }
    }

    /**
     * 全部校验
     * @param obj
     * @param <T>
     */
    public static <T> void allCheckValidate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = failFastValidator.validate(obj);
        //获取失败result
        if (constraintViolations.size() > 0) {
            StringBuffer errorMsgs = new StringBuffer();
            for (ConstraintViolation<T> violation : constraintViolations) {
                errorMsgs.append(String.format("%s:%s", violation.getPropertyPath().toString(), violation.getMessage()));
            }
            throw new BaseException(ERROR_PARAM_CODE, errorMsgs.toString());
        }
    }

}
