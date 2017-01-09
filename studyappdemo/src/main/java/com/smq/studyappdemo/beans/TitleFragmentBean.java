package com.smq.studyappdemo.beans;

import android.support.v4.app.Fragment;

/**
 * Created by shimanqiang on 17/1/9.
 */

public class TitleFragmentBean {
    private String title;
    private Fragment fragment;

    public TitleFragmentBean(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
