package com.example.apttext;

import android.com.spi_lib.SpiInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apt_lib.BindView;
import com.example.apt_lib.IColor;

import java.util.Iterator;
import java.util.ServiceLoader;
import android.com.spi_lib.SpiInterface;

public class MainActivity extends AppCompatActivity {

    @BindView(id = R.id.tv_text)
    TextView textView;

    @BindView(id = R.id.bn_button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FakeButterKnife.bind(this);
        textView.setText("测试假油刀");

        // todo 测试工厂模式
        ColorFactory colorFactory = new ColorFactory();
        IColor blue = colorFactory.getColor("blue");
        Log.d("xx", "测试工厂模式：" + blue.id());
        Toast.makeText(this, blue.id(), Toast.LENGTH_SHORT).show();

        // spi
        ServiceLoader<SpiInterface> loader = ServiceLoader.load(SpiInterface.class);
        Iterator<SpiInterface> iterator = loader.iterator();
        while (iterator.hasNext()){
            SpiInterface next = iterator.next();
            Log.d("xx", next.toSpiString());
        }
    }
}
