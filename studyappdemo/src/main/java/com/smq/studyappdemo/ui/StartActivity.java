package com.smq.studyappdemo.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.smq.studyappdemo.R;
import com.smq.studyappdemo.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = StartActivity.class.getSimpleName();

    private ActivityStartBinding dataBinding;

    private Handler mHandler = new Handler();

    private boolean isJump = false; //是否已经执行跳转，因为有可能是用户点击跳转的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_start);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_start);
        initData();
        initEvent();

        buildAnimation();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isJump = true;
    }

    /**
     * 跳转到主界面
     */
    private synchronized void jumpToMainActivity() {
        if (isJump) {
            return;
        }
        isJump = true;

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);

        finish();
    }


    /**
     * 获取随机图片背景的资源
     *
     * @return
     */
    private int getRandomBackgroundImage() {
        int[] imgs = {R.mipmap.b_1, R.mipmap.b_2,
                R.mipmap.b_3, R.mipmap.b_4, R.mipmap.b_5, R.mipmap.b_6};
        int index = (int) Math.floor(Math.random() * imgs.length);
        //int i = new Random().nextInt(imgs.length);
        return imgs[index];
    }


    /**
     * 初始化数据
     */
    private void initData() {
        /**
         * 设置图片的背景图片
         */
        dataBinding.ivImg.setImageResource(getRandomBackgroundImage());


        /**
         * 倒计时-跳转到主界面
         * 使用最low的一种写法，主要看效果
         */
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

    /**
     * 动画效果
     */
    private void buildAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dataBinding.ivImg.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        dataBinding.ivImg.setAnimation(animation);
    }
}
