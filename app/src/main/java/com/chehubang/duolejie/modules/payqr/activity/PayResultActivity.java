package com.chehubang.duolejie.modules.payqr.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.modules.gift.activity.GlideCircleTransform;
import com.chehubang.duolejie.modules.home.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 首页-扫码付款-付款-付款成功
 * Created by jingang on 2018/7/17 0017.
 */

public class PayResultActivity extends BaseActivity<MainPresenter> {
    @BindView(R.id.ivPayResultHeader)
    ImageView iv;
    @BindView(R.id.tv_pay_result)
    TextView tv;
    @BindView(R.id.but_pay_result)
    Button but;
    @BindView(R.id.tv_pay_result_money)
    TextView tvPayResultMoney;
    private String header = "";

    private double d;
    java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        ButterKnife.bind(this);

        initDate();
    }

    public void initDate() {
        d = Double.parseDouble(getIntent().getStringExtra("pay_money"));
        tvPayResultMoney.setText("¥"+myformat.format(d));

        tv.setText("收款人：" + getIntent().getStringExtra("ussename"));
        header = getIntent().getStringExtra("header");

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(this));

        Glide.with(this)
                .load(header)
                .apply(options)
                .into(iv);
    }

    @Override
    protected MainPresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.but_pay_result)
    public void onClick(View view) {
        PayResultActivity.this.finish();
    }
}
