package com.chehubang.duolejie.NearbyMerchants;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.utils.GsonUtil;
import com.chehubang.duolejie.widget.RecyclerViewBanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.mvp.activity.MainView;

/**
 * 附近商戶-商戶想詳情
 * Created by jingang on 2018/7/25 0025.
 */

public class NmLineBrandDetailsActivity extends BaseActivity<NmLineBrandDetailsPresenter> implements MainView {
    @BindView(R.id.vp_nm_details)
    RecyclerViewBanner vpNmDetails;
    @BindView(R.id.tv_nm_d_name)
    TextView tvNmDName;
    @BindView(R.id.tv_nm_d_introduction)
    TextView tvNmDIntroduction;
    @BindView(R.id.tv_nm_d_address)
    TextView tvNmDAddress;
    @BindView(R.id.tv_nm_d_tel)
    TextView tvNmDTel;
    @BindView(R.id.iv_nm_d_back)
    ImageView ivNmDBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nm_linebrand_details);
        ButterKnife.bind(this);
        mvpPresenter.getLineBrandByIdList(1, getIntent().getStringExtra("line_brand_id"));
    }

    public void setLunbo(){

    }

    @Override
    protected NmLineBrandDetailsPresenter createPresenter() {
        return new NmLineBrandDetailsPresenter(this);
    }

    @Override
    public void getDataSuccess(Object model, int action) {
        if (action == 1) {
            LBDetails lbDetails = GsonUtil.gsonIntance().gsonToBean(model.toString(), LBDetails.class);
            tvNmDName.setText(lbDetails.getLinebrandList().get(0).getBrand_name());
            tvNmDIntroduction.setText(lbDetails.getLinebrandList().get(0).getBrand_introduction());
            tvNmDAddress.setText(lbDetails.getLinebrandList().get(0).getBrand_address());
            tvNmDTel.setText("联系方式：" + lbDetails.getLinebrandList().get(0).getBrand_tel());
        }
    }

    @Override
    public void getDataFail(String msg, int action) {

    }

    @OnClick(R.id.iv_nm_d_back)
    public void onClick(View view) {
        this.finish();
    }
}
