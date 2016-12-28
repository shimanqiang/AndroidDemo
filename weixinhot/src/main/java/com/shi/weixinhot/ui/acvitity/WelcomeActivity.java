package com.shi.weixinhot.ui.acvitity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by shimanqiang on 16/12/28.
 */

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                MainActivity.startActivity(WelcomeActivity.this);
                WelcomeActivity.this.finish();
            }
        }, 3000);
    }
}
