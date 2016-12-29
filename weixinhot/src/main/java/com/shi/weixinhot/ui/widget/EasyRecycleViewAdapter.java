package com.shi.weixinhot.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shimanqiang on 16/12/29.
 */

public abstract class EasyRecycleViewAdapter<T> extends RecyclerView.Adapter {
    private LinkedList<T> mData = new LinkedList<>();
    protected LayoutInflater mInflater;
    protected Context context;

    private EasyRecycleViewAdapter() {
    }

    public EasyRecycleViewAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public EasyRecycleViewAdapter(Context context, List<T> data) {
        this(context);
        if (data instanceof LinkedList) {
            this.mData = (LinkedList<T>) data;
        } else {
            for (T item : data) {
                mData.add(item);
            }
        }
    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<T> getData() {
        return mData;
    }

    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(List<T> data) {
        resetData(data);
    }

    /**
     * 重置数据
     *
     * @param data
     */
    public void resetData(List<T> data) {
        if (data instanceof LinkedList) {
            this.mData = (LinkedList<T>) data;
        } else {
            for (T item : data) {
                mData.add(item);
            }
        }
    }

    /**
     * 添加数据到前面
     *
     * @param data
     */
    public void addData2First(T data) {
        mData.addFirst(data);
    }

    /**
     * 添加数据到前面
     *
     * @param data
     */
    public void addData2First(List<T> data) {
        for (T item : data) {
            mData.addFirst(item);
        }
    }

    /**
     * 添加数据到尾部
     *
     * @param data
     */
    public void addData2Foot(T data) {
        mData.add(data);
    }

    /**
     * 添加数据到尾部
     *
     * @param data
     */
    public void addData2Foot(List<T> data) {
        mData.addAll(data);
    }


    private RecyclerView.ViewHolder headViewHoler;
    private RecyclerView.ViewHolder footViewHoler;
    private int headViewCount = 0;
    private int footViewCount = 0;

    public void addHeadView(RecyclerView.ViewHolder itemViewHolder) {
        headViewHoler = itemViewHolder;
        headViewCount = 1;
    }

    public void addFootView(RecyclerView.ViewHolder itemViewHolder) {
        footViewHoler = itemViewHolder;
        footViewCount = 1;
    }

    public RecyclerView.ViewHolder getHeadView() {
        return headViewHoler;
    }

    public RecyclerView.ViewHolder getFootView() {
        return footViewHoler;
    }


    /**
     * ViewHolder类型
     */
    interface ViewHolderType {
        int HEAD_VIEW = -1;
        int FOOT_VIEW = -2;
        int ITEM_VIEW = -3;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ViewHolderType.HEAD_VIEW:
                viewHolder = headViewHoler;
                break;
            case ViewHolderType.FOOT_VIEW:
                viewHolder = footViewHoler;
                break;
            default:
                viewHolder = onCreateViewHolder$(parent, viewType);
                break;
        }
        return viewHolder;
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {
            switch (getItemViewType(position)) {
                case ViewHolderType.HEAD_VIEW:
                    onBindHeadViewHolder$(holder, position);
                    break;
                case ViewHolderType.FOOT_VIEW:
                    onBindFootViewHolder$(holder, position);
                    break;
                default:
                    onBindViewHolder$(holder, position);
                    break;
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onClick(view, position);
                    }
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (itemLongClickListener != null) {
                        return itemLongClickListener.onLongClick(view, position);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public final int getItemCount() {
        int itemViewCount = (mData == null) ? 0 : mData.size();
        return itemViewCount + headViewCount + footViewCount;
    }

    @Override
    public final int getItemViewType(int position) {
        if (headViewCount > 0 && position == 0) {
            return ViewHolderType.HEAD_VIEW;
        } else if (footViewCount > 0 && position == (getItemCount() - 1)) {
            return ViewHolderType.FOOT_VIEW;
        } else {
            //return ViewHolderType.ITEM_VIEW.value();
            return getItemViewType$(position);
        }
    }


    /**
     * 提供给子类的RecycleViewAdapter的回调
     */

    protected abstract RecyclerView.ViewHolder onCreateViewHolder$(ViewGroup parent, int viewType);

    protected int getItemViewType$(int position) {
        return 0;
    }

    protected abstract void onBindViewHolder$(RecyclerView.ViewHolder holder, int position);

    protected void onBindHeadViewHolder$(RecyclerView.ViewHolder holder, int position) {

    }

    protected void onBindFootViewHolder$(RecyclerView.ViewHolder holder, int position) {

    }


    /**
     * 点击事件接口
     */
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onLongClick(View view, int position);
    }
}
