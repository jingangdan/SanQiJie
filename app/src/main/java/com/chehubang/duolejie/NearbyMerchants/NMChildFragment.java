package com.chehubang.duolejie.NearbyMerchants;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseFragment;
import com.chehubang.duolejie.model.HotKey;
import com.chehubang.duolejie.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import common.mvp.activity.MainView;

/**
 * 附近商户-子类
 * Created by jingang on 2018/7/18.
 */
public class NMChildFragment extends BaseFragment<NMChildPresenter> implements MainView {
    Unbinder unbinder;
    @BindView(R.id.rv_nm_hot)
    RecyclerView rvNmHot;
    @BindView(R.id.rv_nm_brand)
    RecyclerView rvNmBrand;
    private String type, brand_type_id, user_address;
    private int tag;

    private NmHotAdapter hotAdapter;
    private List<HotKey.HotSearchListBean> hotList = new ArrayList<>();

    private NmBrandAdapter brandAdapter;
    private List<LineBrand.LineBrandListBean> brandList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_nm, null);
        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            tag = getArguments().getInt("tag");
            if (tag == 0) {
                brand_type_id = getArguments().getString("brand_type_id");
                user_address = getArguments().getString("user_address");
                setAdapter();
                getData();
            }
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getArguments() != null) {
                if (getActivity() != null) {
                    type = getArguments().getString("type");
                    brand_type_id = getArguments().getString("brand_type_id");
                    user_address = getArguments().getString("user_address");
                    if (!TextUtils.isEmpty(brand_type_id)) {
                        setAdapter();
                        getData();
                    }
                }
            }
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (getUserVisibleHint()) {
//            setAdapter();
//            getData();
//        }
//    }

    public void getData() {
        mvpPresenter.getHotSearchDataBrand(ACTION_DEFAULT + 1, brand_type_id);
    }

    public void setAdapter() {
        if (rvNmHot != null) {
            hotAdapter = new NmHotAdapter(getActivity(), hotList);
            rvNmHot.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
            rvNmHot.setAdapter(hotAdapter);
            hotAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    hotAdapter.changeSelected(position);
                    mvpPresenter.getLineBrandList(ACTION_DEFAULT + 2, brand_type_id, hotList.get(position).getSearch_key(), user_address, "1", "100");
                }
            });

            brandAdapter = new NmBrandAdapter(brandList, getActivity());
            rvNmBrand.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvNmBrand.setAdapter(brandAdapter);

            brandAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startActivity(new Intent(getActivity(),NmLineBrandDetailsActivity.class).putExtra("line_brand_id",brandList.get(position).getId()));
                }
            });
        }
    }

    @Override
    public void getDataSuccess(Object model, int action) {
        if (action == ACTION_DEFAULT + 1) {
            HotKey hotKey = GsonUtil.gsonIntance().gsonToBean(model.toString(), HotKey.class);
            hotList.clear();
            hotList.addAll(hotKey.getHotSearchList());
            hotAdapter.notifyDataSetChanged();
            if (hotList.size() > 0) {
                mvpPresenter.getLineBrandList(ACTION_DEFAULT + 2, brand_type_id, hotList.get(0).getSearch_key(), user_address, "1", "100");
            }
        } else if (action == ACTION_DEFAULT + 2) {
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
    protected NMChildPresenter createPresenter() {
        return new NMChildPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
