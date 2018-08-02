package com.chehubang.duolejie.modules.chargecenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.model.UserDataInfoBean;
import com.chehubang.duolejie.modules.chargecenter.presenter.ApplyRefereePresenter;
import com.chehubang.duolejie.modules.chargecenter.presenter.GiftPresenter;
import com.chehubang.duolejie.modules.home.fragment.GiftFragment;

import org.json.JSONException;
import org.json.JSONObject;

import common.Utils.JSONUtils;
import common.Utils.ToastUtils;
import common.mvp.activity.MainView;


/**
 * Created by Beidouht on 2017/12/20.
 */

public class GiftActivity extends BaseActivity<GiftPresenter> implements MainView, View.OnClickListener {


    private String recommender_money;
    private String roomNumber;

    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        findViewById(R.id.iv_gift_back).setOnClickListener(this);
        findViewById(R.id.iv_gift_right).setOnClickListener(this);
        roomNumber = getIntent().getStringExtra("room_number");
        GiftFragment giftFragment = new GiftFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,giftFragment).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_gift_back:
                finish();
                break;

            case R.id.iv_gift_right:
                ToastUtils.showToast(this,"分享");
                break;


            default:
                break;
        }
    }

    @Override
    public void getDataSuccess(Object model, int action) {
        if (model != null) {
            if (action == ACTION_DEFAULT + 1) {
                UserDataInfoBean bean = JSONUtils.GsonToBean((String) model, UserDataInfoBean.class);
                recommender_money = bean.getRecommender_money();


            }
        }

    }

    @Override
    public void getDataFail(String msg, int action) {

    }

    @Override
    protected GiftPresenter createPresenter() {
        return new GiftPresenter(this);
    }


}
