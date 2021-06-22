package org.springframework.logger.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * @author xs
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping()
public @interface IRequestMapping {

    int id() default 0;

    int[] parentIds() default {};

    int[] roleType() default {};

    String component() default "";

    boolean isShow() default true;

    @AliasFor(
            annotation = RequestMapping.class
    )
    String name() default "";

    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] value() default {};

    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] path() default {};

    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] params() default {};

    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] headers() default {};

    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] consumes() default {};

    @AliasFor(
            annotation = RequestMapping.class
    )
    String[] produces() default {};

    @AliasFor(
            annotation = RequestMapping.class
    )
    RequestMethod[] method() default {};
}
