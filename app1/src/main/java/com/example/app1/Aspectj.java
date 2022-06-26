package com.example.app1;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Aspectj {

    //    @Pointcut("execution(* com.example.app1.MainActivity.onCreate(..))")
    //    @Pointcut("execution(* *..MainActivity.onCreate(..))")
    //    @Pointcut("execution(* *.*.*.MainActivity.onCreate(..))")
    //    @Pointcut("execution(* com.example.app1.BaseActivity+.onCreate(..))")
    //    @Pointcut("execution(* com.example.app1.BaseActivity+.onCreate(..)) && !within(com.example.app1.MainActivity)")
    @Pointcut("execution(* com.example.app1.BaseActivity+.onCreate(..)) && within(com.example.app1.MainActivity)")
    public void activityOnCreatePointcut() {

    }

    @Before("activityOnCreatePointcut()")
    public void activityOnCreateBefore(JoinPoint joinPoint) {
        Log.d("xx", "activityOnCreateBefore: " + joinPoint.getThis());
    }

    @Around("activityOnCreatePointcut()")
    public void activityOnCreateAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d("xx", "activityOnCreateAround: " + joinPoint.getThis());
        joinPoint.proceed();
    }

    // 如果After和Around同时存在,After必须在Around之后
    @After("activityOnCreatePointcut()")
    public void activityOnCreateAfter(JoinPoint joinPoint) {
        Log.d("xx", "activityOnCreateAfter: " + joinPoint.getThis());
    }

    // 区分call和execution

    //    @Pointcut("call(* com.example.app1.TextAspectjCall.call())")
    //    @Pointcut("call(* com.example.app1.TextAspectjCall.call()) && !withincode(* com.example.app1.MainActivity.onCreate(..))")
    @Pointcut(
        "call(* com.example.app1.TextAspectjCall.call()) && withincode(* com.example.app1.MainActivity.onCreate(..))")
    public void callPointcut() {

    }

    @Before("callPointcut()")
    public void call(JoinPoint joinPoint) {
        Log.d("xx", "callBefore（call）: " + joinPoint.getThis());
    }

    @Pointcut("execution(* com.example.app1.TextAspectjCall.call())")
    public void callPointcut1() {

    }

    @Before("callPointcut1()")
    public void call1(JoinPoint joinPoint) {
        Log.d("xx", "callBefore（execution）: " + joinPoint.getThis());
    }

    // 带返回值的
    @Pointcut("call(* com.example.app1.TextAspectjCall.callWithReturn())")
    public void callWithReturn() {

    }

    @Around("callWithReturn()")
    public Object call2(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        Log.d("xx", "callWithReturn" + duration);
        return result;
    }
}
