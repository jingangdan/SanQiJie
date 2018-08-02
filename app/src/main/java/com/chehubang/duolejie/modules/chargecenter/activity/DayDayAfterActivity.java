package com.chehubang.duolejie.modules.chargecenter.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.model.BannerBean;
import com.chehubang.duolejie.modules.chargecenter.presenter.DayDayAfterPresenter;
import com.chehubang.duolejie.modules.home.activity.WebviewActivity;
import com.chehubang.duolejie.utils.DialogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import common.Utils.JSONUtils;
import common.Utils.ToastUtils;
import common.mvp.activity.MainView;
import common.picture.PictureUtils;


/**
 * Created by Beidouht on 2017/12/20.
 */

public class DayDayAfterActivity extends BaseActivity<DayDayAfterPresenter> implements MainView, View.OnClickListener, CheckBox.OnCheckedChangeListener {

    private TextView totalPay, balance, needPay, commit;
    private static final int SDK_PAY_FLAG = 1;
    private String id;
    private CheckBox useBalance;
    private CheckBox useZfb;
    private boolean isZfb = false;
    private boolean isBalance = false;
    private String all_perice;
    private String need_pay_money;
    private String user_money;
    private TextView insert_room_number;
    private ImageView iv_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daydayafter);
        ImageView back = (ImageView) findViewById(R.id.iv_dadaafter_back);
        View tv_applireferee = (TextView) findViewById(R.id.tv_applireferee);
        findViewById(R.id.iv_dadaafter_right).setOnClickListener(this);
        ImageView tv_hysttj = (ImageView) findViewById(R.id.tv_hysttj);
        insert_room_number = (TextView) findViewById(R.id.insert_room_number);
        iv_banner = (ImageView) findViewById(R.id.iv_banner);
        iv_banner.setOnClickListener(this);
        findViewById(R.id.tv_search).setOnClickListener(this);

        View about_us = findViewById(R.id.about_us);
        about_us.setOnClickListener(this);
        back.setOnClickListener(this);
        tv_applireferee.setOnClickListener(this);
        mvpPresenter.getCarLifeBanner(ACTION_DEFAULT+1, "12");
        Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/hysttj.ttf");
//        tv_hysttj.setTypeface(tf);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 33) {
            if (resultCode == 10) {
                finish();
            } else if (resultCode == 20) {
                Dialog openinghints = DialogUtils.Openinghints(this, "成功", "15%消费鼓励金已到账");
                openinghints.show();
            }
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_dadaafter_back:
                finish();
                break;
            case R.id.iv_dadaafter_right:
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("url", "http://39.107.14.118/appInterface/aboutUsContentInfo.jhtml?id=18");  //外网版本
                // intent7.putExtra("url","http://192.168.3.25:8090/GetTreasureAppJinDong/appInterface/aboutUsContentInfo.jhtml?id=1&is_show_message=y");
                intent.putExtra("title", "免单攻略");
                startActivity(intent);
                break;
            case R.id.tv_applireferee:
                //申请开通推荐人
                intent = new Intent(this, ApplyRefereeActivity.class);
                startActivityForResult(intent, 33);
                break;
            case R.id.about_us:
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("url", "http://39.107.14.118/appInterface/aboutUsContentInfo.jhtml?id=10");  //外网版本
                // intent7.putExtra("url","http://192.168.3.25:8090/GetTreasureAppJinDong/appInterface/aboutUsContentInfo.jhtml?id=1&is_show_message=y");
                intent.putExtra("title", "免单规则");
                startActivity(intent);
                break;

            case R.id.tv_search:
                String room_number = insert_room_number.getText().toString();
                if(TextUtils.isEmpty(room_number)){
                    ToastUtils.showToast(this,"请输入房间号！");
                    return;
                }
                intent = new Intent(this, GiftActivity.class);
                intent.putExtra("room_number", room_number);
                startActivity(intent);
                break;


            case R.id.iv_banner:
                intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("url", "http://39.107.14.118/appInterface/aboutUsContentInfo.jhtml?id=19");
                intent.putExtra("title", "PLUS会员");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void getDataSuccess(Object model, int action) {
        if (model != null) {
            if (action == ACTION_DEFAULT + 1) {
               try {
                   JSONObject jsonObject = new JSONObject(model.toString());
                   JSONArray advertisementList = jsonObject.getJSONArray("advertisementList");
                   ArrayList<BannerBean> banners = JSONUtils.GsonjsonToArrayList(jsonObject.getString("advertisementList"), BannerBean.class);
                   BannerBean bannerBean = banners.get(0);
                   String imgurl = bannerBean.getHeader();
                   PictureUtils.loadPicture(this, imgurl,iv_banner, R.drawable.iv_scan);

               }catch (Exception e){

               }
            }
        }
    }

    @Override
    public void getDataFail(String msg, int action) {

    }

    @Override
    protected DayDayAfterPresenter createPresenter() {
        return new DayDayAfterPresenter(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_cashier_use_balance:
                isBalance = b;
                break;
            case R.id.cb_cashier_use_zfb:
                isZfb = b;
                break;
        }
    }
}
