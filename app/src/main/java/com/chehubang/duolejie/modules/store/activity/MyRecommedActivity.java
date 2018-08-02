package com.chehubang.duolejie.modules.store.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.model.Recommend;
import com.chehubang.duolejie.modules.home.adapter.MyRecommendAdapter;
import com.chehubang.duolejie.modules.store.presenter.MyRecommendPresenter;
import com.chehubang.duolejie.utils.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.mvp.activity.MainView;

/**
 * 我的-我的推荐人
 * Created by Administrator on 2018/7/17 0017.
 */
public class MyRecommedActivity extends BaseActivity<MyRecommendPresenter> implements
        OnRefreshLoadmoreListener,
        MainView {
    @BindView(R.id.iv_order_back)
    ImageView ivOrderBack;
    @BindView(R.id.lv_recommed)
    RecyclerView content;
    @BindView(R.id.srf_myrecommed)
    SmartRefreshLayout refreshLayout;

    private MyRecommendAdapter mAdapter;
    private List<Recommend.UserRecommendListBean> list = new ArrayList<>();

    private int page = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommed);
        ButterKnife.bind(this);

        mAdapter = new MyRecommendAdapter(list, this);
        content.setLayoutManager(new LinearLayoutManager(this));
        content.setAdapter(mAdapter);

        refreshLayout.setOnRefreshLoadmoreListener(this);
        //设置 Header 为 Material风格
        refreshLayout.setRefreshHeader(new MaterialHeader(this).setColorSchemeColors(Color.BLUE));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        initDate();
    }

    public void initDate() {
        page = 1;
        mvpPresenter.getRecommendList(1, page);
    }

    @Override
    protected MyRecommendPresenter createPresenter() {
        return new MyRecommendPresenter(this);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        //加载更多
        page += 1;
        mvpPresenter.getRecommendList(1, page);
        refreshLayout.finishLoadmore(2000);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        //刷新
        page = 1;
        if (!list.isEmpty()) {
            list.clear();
            mvpPresenter.getRecommendList(1, page);
        }
        refreshLayout.finishRefresh(2000);
    }

    @OnClick(R.id.iv_order_back)
    public void onClick(View view) {
        this.finish();
    }

    @Override
    public void getDataSuccess(Object model, int action) {
        //获取成功
        if (action == 1) {
            Recommend recommend = GsonUtil.gsonIntance().gsonToBean(model.toString(), Recommend.class);
            list.addAll(recommend.getUserRecommendList());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDataFail(String msg, int action) {
        //获取失败
    }
}
