package com.chehubang.duolejie.modules.order.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseFragment;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.model.BannerBean;
import com.chehubang.duolejie.model.OrderBean;
import com.chehubang.duolejie.modules.order.adapter.OrderAdapter;
import com.chehubang.duolejie.modules.order.presenter.OrderPresenter;

import java.util.ArrayList;
import java.util.List;

import common.Utils.JSONUtils;
import common.mvp.activity.MainView;
import common.picture.PictureUtils;

/**
 * Created by ZZH on 2018/2/5
 *
 * @date: 2018/2/5 下午3:20
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @Description: 未发货
 */
public class HangOrderFragment extends BaseFragment<OrderPresenter> implements MainView, View.OnClickListener {
    private ListView order_list;
    private ArrayList<OrderBean> mlist = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private ImageView banner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order1, container, false);
        banner = (ImageView) view.findViewById(R.id.iv_banner);
        order_list = (ListView) view.findViewById(R.id.lv_order_list);
        mvpPresenter.getOderList(ACTION_DEFAULT + 1, UserInfo.getInstance().getId(), "待发货", "1", "50");
        orderAdapter = new OrderAdapter(getActivity(), mlist);
        order_list.setAdapter(orderAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter.getBanner(ACTION_DEFAULT + 2, "8");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getDataSuccess(Object model, int action) {

        if (model != null) {
            if (action == ACTION_DEFAULT + 1) {
                mlist.clear();
                List<OrderBean> data = (List<OrderBean>) model;
                mlist.addAll(data);
                orderAdapter.notifyDataSetChanged();
            } else if (action == ACTION_DEFAULT + 2) {
                if (!TextUtils.isEmpty(model.toString())) {
                    Wrapper wrapper = JSONUtils.GsonToBean(model.toString(), Wrapper.class);
                    if (wrapper != null && wrapper.getAdvertisementList() != null && !wrapper.getAdvertisementList().isEmpty()) {
                        PictureUtils.loadPicture(getActivity(), wrapper.getAdvertisementList().get(0).getHeader(), banner, R.drawable.default_avatar);
                    } else {
                        banner.setVisibility(View.GONE);
                    }
                } else {
                    banner.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void getDataFail(String msg, int action) {

    }

    class Wrapper {
        private List<BannerBean> advertisementList;

        public List<BannerBean> getAdvertisementList() {
            return advertisementList;
        }

        public void setAdvertisementList(List<BannerBean> advertisementList) {
            this.advertisementList = advertisementList;
        }
    }

    @Override
    protected OrderPresenter createPresenter() {
        return new OrderPresenter(this);
    }
}
