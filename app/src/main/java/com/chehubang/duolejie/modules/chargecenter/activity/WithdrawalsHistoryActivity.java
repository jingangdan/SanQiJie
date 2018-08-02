package com.chehubang.duolejie.modules.chargecenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.model.MoneyHistoryBean;
import com.chehubang.duolejie.model.userMoneyBean;
import com.chehubang.duolejie.modules.chargecenter.adapter.SelectMoneyAdapter;
import com.chehubang.duolejie.modules.chargecenter.adapter.WithdrawalsHistoryAdapter;
import com.chehubang.duolejie.modules.chargecenter.presenter.SelectMoneyPresenter;
import com.chehubang.duolejie.modules.chargecenter.presenter.WithdrawalsHistoryPresenter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import common.Utils.JSONUtils;
import common.Utils.ToastUtils;
import common.mvp.activity.MainView;

/**
 * Created by ZZH on 2018/1/30.
 *
 * @Date: 2018/1/30
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 充值
 */
public class WithdrawalsHistoryActivity extends BaseActivity<WithdrawalsHistoryPresenter> implements MainView,View.OnClickListener,WithdrawalsHistoryAdapter.OnItemBack{

    private RecyclerView contentlist;
    private List<MoneyHistoryBean> mlist = new ArrayList<>();
    private WithdrawalsHistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawalshistory);
        findAtyViewById(R.id.iv_history_back).setOnClickListener(this);
        contentlist = findAtyViewById(R.id.lv_selectmoney_content);
        contentlist.setLayoutManager(new LinearLayoutManager(this));
        contentlist.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAdapter = new WithdrawalsHistoryAdapter(this, mlist);
        contentlist.setAdapter(mAdapter);
        mvpPresenter.getAlreadyExchangeData(ACTION_DEFAULT+1, UserInfo.getInstance().getId());
        mAdapter.setOnItemBack(this);
    }

    @Override
    protected WithdrawalsHistoryPresenter createPresenter() {
        return new WithdrawalsHistoryPresenter(this);
    }



    @Override
    public void getDataSuccess(Object model, int action) {
        if (model != null) {
            if (action == ACTION_DEFAULT + 1) {
                try {
                    String userMoneyExchangeList = new JSONObject(model.toString()).getString("userMoneyExchangeList");
                    ArrayList<MoneyHistoryBean> userMoneyBeans = JSONUtils.GsonjsonToArrayList(userMoneyExchangeList, MoneyHistoryBean.class);
                    mlist.addAll(userMoneyBeans);
                    mAdapter.notifyDataSetChanged();
                }catch (Exception e){
                }
                Log.d("",model.toString());
            }else if(action == ACTION_DEFAULT + 2){

            }
        }

    }


    @Override
    public void getDataFail(String msg, int action) {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.iv_history_back:
                finish();
                break;
        }

    }

    @Override
    public void checkitemBack() {

    }
}
