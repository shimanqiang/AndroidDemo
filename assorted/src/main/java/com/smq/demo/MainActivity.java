package com.smq.demo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File externalCacheDir = getExternalCacheDir();
        File externalFilesDir = getExternalFilesDir("");


        List<String> permissions = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1 == requestCode) {
            for (String permission : permissions) {
                if (android.Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)) {

                }
            }
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (android.Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)) {
                    //TODO 授权的权限
                }
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    //TODO 拒绝授权
                } else if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //TODO 已经授权
                }
            }
        }
    }
}
