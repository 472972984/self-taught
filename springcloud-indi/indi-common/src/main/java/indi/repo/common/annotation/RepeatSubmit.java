package indi.repo.common.annotation;

import java.lang.annotation.*;

/**
 * @author admin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RepeatSubmit {
}
