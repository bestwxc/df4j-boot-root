package com.df4j.v2.boot.datasource;


import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UseDataSource {
    String value() default "";
    boolean useMaster() default false;
}
