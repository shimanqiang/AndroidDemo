package com.shi.weixinhot.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.shi.weixinhot.R;
import com.shi.weixinhot.beans.CategoryBean;
import com.shi.weixinhot.beans.ItemBean;
import com.shi.weixinhot.tools.HttpUtil;
import com.shi.weixinhot.tools.LoadDataManager;
import com.shi.weixinhot.tools.LogUtil;
import com.shi.weixinhot.ui.acvitity.ContentActivity;
import com.shi.weixinhot.ui.adapter.HomeAdapter;
import com.shi.weixinhot.ui.widget.EasyRecycleView;
import com.shi.weixinhot.ui.widget.EasyRecycleViewAdapter;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by shimanqiang on 16/12/26.
 */

public class HomeFragment extends Fragment {
    private final static String TAG = HomeFragment.class.getSimpleName();

    public final static String CATEGORY = "category";

    private EasyRecycleView mRecyclerView;
    //private MyAdapter mAdapter;
    private HomeAdapter mAdapter;
    private CategoryBean categoryBean;
    private LayoutInflater mInflater;

    public static HomeFragment newInstance(CategoryBean categoryBean) {
        HomeFragment fragment = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (EasyRecycleView) v.findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // mAdapter = new MyAdapter();
        mAdapter = new HomeAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnItemClickListener(new EasyRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                ItemBean itemBean = mAdapter.getData().get(position);
                String url = itemBean.getUrl();
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        //添加分割线
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        Bundle args = getArguments();
        if (args != null) {
            categoryBean = (CategoryBean) args.getSerializable(CATEGORY);

            initData();
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                }
//            });
//        }

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                int slop = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
//                LogUtil.w(TAG, "------->aaa :" + newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                LogUtil.w(TAG, "------->isSlideToBottom :" + dx + "@@@@@@" + dy);
//                if (!isLoading && !isNothing && isSlideToBottom(recyclerView)) {
//                    // LogUtil.w(TAG, "------->isSlideToBottom:" + isSlideToBottom(recyclerView));
//                    // 显示 loading 状态
//                    // 加载数据
//                    //Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
//                    int slop = ViewConfiguration.get(getContext()).getScaledOverflingDistance();
//
//                    LogUtil.w(TAG, "------->isSlideToBottom :" + slop);
//                    if (slop > 20) {
//                        LogUtil.e(TAG, "------->isSlideToBottom >>>>>>>:" + slop);
//                        //Toast.makeText(getActivity(), "bottom upup", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//        });


        mRecyclerView.setOnLoadingDataListener(new EasyRecycleView.OnLoadingDataListener<List<ItemBean>>() {
            @Override
            public List<ItemBean> onLoading() {
                String nextUrl = categoryBean.getNextUrl();
                return LoadDataManager.getInstance().generateFixData(nextUrl);
            }

            @Override
            public void onSuccess(List<ItemBean> items) {
                int currentPos = mAdapter.getData().size();
                mAdapter.addData2Foot(items);
                Toast.makeText(getContext(), "加载完毕", Toast.LENGTH_SHORT).show();
                //layoutManager.scrollToPositionWithOffset(currentPos, 3);
                layoutManager.scrollToPosition(currentPos + 1);
            }
        });

        return v;
    }

    /**
     * //能够识别的最小滑动举例
     * ViewConfiguration.getScaledTouchSlop();
     * //最小加速度
     * ViewConfiguration.getScaledMinimumFlingVelocity();
     * //最大加速度
     * ViewConfiguration.getScaledMaximumFlingVelocity();
     * //滚动距离
     * ViewConfiguration.getScaledOverscrollDistance();
     * //Filing距离
     * ViewConfiguration.getScaledOverflingDistance();
     */
    private boolean isLoading = false;//是否正在加载数据
    private boolean isNothing = false;//是否已经没有更多的数据

    /**
     * 判断是否在了底部
     * RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
     * RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
     *
     * @param recyclerView
     * @return
     */
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.getChildCount() == 0) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }


    /**
     * 加载数据
     */
    private void initData() {

        LoadDataManager.getInstance().generateFixData(categoryBean.getUrl(), new LoadDataManager.Callback<List<ItemBean>>() {
            @Override
            public void onSuccess(List<ItemBean> obj) {
                LogUtil.d(TAG, "success:" + obj.toString());

                Message message = mHanler.obtainMessage();
                message.what = LOAD_DATA;
                message.obj = obj;
                mHanler.sendMessage(message);

            }
        });

    }

    private final static int LOAD_DATA = 100; //加载数据
    private Handler mHanler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_DATA:
                    /**
                     * 设置数据到Adapter
                     */
                    mAdapter.addData((List<ItemBean>) (msg.obj));

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


    private void asyncLoadImg(final String url, final ImageView iv_img) {
        HttpUtil.executePost(url, null, new HttpUtil.ResponseCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                final BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv_img.setImageDrawable(drawable);
                    }
                });
            }
        });
    }

    /**
     * 异步加载图片
     */
    class DownLoadTask extends AsyncTask<String, Void, BitmapDrawable> {
        private ImageView mImageView;
        String url;

        public DownLoadTask(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            url = params[0];
            Bitmap bitmap = downLoadBitmap(url);
            BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
            return drawable;
        }

        private Bitmap downLoadBitmap(String url) {
            Response response = HttpUtil.executeGetSync(url);
            return BitmapFactory.decodeStream(response.body().byteStream());
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            super.onPostExecute(drawable);

            if (mImageView != null && drawable != null) {
                mImageView.setImageDrawable(drawable);
            }
        }
    }
}
