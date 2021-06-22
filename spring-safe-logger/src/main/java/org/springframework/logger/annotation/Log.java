package org.springframework.logger.annotation;

import java.lang.annotation.*;

/**
 * @author Sir.D
 */
@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    String eventCategory() default "";

    String eventType();

    String[] params() default {};

    String message();

    int level() default 0;

}
