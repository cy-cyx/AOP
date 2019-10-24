package com.example.apttext;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * create by cy
 * time : 2019/10/24
 * version : 1.0
 * Features : 自制黄油刀
 */
public class FakeButterKnife {

    public static void bind(Activity activity) {
        try {
            Class<?> bindingClass = activity.getClass().getClassLoader().
                    loadClass(activity.getClass().getName() + "_bind");
            Constructor<?> constructor = bindingClass.getConstructor(activity.getClass(), View.class);
            constructor.newInstance(activity, activity.getWindow().getDecorView());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
