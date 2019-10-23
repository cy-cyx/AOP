package com.example.apttext;

import com.example.apt_lib.ColorFactory;
import com.example.apt_lib.IColor;

/**
 * create by cy
 * time : 2019/10/22
 * version : 1.0
 * Features :
 */
@ColorFactory(id = "red")
public class Red implements IColor {

    @Override
    public String id() {
        return "red";
    }
}
