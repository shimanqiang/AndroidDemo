package com.smq.annotationdemo;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class AnnotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);

        int i = testMethod(null);
        System.out.println(i);
    }


    @CheckResult
    private int testMethod(@NonNull String str) {

        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        return 0;
    }
}
