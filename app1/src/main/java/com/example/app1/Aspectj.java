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

    //    @Pointcut("execution(* android.com.aspectj.MainActivity.onCreate(..))")
    //    @Pointcut("execution(* *..MainActivity.onCreate(..))")
    //    @Pointcut("execution(* *.*.*.MainActivity.onCreate(..))")
    //    @Pointcut("execution(* android.com.aspectj.BaseActivity+.onCreate(..))")
    //    @Pointcut("execution(* android.com.aspectj.BaseActivity+.onCreate(..)) && !within(android.com.aspectj.MainActivity)")
    @Pointcut("execution(* android.com.aspectj.BaseActivity+.onCreate(..)) && within(android.com.aspectj.MainActivity)")
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

    //    @Pointcut("call(* android.com.aspectj.TextAspectjCall.call())")
    //    @Pointcut("call(* android.com.aspectj.TextAspectjCall.call()) && !withincode(* android.com.aspectj.MainActivity.onCreate(..))")
    @Pointcut(
        "call(* android.com.aspectj.TextAspectjCall.call()) && withincode(* android.com.aspectj.MainActivity.onCreate(..))")
    public void callPointcut() {

    }

    @Before("callPointcut()")
    public void call(JoinPoint joinPoint) {
        Log.d("xx", "callBefore（call）: " + joinPoint.getThis());
    }

    @Pointcut("execution(* android.com.aspectj.TextAspectjCall.call())")
    public void callPointcut1() {

    }

    @Before("callPointcut1()")
    public void call1(JoinPoint joinPoint) {
        Log.d("xx", "callBefore（execution）: " + joinPoint.getThis());
    }

    // 带返回值的
    @Pointcut("call(* android.com.aspectj.TextAspectjCall.callWithReturn())")
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
