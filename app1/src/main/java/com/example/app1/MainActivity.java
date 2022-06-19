package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("xx", "onCreate: ");

        new TextAspectjCall().call();
        Log.d("xx", new TextAspectjCall().callWithReturn());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void click(View view) {
        startActivity(new Intent(this, Activity1.class));
    }
}
