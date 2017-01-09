package com.smq.studyappdemo.ui.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;


public abstract class BaseFragment extends Fragment {
    protected static final String TAG = "BaseFragment";

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_base, container, false);
//    }


    /**
     * 根据ID查找View
     *
     * @param id  必须是idRes
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewById(@IdRes int id) {
        return (T) getView().findViewById(id);
    }
}
