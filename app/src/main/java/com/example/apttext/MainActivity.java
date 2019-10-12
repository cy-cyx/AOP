package com.example.apttext;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apt_lib.AutoCreat;

@AutoCreat
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }
}
