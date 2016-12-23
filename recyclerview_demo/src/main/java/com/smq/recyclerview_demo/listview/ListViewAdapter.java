package com.smq.recyclerview_demo.listview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.smq.recyclerview_demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shimanqiang on 16/12/23.
 */

public class ListViewAdapter extends RecyclerView.Adapter {
    List<DataModel> mList = new ArrayList<>();

    public void addData(List<DataModel> data) {
        if (data != null) {
            mList = data;
        }
    }

    LayoutInflater mLayoutInflater;

    public ListViewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DataModel.TYPE_ONE:
                return new TypeOneViewHodler(mLayoutInflater.inflate(R.layout.list_view_item_one, parent, false));
            case DataModel.TYPE_TWO:
                return new TypeTwoViewHodler(mLayoutInflater.inflate(R.layout.list_view_item_two, parent, false));
            case DataModel.TYPE_THREE:
                return new TypeThreeViewHodler(mLayoutInflater.inflate(R.layout.list_view_item_three, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        DataModel model = mList.get(position);
        switch (viewType) {
            case DataModel.TYPE_ONE:
                TypeOneViewHodler oneViewHodler = (TypeOneViewHodler) holder;
                oneViewHodler.avatar.setBackgroundResource(model.avatorColor);
                oneViewHodler.name.setText(model.name);
                break;
            case DataModel.TYPE_TWO:
                TypeTwoViewHodler typeTwoViewHodler = (TypeTwoViewHodler) holder;
                typeTwoViewHodler.avatar.setBackgroundResource(model.avatorColor);
                typeTwoViewHodler.name.setText(model.name);
                typeTwoViewHodler.content.setText(model.content);
                break;
            case DataModel.TYPE_THREE:
                TypeThreeViewHodler typeThreeViewHodler = (TypeThreeViewHodler) holder;
                typeThreeViewHodler.avatar.setBackgroundResource(model.avatorColor);
                typeThreeViewHodler.name.setText(model.name);
                typeThreeViewHodler.content.setText(model.content);
                typeThreeViewHodler.contentColor.setBackgroundResource(model.contentColor);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 这个方法不自动生成，需要覆写：code--generate
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mList.get(position).type;
    }
}
