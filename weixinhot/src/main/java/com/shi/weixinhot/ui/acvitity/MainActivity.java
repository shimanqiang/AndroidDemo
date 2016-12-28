package com.shi.weixinhot.ui.acvitity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.shi.weixinhot.R;
import com.shi.weixinhot.ui.adapter.WeiXinPageAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        WeiXinPageAdapter adapter = new WeiXinPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        // 设置TableLayout为可滚动（在ViewPager设置Adapter之后），也可在布局中添加tabMode属性
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        // 将TabLayout和ViewPager关联起来
        tabs.setupWithViewPager(mViewPager);
        /**
         * 给Tabs设置适配器:该方法过时：使用setupWithViewPager就足够了
         * http://stackoverflow.com/questions/35945592/settabsfrompageradapter-is-deprecated
         */
        //tabs.setTabsFromPagerAdapter(adapter);
    }

    private void initView() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.vp);
    }

}
