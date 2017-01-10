package com.smq.studyappdemo.ui.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smq.studyappdemo.R;
import com.smq.studyappdemo.beans.TitleFragmentBean;
import com.smq.studyappdemo.ui.adapter.MyFragmentPagerAdapter;
import com.smq.studyappdemo.ui.view.gank.GankFourFragment;
import com.smq.studyappdemo.ui.view.gank.GankOneFragment;
import com.smq.studyappdemo.ui.view.gank.GankThreeFragment;
import com.smq.studyappdemo.ui.view.gank.GankTwoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankFragment extends BaseFragment {
    private List<TitleFragmentBean> data = new ArrayList<>();

    public GankFragment() {
        // Required empty public constructor
    }

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_gank, container, false);
//        mViewPager = (ViewPager) view.findViewById(R.id.gank_viewpager);
//        return view;

        return inflater.inflate(R.layout.fragment_gank, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        //
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), data);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        adapter.notifyDataSetChanged();

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void initView() {
        mViewPager = findViewById(R.id.gank_viewpager);
        mTabLayout = findViewById(R.id.gank_tabs);
    }


    private void initFragmentData() {
        data.add(new TitleFragmentBean("每日推荐", new GankOneFragment()));
        data.add(new TitleFragmentBean("福利", new GankTwoFragment()));
        data.add(new TitleFragmentBean("干活定制", new GankThreeFragment()));
        data.add(new TitleFragmentBean("大安卓", new GankFourFragment()));
    }
}
