package com.atguigu.crud.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * @Auther: Albert
 * @Date: 2018/11/3 23:27
 * @Description:
 */
@Component
@Aspect
public class SysLogAspect {

    @Pointcut("@annotation(com.atguigu.crud.annotation.SysLog)")
    public void logPointCut(){

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point)throws Throwable{
        long beginTime = System.currentTimeMillis();
        System.out.println("开始时间："+beginTime);
        Object result = point.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println("结束时间:"+endTime);
        return result;
    }

}
