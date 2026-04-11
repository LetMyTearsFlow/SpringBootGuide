package org.cqlin.aopjointpointinfodemo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;


@Aspect
@Component
public class OrderAspect {

    @Around("execution(* org.cqlin.aopjointpointinfodemo.service..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        // 前置通知
        System.out.println("[OrderAspect] Before " + methodName);
        // execute target method
        Object proceed;
        try {
            Object[] args = joinPoint.getArgs();
            proceed = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            // 异常逻辑
            System.out.println("[OrderAspect] Exception " + methodName + ": " + throwable.getMessage());
            throw throwable;
        } finally {
            // 后置逻辑
            System.out.println("[OrderAspect] Return " + methodName);
        }

        return proceed;
    }
}
