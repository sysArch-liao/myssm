package com.atguigu.crud.annotation;

import java.lang.annotation.*;

/**
 * @Auther: Albert
 * @Date: 2018/11/3 23:22
 * @Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
