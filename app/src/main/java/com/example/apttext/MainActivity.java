package com.example.apttext;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apt_lib.IColor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // todo 测试工厂模式
        ColorFactory colorFactory = new ColorFactory();
        IColor blue = colorFactory.getColor("blue");
        Log.d("xx", "测试工厂模式：" + blue.id());
    }
}
