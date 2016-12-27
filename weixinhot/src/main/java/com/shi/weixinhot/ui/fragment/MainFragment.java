package com.shi.weixinhot.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shi.weixinhot.R;
import com.shi.weixinhot.beans.CategoryBean;
import com.shi.weixinhot.beans.ItemBean;
import com.shi.weixinhot.tools.LoadDataManager;

import java.util.List;

/**
 * Created by shimanqiang on 16/12/26.
 */

public class MainFragment extends Fragment {
    public final static String CATEGORY = "category";

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private CategoryBean categoryBean;
    private LayoutInflater mInflater;

    public static MainFragment newInstance(CategoryBean categoryBean) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable(CATEGORY, categoryBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(getContext());
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

        LoadDataManager.getInstance().generateFixData(categoryBean.getUrl(), new LoadDataManager.Callback<List<ItemBean>>() {
            @Override
            public void onSuccess(List<ItemBean> obj) {

                Log.e("test", "success:" + obj.toString());
                Message message = mHanler.obtainMessage();
                message.what = 100;
                message.obj = obj;
                mHanler.sendMessage(message);

            }
        });

    }

    private Handler mHanler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("test2", "dfsdfsdf");
            switch (msg.what) {
                case 100:
                    /**
                     * 设置数据到Adapter
                     */
                    mAdapter.setDatas((List<ItemBean>) (msg.obj));

                    /**
                     * 发送数据更改通知，更新UI
                     */
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };


    class BannerViewHolder extends RecyclerView.ViewHolder {

        public BannerViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            avatar.setBackgroundResource(android.R.color.holo_red_dark);//TODO
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }


    class MyAdapter extends RecyclerView.Adapter {
        private List<ItemBean> mDatas;

        public void setDatas(List<ItemBean> mDatas) {
            this.mDatas = mDatas;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemViewHolder(mInflater.inflate(R.layout.fragment_main_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder newHolder = (ItemViewHolder) holder;
            ItemBean itemBean = mDatas.get(position);
            newHolder.name.setText(itemBean.getTitle());
        }

        @Override
        public int getItemCount() {
            return (mDatas == null) ? 0 : mDatas.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
    }

}
