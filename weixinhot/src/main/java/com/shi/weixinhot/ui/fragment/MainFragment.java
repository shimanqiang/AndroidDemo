package com.shi.weixinhot.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shi.weixinhot.R;
import com.shi.weixinhot.model.CategoryBean;
import com.shi.weixinhot.model.ItemBean;

import java.util.List;

/**
 * Created by shimanqiang on 16/12/26.
 */

public class MainFragment extends Fragment {
    public final static String CATEGORY = "category";

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private CategoryBean categoryBean;

    public static MainFragment newInstance(CategoryBean categoryBean) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable(CATEGORY, categoryBean);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        Bundle args = getArguments();
        if (args != null) {
            categoryBean = (CategoryBean) args.getSerializable(CATEGORY);

            initData();
        }

        return v;
    }

    /**
     * 加载数据
     */
    private void initData() {
        /**
         * 设置数据到Adapter
         */
        mAdapter.setDatas(null);

        /**
         * 发送数据更改通知，更新UI
         */
        mAdapter.notifyDataSetChanged();
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {

        public BannerViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }


    class MyAdapter extends RecyclerView.Adapter {
        private List<ItemBean> mDatas;

        public void setDatas(List<ItemBean> mDatas) {
            this.mDatas = mDatas;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
    }

}
