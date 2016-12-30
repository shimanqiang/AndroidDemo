package com.shi.weixinhot.ui.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.shi.weixinhot.tools.LogUtil;

/**
 * Created by shimanqiang on 16/12/29.
 */

public class EasyRecycleView extends RecyclerView implements View.OnTouchListener {
    private static final String TAG = EasyRecycleView.class.getSimpleName();


    public EasyRecycleView(Context context) {
        this(context, null);
    }

    public EasyRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        /**
         * 设置Touch监听器
         */
        setOnTouchListener(this);
        /**
         * 设置Item增加、移除动画
         */
        setItemAnimator(new DefaultItemAnimator());
        /**
         * 设置默认的分割线
         */
        addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    /**
     * Y轴滑动的距离计算
     */
    private float moveY = 0;
    private float downY = 0;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                moveY = 0;
                downY = event.getY();//float DownY
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = event.getY() - downY;//y轴距离
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        /**
         * 返回 false事件会继续传递
         * 返回 true 事件不会继续传递
         */
        //return false;
        return super.onTouchEvent(event);
    }


    /**
     * 监听滚动事件
     *
     * @param listener
     */
    private boolean isLoading = false;//是否正在加载数据


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
     * 上拉加载监听
     */
    private OnLoadingDataListener loadingDataListener;


    /**
     * 设置上拉加载的监听事件的方法
     *
     * @param listener
     */
    public void setOnLoadingDataListener(OnLoadingDataListener listener) {
        this.loadingDataListener = listener;
        super.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && isSlideToBottom(recyclerView)) {
//                    if (loadingDataListener != null) {
//                        new LoadDataAsyncTask(loadingDataListener).execute();
//                    }
                    LogUtil.e(TAG, "moveY:" + moveY);
                    if (-moveY > 300 && loadingDataListener != null) {
                        new LoadDataAsyncTask(loadingDataListener).execute();
                    }
//                    EasyRecycleViewAdapter adapter = (EasyRecycleViewAdapter) getAdapter();
//                    adapter.getHeadView().itemView.getMeasuredHeight();
                }
            }
        });
    }

    public interface OnLoadingDataListener<T> {
        T onLoading();

        void onSuccess(T items);
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
    class LoadDataAsyncTask extends AsyncTask<Void, Void, Object> {
        private OnLoadingDataListener loadingDataListener;

        public LoadDataAsyncTask(OnLoadingDataListener loadingDataListener) {
            this.loadingDataListener = loadingDataListener;
        }

        @Override
        protected Object doInBackground(Void... voids) {
            isLoading = true;
            return loadingDataListener.onLoading();
        }

        @Override
        protected void onPostExecute(Object obj) {
            isLoading = false;
            loadingDataListener.onSuccess(obj);
        }
    }

}
