package com.why.demo2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.why.demo2.ApI.API;
import com.why.demo2.Bean.JiuGoGeBean;
import com.why.demo2.Bean.LuoBoBean;
import com.why.demo2.MYTuijian.MyTuijianAdpter;
import com.why.demo2.MyJiugonggeAapter.MyListAdpter;
import com.why.demo2.MyMiaoshaAdpter.MiaoshaAdpter;
import com.why.demo2.Resou.SousuoActivity;
import com.why.demo2.Utils.OkHttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private List<LuoBoBean.MiaoshaBean.ListBeanX> list;
    private List<LuoBoBean.TuijianBean.ListBean> tui;
    private RecyclerView jiu;
    private RecyclerView miaosha;
    private RecyclerView tuijian;
    private XBanner xBanner;
    private List<LuoBoBean.DataBean> data;
    private List<String> imgesUrl;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                xBanner.setData(imgesUrl,null);
                xBanner.setPoinstPosition(XBanner.RIGHT);
                xBanner.setmAdapter(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(MainActivity.this).load(imgesUrl.get(position)).into((ImageView) view);

                    }
                });

                xBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(XBanner banner, int position) {
                        Intent in=new Intent(MainActivity.this,WebActivity.class);

                        in.putExtra("url",data.get(position).getUrl());
                        startActivity(in);
                    }
                });
//            }else if (msg.what == 2){
//                //适配器
//                jiuAdapter = new MyListAdpter(MainActivity.this,jiuBeanData);
//                jiu.setAdapter(jiuAdapter);
//                //显示方式
//                jiu.setLayoutManager(new GridLayoutManager(MainActivity.this,2, OrientationHelper.HORIZONTAL,false));
            }else if(msg.what==3){
                MiaoshaAdpter miaoshaAdpter=new MiaoshaAdpter(MainActivity.this,list);
                rlv_miaosha.setAdapter(miaoshaAdpter);
                rlv_miaosha.setLayoutManager(new LinearLayoutManager(MainActivity.this,OrientationHelper.HORIZONTAL,false));
            }else if(msg.what==4){
                MyTuijianAdpter myTuijianAdpter=new MyTuijianAdpter(MainActivity.this,tui);
               rlv_tuijian.setAdapter(myTuijianAdpter);
               rlv_tuijian.setLayoutManager(new GridLayoutManager(MainActivity.this,2,OrientationHelper.VERTICAL,false));
            }
        }
    };
    private List<JiuGoGeBean.DataBean> jiuBeanData;
    private MyListAdpter jiuAdapter;
    private RecyclerView rlv_miaosha;
    private RecyclerView rlv_tuijian;
    private ViewFlipper vf;
    private TextView sousuo;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        findView();
        //轮播
        lunBo();
        //九宫格
        jiuGongGe();
        //京东秒杀图片
        Miaosha();
        //推荐
        Tuijian();
        //跑马灯
        PaoMaD();
    }

    private void PaoMaD() {
        vf.addView(View.inflate(MainActivity.this,R.layout.frag_sy_gg,null));
        vf.addView(View.inflate(MainActivity.this,R.layout.frag_sy_gg2,null));
        vf.addView(View.inflate(MainActivity.this,R.layout.frag_sy_gg3,null));
    }

    private void Tuijian() {
        OkHttpUtil.doGet(API.Lunbo_API, new Callback() {



            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LuoBoBean title=new Gson().fromJson(string,LuoBoBean.class);
                tui = title.getTuijian().getList();
                handler.sendEmptyMessage(4);
            }
        });
    }

    private void Miaosha() {
        OkHttpUtil.doGet(API.Lunbo_API, new Callback() {



            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LuoBoBean title=new Gson().fromJson(string,LuoBoBean.class);
                list = title.getMiaosha().getList();
            handler.sendEmptyMessage(3);

            }
        });
    }

    private void findView() {
        xBanner = findViewById(R.id.xbanner);
//        jiu = findViewById(R.id.rc);
        rlv_miaosha = findViewById(R.id.rlv_miaosha);
        rlv_tuijian = findViewById(R.id.rlv_tuijian);
        vf = findViewById(R.id.vf);
        sousuo = findViewById(R.id.tv_sousuo);
sousuo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,SousuoActivity.class);
        startActivity(intent);
    }
});

    }
    //图片轮播
    private void lunBo() {
        OkHttpUtil.doGet(API.Lunbo_API, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                LuoBoBean guangGaoBean = gson.fromJson(string, LuoBoBean.class);
                data = guangGaoBean.getData();
                imgesUrl = new ArrayList<>();
                for (LuoBoBean.DataBean dataBean : data)
                {
                    String icon1 = dataBean.getIcon();
                    imgesUrl.add(icon1);
                }
                handler.sendEmptyMessage(1);
            }
        });
    }

    //九宫格
    private void jiuGongGe() {
        List<Integer> tup=new ArrayList<>();
        tup.add(R.drawable.guang2);
        tup.add(R.drawable.guang3);
        OkHttpUtil.doGet(API.SHOPPING_API, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                String string = response.body().string();
//                Gson gson = new Gson();
//                JiuGoGeBean jiuBean = gson.fromJson(string, JiuGoGeBean.class);
//                jiuBeanData = jiuBean.getData();
//                handler.sendEmptyMessage(2);
            }
        });
    }

}
