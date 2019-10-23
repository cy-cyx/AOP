package com.example.apt_lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create by cy
 * time : 2019/10/12
 * version : 1.0
 * Features : 用于测试工厂模式
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColorFactory {
    String id();
}
