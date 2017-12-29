package com.why.demo2.MyMiaoshaAdpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.why.demo2.Bean.JiuGoGeBean;
import com.why.demo2.Bean.LuoBoBean;
import com.why.demo2.MainActivity;
import com.why.demo2.R;

import java.util.List;

/**
 * Created by 小慧莹 on 2017/12/27.
 */

public class MiaoshaAdpter extends RecyclerView.Adapter<MiaoshaHolder>{

        private  Context context;
        private List<LuoBoBean.MiaoshaBean.ListBeanX> list;

        public MiaoshaAdpter(Context context, List<LuoBoBean.MiaoshaBean.ListBeanX> list) {
            this.context = context;
            this.list = list;
        }
    @Override
    public MiaoshaHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=View.inflate(context, R.layout.miaosha_listview,null);
        MiaoshaHolder miaoshaHolder=new MiaoshaHolder(view);
        return miaoshaHolder;
    }

    @Override
    public void onBindViewHolder(MiaoshaHolder holder, int position) {
        String s = list.get(position).getImages().split("\\|")[0] + "";
        Glide.with(context).load(s).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
