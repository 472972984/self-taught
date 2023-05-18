package indi.repo.springboot.log.annotation;

import java.lang.annotation.*;

/**
 * @author admin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Inherited
public @interface OpLog {

    String message() default "";

    String messageSuffix() default "";

}
