package com.shi.weixinhot.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shi.weixinhot.R;
import com.shi.weixinhot.beans.ItemBean;

/**
 * Created by shimanqiang on 16/12/29.
 */

public class HomeAdapter extends BaseRecycleViewAdapter<ItemBean> {

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder$(ViewGroup parent, int viewType) {
        return new HomeAdapter.ItemViewHolder(mInflater.inflate(R.layout.fragment_home_item, parent, false));
    }

    @Override
    protected void onBindViewHolder$(RecyclerView.ViewHolder holder, int position) {
        HomeAdapter.ItemViewHolder newHolder = (HomeAdapter.ItemViewHolder) holder;
        final ItemBean itemBean = getData().get(position);
        newHolder.title.setText(itemBean.getTitle());
        newHolder.author.setText(itemBean.getAuthor());
        //newHolder.count.setText("1000+");//TODO
        newHolder.time.setText(itemBean.getAboutTime());

        /**
         * 加载图片
         */
        Glide.with(context).load(itemBean.getImgUrl()).into(newHolder.iv_img);
    }

    @Override
    protected void onBindFootViewHolder$(RecyclerView.ViewHolder holder, int position) {
        super.onBindFootViewHolder$(holder, position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView title;
        TextView author;
        TextView count;
        TextView time;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            //iv_img.setBackgroundResource(android.R.color.holo_red_dark);//TODO
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            count = (TextView) itemView.findViewById(R.id.count);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        public BannerViewHolder(View itemView) {
            super(itemView);
        }
    }

}
