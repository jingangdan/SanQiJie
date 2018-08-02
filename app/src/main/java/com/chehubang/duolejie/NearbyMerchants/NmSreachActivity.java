package com.chehubang.duolejie.NearbyMerchants;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.Utils.ToastUtils;
import common.mvp.activity.MainView;

/**
 * 附近商户-搜索结果
 * Created by jingang on 2018/7/23.
 */

public class NmSreachActivity extends BaseActivity<NmSreachPresenter> implements MainView {
    @BindView(R.id.iv_nm_sreach_back)
    ImageView ivNmSreachBack;
    @BindView(R.id.et_nm_sreach)
    EditText etNmSreach;
    @BindView(R.id.but_nm_sreach)
    Button butNmSreach;
    @BindView(R.id.rv_nm_sreach)
    RecyclerView rvNmSreach;

    private String address;

    private NmBrandAdapter brandAdapter;
    private List<LineBrand.LineBrandListBean> brandList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nm_sreach);
        ButterKnife.bind(this);
        address = getIntent().getStringExtra("address");

        setAdapter();
    }

    public void setAdapter() {
        brandAdapter = new NmBrandAdapter(brandList, this);
        rvNmSreach.setLayoutManager(new LinearLayoutManager(this));
        rvNmSreach.setAdapter(brandAdapter);
    }

    @Override
    public void getDataSuccess(Object model, int action) {
        if (action == 1) {
            Log.e("ssssss", "datas = " + model.toString());
            LineBrand lineBrand = GsonUtil.gsonIntance().gsonToBean(model.toString(), LineBrand.class);
            brandList.clear();
            brandList.addAll(lineBrand.getLineBrandList());
            brandAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDataFail(String msg, int action) {

    }

    @Override
    protected NmSreachPresenter createPresenter() {
        return new NmSreachPresenter(this);
    }

    @OnClick({R.id.iv_nm_sreach_back, R.id.but_nm_sreach})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_nm_sreach_back:
                this.finish();
                break;
            case R.id.but_nm_sreach:
                if (!TextUtils.isEmpty(etNmSreach.getText().toString())) {
                    mvpPresenter.getBrandTypeListBySerarch(1, etNmSreach.getText().toString().trim(), address);
                } else {
                    ToastUtils.showToast(this, "请输入搜索内容");
                }
                break;
        }
    }
}
