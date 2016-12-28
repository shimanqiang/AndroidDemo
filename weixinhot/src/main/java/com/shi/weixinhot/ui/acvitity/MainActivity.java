package com.shi.weixinhot.ui.acvitity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.shi.weixinhot.R;
import com.shi.weixinhot.ui.adapter.WeiXinPageAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private ViewPager mViewPager;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    /**
     * http://blog.csdn.net/zeng292390450/article/details/51601940
     * 在做启动页面的时候，想实现和微信，qq那样的启动页面，在第一次启动会显示出来，
     * 按下返回键回到桌面再次进入就不会显示了，除非程序被杀死。昨天学习到了两种方式实现这个效果，记下
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(Intent.ACTION_MAIN);  //主启动，不期望接收数据
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);       //新的activity栈中开启，或者已经存在就调到栈前
            i.addCategory(Intent.CATEGORY_HOME);            //添加种类，为设备首次启动显示的页面
            startActivity(i);
        }
        return super.onKeyDown(keyCode, event);
    }

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
