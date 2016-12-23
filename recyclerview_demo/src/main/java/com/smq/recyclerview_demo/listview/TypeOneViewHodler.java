package com.smq.recyclerview_demo.listview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smq.recyclerview_demo.R;

/**
 * Created by shimanqiang on 16/12/23.
 */

public class TypeOneViewHodler extends RecyclerView.ViewHolder {
    ImageView avatar;
    TextView name;

    public TypeOneViewHodler(View itemView) {
        super(itemView);

        avatar = (ImageView) itemView.findViewById(R.id.avatar);
        name = (TextView) itemView.findViewById(R.id.name);
    }
}
