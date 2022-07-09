package com.hld.mvvm.demo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.koin.java.KoinJavaComponent;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.button).setOnClickListener(v -> {

            TestBean t = KoinJavaComponent.get(TestBean.class);
            System.out.println("-------------t:" + t.testStr());
        });
    }
}
