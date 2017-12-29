package com.why.demo2.MyJiugonggeAapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.why.demo2.R;

/**
 * Created by 小慧莹 on 2017/12/26.
 */

public class MyListHolder extends RecyclerView.ViewHolder {

    public ImageView iv;
    public  TextView tv;

    public MyListHolder(View itemView) {
        super(itemView);
          iv = itemView.findViewById(R.id.iv);
          tv = itemView.findViewById(R.id.tv);

    }
}
