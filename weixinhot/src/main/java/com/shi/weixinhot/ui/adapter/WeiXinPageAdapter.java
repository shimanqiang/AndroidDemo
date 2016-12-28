package com.shi.weixinhot.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shi.weixinhot.beans.CategoryBean;
import com.shi.weixinhot.tools.LoadDataManager;
import com.shi.weixinhot.ui.fragment.FixFragment;
import com.shi.weixinhot.ui.fragment.HomeFragment;

import java.util.List;

/**
 * Created by shimanqiang on 16/12/26.
 */

public class WeiXinPageAdapter extends FragmentPagerAdapter {
    List<CategoryBean> mData = LoadDataManager.getInstance().generateCategory();

    public WeiXinPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return HomeFragment.newInstance(mData.get(0));
        }
        return FixFragment.newInstance(mData.get(position));
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getName();
    }
}
