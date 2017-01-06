package com.smq.databanding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.smq.databanding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //MainBanding dataBinding;
    ActivityMainBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();

    }

    private void init() {
        dataBinding.setUser(new User("zhangfei", 18));
    }
}
