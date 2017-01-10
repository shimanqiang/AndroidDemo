package com.smq.studyappdemo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;

import com.smq.studyappdemo.beans.TitleFragmentBean;

import java.util.List;

/**
 * Created by shimanqiang on 17/1/9.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<TitleFragmentBean> data;

    public MyFragmentPagerAdapter(FragmentManager fm, List<TitleFragmentBean> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = data.get(position).getTitle();
        if (TextUtils.isEmpty(title)) {
            //return super.getPageTitle(position);
            return null;
        }
        return title;
    }
}
