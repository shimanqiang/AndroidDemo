package com.shi.weixinhot.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shi.weixinhot.model.CategoryBean;
import com.shi.weixinhot.tools.LoadDataManager;
import com.shi.weixinhot.ui.fragment.PlaceholderFragment;

import java.util.List;

/**
 * Created by shimanqiang on 16/12/26.
 */

public class MainPageAdapter extends FragmentPagerAdapter {
    List<CategoryBean> mData = LoadDataManager.getInstance().generateCategory();

    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new PlaceholderFragment().newInstance(position);
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
