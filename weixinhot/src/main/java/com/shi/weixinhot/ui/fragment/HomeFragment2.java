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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shi.weixinhot.R;
import com.shi.weixinhot.beans.CategoryBean;
import com.shi.weixinhot.beans.ItemBean;
import com.shi.weixinhot.tools.HttpUtil;
import com.shi.weixinhot.tools.LoadDataManager;
import com.shi.weixinhot.tools.LogUtil;
import com.shi.weixinhot.ui.acvitity.ContentActivity;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by shimanqiang on 16/12/26.
 */

public class HomeFragment2 extends Fragment {
    private final static String TAG = HomeFragment2.class.getSimpleName();

    public final static String CATEGORY = "category";

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private CategoryBean categoryBean;
    private LayoutInflater mInflater;

    public static HomeFragment2 newInstance(CategoryBean categoryBean) {
        HomeFragment2 fragment = new HomeFragment2();
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

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                }
//            });
//        }

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isLoading && !isNothing && isSlideToBottom(recyclerView)) {
                    LogUtil.w(TAG, "------->isSlideToBottom:" + isSlideToBottom(recyclerView));
                    // 显示 loading 状态
                    // 加载数据
                    Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }

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


    class MyAdapter extends RecyclerView.Adapter {
        private List<ItemBean> mDatas;

        public void setDatas(List<ItemBean> mDatas) {
            this.mDatas = mDatas;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemViewHolder(mInflater.inflate(R.layout.fragment_home_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder newHolder = (ItemViewHolder) holder;
            final ItemBean itemBean = mDatas.get(position);
            newHolder.title.setText(itemBean.getTitle());
            newHolder.author.setText(itemBean.getAuthor());
            //newHolder.count.setText("1000+");//TODO
            newHolder.time.setText(itemBean.getAboutTime());
            /**
             * 异步加载图标（默认的）
             * newHolder.iv_img.setImageResource(R.mipmap.wgtx);
             */

            /**
             * 第一版
             * asyncLoadImg(itemBean.getImgUrl(), newHolder.iv_img);
             */
            /**
             * 第二版
             * DownLoadTask downLoadTask = new DownLoadTask(newHolder.iv_img);
             * downLoadTask.execute(itemBean.getImgUrl());
             */
            /**
             * 第三版
             * 使用第三方库 Glide加载图片，太好用了
             */
            Glide.with(HomeFragment2.this).load(itemBean.getImgUrl()).into(newHolder.iv_img);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = itemBean.getUrl();
                    Intent intent = new Intent(getActivity(), ContentActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            });
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
