package com.smq.studyappdemo.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.smq.studyappdemo.R;
import com.smq.studyappdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initIds();
        initNav();
        initEvent();

        initDrawerLayout();
    }


    private ImageView titlebarMeanu;
    private DrawerLayout drawerLayout;

    private void initIds() {
        titlebarMeanu = mainBinding.include.titlebarMeanu;
        drawerLayout = mainBinding.drawerLayout;

    }

    private void initNav() {
        /**
         * 让图片就是显示他本身的颜色
         */
        mainBinding.navigation.setItemIconTintList(null);
    }

    private void initEvent() {
        //mainBinding.include.titlebarMeanu.setOnClickListener(this);
        titlebarMeanu.setOnClickListener(this);


        mainBinding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.menu_project_home) {
                    // Handle the camera action
                } else if (id == R.id.menu_down) {

                } else if (id == R.id.menu_upgrade) {

                } else if (id == R.id.menu_upgrade) {

                } else if (id == R.id.menu_about) {

                } else if (id == R.id.menu_call_me) {

                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void initDrawerLayout() {
        View headerView = mainBinding.navigation.getHeaderView(0);
        headerView.setBackgroundResource(R.mipmap.ic_nav_bg_drawerlayout);
        final ImageView header_img = (ImageView) headerView.findViewById(R.id.header_img);
        //header_img.setImageResource(R.mipmap.ic_nav_bg_drawerlayout);
        //TODO : load image from internet
        Glide.with(this)
                .load(R.mipmap.ic_avatar)
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(header_img) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        header_img.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_meanu:
                //mainBinding.drawerLayout.openDrawer(GravityCompat.START);
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

}
