package com.why.demo2.MYTuijian;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.why.demo2.Bean.LuoBoBean;
import com.why.demo2.MainActivity;
import com.why.demo2.MyMiaoshaAdpter.MiaoshaHolder;
import com.why.demo2.R;

import java.util.List;

/**
 * Created by 小慧莹 on 2017/12/27.
 */

public class MyTuijianAdpter extends RecyclerView.Adapter<MyTuijianHolder> {
    private  Context context;
    private  List<LuoBoBean.TuijianBean.ListBean> tui;

    public MyTuijianAdpter(Context context, List<LuoBoBean.TuijianBean.ListBean> tui) {
        this.context=context;
        this.tui=tui;
    }

    @Override
    public MyTuijianHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.tuijian_liatview, null);
        MyTuijianHolder tuijianHolder=new MyTuijianHolder(view) ;
        return tuijianHolder;
    }

    @Override
    public void onBindViewHolder(MyTuijianHolder holder, int position) {
        String s = tui.get(position).getImages().split("\\|")[0] + "";
        Glide.with(context).load(s).into(holder.iv);
        holder.name.setText(tui.get(position).getTitle());
        holder.price.setText(tui.get(position).getPrice()+"");


    }

    @Override
    public int getItemCount() {
        return tui.size();
    }
}
