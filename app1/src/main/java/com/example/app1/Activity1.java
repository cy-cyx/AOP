package com.example.app1;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * create by caiyx in 2020/5/9
 */
public class Activity1 extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new TextAspectjCall().call();
    }


}
