package com.shi.weixinhot.ui.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by shimanqiang on 16/12/29.
 */

public class EasyRecycleView extends RecyclerView implements View.OnTouchListener {
    /**
     * Y轴滑动的距离
     */
    private float moveY = 0;


    public EasyRecycleView(Context context) {
        this(context, null);
    }

    public EasyRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float downY = 0;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();//float DownY
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = event.getY() - downY;//y轴距离
                break;
            case MotionEvent.ACTION_UP:
                moveY = 0;
                break;
        }
        /**
         * 返回 false事件会继续传递
         * 返回 true 事件不会继续传递
         */
        return false;
    }


    /**
     * 监听滚动事件
     *
     * @param listener
     */
    private boolean isLoading = false;//是否正在加载数据

    @Override
    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        super.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && isSlideToBottom(recyclerView)) {
                    if (moveY > 5 && loadingDataListener != null) {
                        new LoadDataAsyncTask(loadingDataListener).execute();
                    }
                }
            }
        });
    }

    /**
     * 判断是否在了底部
     * RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
     * RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
     *
     * @param recyclerView
     * @return
     */
    private boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.getChildCount() == 0) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    /**
     * 下拉加载监听
     */
    private OnLoadingDataListener loadingDataListener;

    public void setOnLoadingDataListener(OnLoadingDataListener loadingDataListener) {
        this.loadingDataListener = loadingDataListener;
    }

    interface OnLoadingDataListener {
        void onLoading();

        void onSuccess();
    }


    /**
     * 设置适配器
     *
     * @param adapter
     */
    private EasyRecycleViewAdapter mAdapter;

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null && adapter instanceof EasyRecycleViewAdapter) {
            this.mAdapter = (EasyRecycleViewAdapter) adapter;
        }
    }


    /**
     * Item点击事件设置
     */
    public void setOnItemClickListener(EasyRecycleViewAdapter.OnItemClickListener listener) {
        if (mAdapter != null) {
            if (listener != null) {
                mAdapter.setOnItemClickListener(listener);
            }
        }
    }

    /**
     * Item长按事件设置
     */
    public void setOnItemLongClickListener(EasyRecycleViewAdapter.OnItemLongClickListener listener) {
        if (mAdapter != null) {
            if (listener != null) {
                mAdapter.setOnItemLongClickListener(listener);
            }
        }
    }


    /**
     * 加载数据的Task
     */
    class LoadDataAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private OnLoadingDataListener loadingDataListener;

        public LoadDataAsyncTask(OnLoadingDataListener loadingDataListener) {
            this.loadingDataListener = loadingDataListener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            isLoading = true;
            loadingDataListener.onLoading();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            isLoading = false;
            loadingDataListener.onSuccess();
        }
    }

}
