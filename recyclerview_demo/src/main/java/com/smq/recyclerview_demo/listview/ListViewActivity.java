package com.smq.recyclerview_demo.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.smq.recyclerview_demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * http://blog.csdn.net/goodchangyong/article/details/51324183
 * http://www.lcode.org/recyclerview%E5%AE%8C%E5%85%A8%E8%A7%A3%E6%9E%90%E8%AE%A9%E4%BD%A0%E4%BB%8E%E6%AD%A4%E7%88%B1%E4%B8%8Arecyclerview/
 */
public class ListViewActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        /**
         * 实例化
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        /**
         * 设置LayoutManager
         * 参数：上下文，纵向（方向），false不翻转
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        /**
         * 设置适配器Adapter
         */
        mAdapter = new ListViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);


        /**
         * 参考：http://blog.csdn.net/lmj623565791/article/details/45059587
         */
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        /**
         * 初始化数据
         */
        initData();
    }

    int[] colors = new int[]{
            android.R.color.holo_red_dark,
            android.R.color.holo_blue_dark,
            android.R.color.holo_orange_dark
    };

    private void initData() {
        List<DataModel> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

            int type = (int) (Math.random() * 3 + 1);

            DataModel data = new DataModel();
            data.type = type;
            data.avatorColor = colors[type - 1]; //注意数组下标是从0开始
            data.name = "name:" + i;
            data.content = "content:" + i;
            data.contentColor = colors[(int) (Math.random() * 3)];

            list.add(data);
        }

        /**
         * 设置数据到Adapter
         */
        mAdapter.addData(list);

        /**
         * 发送数据更改通知，更新UI
         */
        mAdapter.notifyDataSetChanged();
    }


}
