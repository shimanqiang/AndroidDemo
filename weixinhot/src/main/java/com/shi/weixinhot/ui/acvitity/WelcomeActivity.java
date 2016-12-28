package com.shi.weixinhot.ui.acvitity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.shi.weixinhot.R;

/**
 * Created by shimanqiang on 16/12/28.
 */

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_welcome, null);
        //setContentView(R.layout.activity_welcome);
        setContentView(view);

        //渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        MainActivity.startActivity(WelcomeActivity.this);
                        WelcomeActivity.this.finish();
                        //overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                    }
                }, 3000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });

//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                MainActivity.startActivity(WelcomeActivity.this);
//                WelcomeActivity.this.finish();
//            }
//        }, 3000);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
