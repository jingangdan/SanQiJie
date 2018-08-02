package com.chehubang.duolejie.modules.home.activity;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.base.BaseFragment;
import com.chehubang.duolejie.model.RadioListBean;
import com.chehubang.duolejie.model.ViewPagerPicBean;
import com.chehubang.duolejie.modules.home.adapter.HomeBalanceAdapter;
import com.chehubang.duolejie.modules.home.presenter.HomeBalancePresenter;
import com.chehubang.duolejie.modules.home.presenter.HomePresenter;
import com.chehubang.duolejie.modules.order.presenter.MoneyDetailedPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import common.Utils.JSONUtils;
import common.mvp.activity.MainView;

public class HomeBalanceActivity extends BaseActivity<HomeBalancePresenter> implements MainView {
    private ListView mListView;
    private List<RadioListBean> radioListBeans = new ArrayList<>();
    private HomeBalanceAdapter adapter;
    private ImageView mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_balance2);
        mListView = (ListView) findViewById(R.id.homebalance_list);
        mBack = (ImageView) findViewById(R.id.iv_balance_back);

        String marqueel = getIntent().getStringExtra("marqueelist");
        Log.e("tag", "yq-->" + marqueel);
        mvpPresenter.getViewPagerData(ACTION_DEFAULT + 1);
        if (radioListBeans != null) {
            adapter = new HomeBalanceAdapter(radioListBeans, getApplicationContext());
            mListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected HomeBalancePresenter createPresenter() {
        return new HomeBalancePresenter(this);
    }

    @Override
    public void getDataSuccess(Object model, int action) {
        if (action == ACTION_DEFAULT + 1) {
            if (model != null) {
                radioListBeans.clear();
                try {
                    JSONObject jsonObject = new JSONObject((String) model);
                    JSONArray goodsListBrand = jsonObject.getJSONArray("radiolist");
                    Log.e("Tag", "yq--->" + goodsListBrand.toString());
                    for (int i = 0; i < goodsListBrand.length(); i++) {
                        JSONObject o1 = (JSONObject) goodsListBrand.get(i);
                        RadioListBean goodsListBean = JSONUtils.GsonToBean(o1.toString(), RadioListBean.class);
                        radioListBeans.add(goodsListBean);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void getDataFail(String msg, int action) {

    }
}