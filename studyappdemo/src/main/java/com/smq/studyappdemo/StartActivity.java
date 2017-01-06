package com.smq.studyappdemo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.smq.studyappdemo.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding dataBinding;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_start);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_start);

        initData();
        initEvent();
    }


    /**
     * 跳转到主界面
     */
    private void jumpToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    /**
     * 初始化数据
     * 使用最low的一种写法，主要看效果
     */
    private void initData() {
        dataBinding.setRemainingTime("3s");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataBinding.setRemainingTime("2s");
            }
        }, 1000);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataBinding.setRemainingTime("1s");
            }
        }, 2000);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataBinding.setRemainingTime("");
                jumpToMainActivity();
            }
        }, 3000);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        /**
         * 点击跳过，直接到主界面上
         */
        dataBinding.tvJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToMainActivity();
            }
        });
    }
}
