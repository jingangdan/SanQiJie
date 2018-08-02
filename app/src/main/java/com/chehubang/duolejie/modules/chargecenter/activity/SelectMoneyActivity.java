package com.chehubang.duolejie.modules.chargecenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.model.UserDataInfoBean;
import com.chehubang.duolejie.model.ViewPagerPicBean;
import com.chehubang.duolejie.model.userMoneyBean;
import com.chehubang.duolejie.modules.chargecenter.adapter.SelectMoneyAdapter;
import com.chehubang.duolejie.modules.chargecenter.presenter.SelectMoneyPresenter;
import com.chehubang.duolejie.modules.chargecenter.presenter.WithdrawalsPresenter;
import com.chehubang.duolejie.modules.home.adapter.HomeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import common.Log.LogUtils;
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
public class SelectMoneyActivity extends BaseActivity<SelectMoneyPresenter> implements MainView,View.OnClickListener,SelectMoneyAdapter.OnItemBack{
    private RecyclerView contentlist;
    private SelectMoneyAdapter mAdapter;
    private List<userMoneyBean> mlist = new ArrayList<>();
    private View iv_confirm_withdrawals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmoney);
        findAtyViewById(R.id.iv_selectmoney_back).setOnClickListener(this);

        iv_confirm_withdrawals = findAtyViewById(R.id.iv_confirm_withdrawals);
        iv_confirm_withdrawals.setOnClickListener(this);
        contentlist = findAtyViewById(R.id.lv_selectmoney_content);
        contentlist.setLayoutManager(new LinearLayoutManager(this));
        contentlist.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAdapter = new SelectMoneyAdapter(this,mlist);
        contentlist.setAdapter(mAdapter);
        mvpPresenter.getUserMoneyData(ACTION_DEFAULT+1, UserInfo.getInstance().getId());
        mAdapter.setOnItemBack(this);
    }

    @Override
    protected SelectMoneyPresenter createPresenter() {
        return new SelectMoneyPresenter(this);
    }

    @Override
    public void getDataSuccess(Object model, int action) {
        if (model != null) {
            if (action == ACTION_DEFAULT + 1) {
                try {
                    String userMoneyList = new JSONObject(model.toString()).getString("userMoneyList");
                    ArrayList<userMoneyBean> userMoneyBeans = JSONUtils.GsonjsonToArrayList(userMoneyList, userMoneyBean.class);
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
            case R.id.iv_selectmoney_back:
                finish();
                break;
            case R.id.iv_confirm_withdrawals:
                double moneyCount=0;
                String ids="";
                for(userMoneyBean o:mlist){
                    if(o.isSelectd()){
                        moneyCount=moneyCount+o.getMoney();
                        if(!TextUtils.isEmpty(ids)){
                            ids=ids+","+o.getId();
                        }else{
                            ids=ids+o.getId();
                        }
                    }

                }
                intent=new Intent();
                intent.putExtra("money",moneyCount+"");
                intent.putExtra("user_money_ids",ids);
                setResult(Activity.RESULT_OK,intent);
                finish();
                break;
        }

    }

    @Override
    public void checkitemBack() {
        boolean flat=false;
        for(userMoneyBean o:mlist){
            if(o.getSelectd()){
                flat=true;

            }
        }
        if(flat){
            iv_confirm_withdrawals.setClickable(true);
        }else{
            iv_confirm_withdrawals.setClickable(false);
        }

    }
}
