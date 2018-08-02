package com.chehubang.duolejie.modules.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseFragment;
import com.chehubang.duolejie.config.Constant;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.listener.OnButtonClick;
import com.chehubang.duolejie.model.UserDataInfoBean;
import com.chehubang.duolejie.modules.chargecenter.activity.RechargeActivity;
import com.chehubang.duolejie.modules.chargecenter.activity.WithdrawalsActivity;
import com.chehubang.duolejie.modules.gift.activity.DrawResultActivity;
import com.chehubang.duolejie.modules.home.activity.MainActivity;
import com.chehubang.duolejie.modules.home.activity.WebviewActivity;
import com.chehubang.duolejie.modules.home.presenter.MyPresenter;
import com.chehubang.duolejie.modules.installmentorder.activity.InstallmentOrderActivity;
import com.chehubang.duolejie.modules.login.activity.LoginActivity;
import com.chehubang.duolejie.modules.mysetting.activity.MySettingActivity;
import com.chehubang.duolejie.modules.order.activity.AfterSalesActivity;
import com.chehubang.duolejie.modules.order.activity.MoneyDetailedActivity;
import com.chehubang.duolejie.modules.order.activity.OrderActivity;
import com.chehubang.duolejie.modules.setting.activitiy.NoticeActivity;
import com.chehubang.duolejie.modules.setting.activitiy.SettingActivity;
import com.chehubang.duolejie.modules.store.activity.MyRecommedActivity;
import com.chehubang.duolejie.modules.store.activity.StoreActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import common.Utils.JSONUtils;
import common.Utils.ToastUtils;
import common.mvp.activity.MainView;
import common.picture.PictureUtils;

/**
 * @Date: 2018/1/29 21:07
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 个人中心
 */
public class MyFragment extends BaseFragment<MyPresenter> implements MainView, View.OnClickListener, OnButtonClick {

    TextView tv_preson_data, myNotice, orderHang, orderDelivery, orderReturn, tv_preson_balance, tv_preson_conpon;
    private ImageView header;
    private TextView tv_id,tv_yqm;
    private boolean canPress = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        LinearLayout myorder = (LinearLayout) view.findViewById(R.id.my_order);
        tv_id = (TextView) view.findViewById(R.id.tv_id);
        tv_yqm = (TextView) view.findViewById(R.id.tv_yqm);

        TextView setting = (TextView) view.findViewById(R.id.tv_my_setting);
        header = (ImageView) view.findViewById(R.id.civ_person_header);
        tv_preson_data = (TextView) view.findViewById(R.id.tv_preson_data);
        tv_preson_balance = (TextView) view.findViewById(R.id.tv_person_balance);
        tv_preson_conpon = (TextView) view.findViewById(R.id.tv_person_conpon);
        myNotice = (TextView) view.findViewById(R.id.tv_my_notice);
        orderHang = (TextView) view.findViewById(R.id.tv_my_order_hang);
        orderDelivery = (TextView) view.findViewById(R.id.tv_my_order_delivery);
        orderReturn = (TextView) view.findViewById(R.id.tv_my_order_return);
        TextView installorder = (TextView) view.findViewById(R.id.tv_my_installorder);
        TextView about = (TextView) view.findViewById(R.id.tv_my_about_us);
        TextView service = (TextView) view.findViewById(R.id.tv_my_customer_service);
        view.findViewById(R.id.tv_tixian).setOnClickListener(this);
        view.findViewById(R.id.tv_id).setOnClickListener(this);
        view.findViewById(R.id.tv_yqm).setOnClickListener(this);
        view.findViewById(R.id.tv_moneydetailed).setOnClickListener(this);
        service.setOnClickListener(this);
        myorder.setOnClickListener(this);
        setting.setOnClickListener(this);
        myNotice.setOnClickListener(this);
        tv_preson_data.setOnClickListener(this);
        orderHang.setOnClickListener(this);
        orderDelivery.setOnClickListener(this);
        orderReturn.setOnClickListener(this);
        installorder.setOnClickListener(this);
        view.findViewById(R.id.tv_chongzhi).setOnClickListener(this);
        view.findViewById(R.id.tv_draw).setOnClickListener(this);
        view.findViewById(R.id.tv_store).setOnClickListener(this);
        view.findViewById(R.id.tv_collect).setOnClickListener(this);
        view.findViewById(R.id.tv_more).setOnClickListener(this);
        view.findViewById(R.id.tv_card).setOnClickListener(this);
        tv_id.setOnClickListener(this);
        tv_yqm.setOnClickListener(this);
        about.setOnClickListener(this);
        ((MainActivity) getActivity()).addButtonClickListener(this);

        if (!TextUtils.isEmpty(UserInfo.getInstance().getId())) {
            tv_preson_data.setText(UserInfo.getInstance().getNick_name());
            tv_id.setText("ID:" + UserInfo.getInstance().getId());
            tv_yqm.setText("ID:" + UserInfo.getInstance().getInvite_code());

        } else {
            tv_preson_data.setText("登录/注册");
            header.setImageResource(R.drawable.icon_logo);
            tv_id.setText("");
            tv_yqm.setText("");
        }
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mvpPresenter.getUserData(ACTION_DEFAULT + 1, UserInfo.getInstance().getId());
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (canPress) {
            switch (v.getId()) {
                case R.id.tv_my_setting:
                    intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_preson_data:
                    if (TextUtils.isEmpty(UserInfo.getInstance().getId())) {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), MySettingActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.tv_id:
                    if (TextUtils.isEmpty(UserInfo.getInstance().getId())) {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), MySettingActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.tv_yqm:
                    if (TextUtils.isEmpty(UserInfo.getInstance().getId())) {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), MySettingActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.tv_my_notice:
                    intent = new Intent(getActivity(), NoticeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.my_order:
                    intent = new Intent(getActivity(), OrderActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_my_order_hang:
                    intent = new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("position", 1);
                    startActivity(intent);
                    break;
                case R.id.tv_my_order_delivery:
                    intent = new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("position", 2);
                    startActivity(intent);
                    break;
                case R.id.tv_my_order_return:
                    intent = new Intent(getActivity(), AfterSalesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_my_installorder:
                    intent = new Intent(getActivity(), InstallmentOrderActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_my_customer_service:
                    ToastUtils.showToast(getActivity(), "400-004-1681");
                    break;
                case R.id.tv_my_about_us:
                    intent = new Intent(getActivity(), WebviewActivity.class);
                    intent.putExtra("url", "http://39.107.14.118/appInterface/aboutUsContentInfo.jhtml?id=1");  //外网版本
                    // intent7.putExtra("url","http://192.168.3.25:8090/GetTreasureAppJinDong/appInterface/aboutUsContentInfo.jhtml?id=1&is_show_message=y");
                    intent.putExtra("title", "关于我们");
                    startActivity(intent);
                    break;
                case R.id.tv_chongzhi:
                    intent = new Intent(getActivity(), RechargeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_draw:
                    intent = new Intent(getActivity(), DrawResultActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_store:
//                    intent = new Intent(getActivity(), StoreActivity.class);
//                    startActivity(intent);
                    startActivity(new Intent(getActivity(),MyRecommedActivity.class));
                    break;
                case R.id.tv_collect:
                    break;
                case R.id.tv_more:
                    /*ToastUtils.centerToastWhite(getActivity(), "本服务暂未开放");
                    break;*/
                    break;

                case R.id.tv_tixian:
                    intent = new Intent(getActivity(), WithdrawalsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_card:
                    ToastUtils.centerToastWhite(getActivity(), "本服务暂未开发");
                    break;

                case R.id.tv_moneydetailed:
                    intent=new Intent(getActivity(), MoneyDetailedActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        } else {
            intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void getDataSuccess(Object model, int action) {
        if (model != null) {
            if (action == ACTION_DEFAULT + 1) {
                UserDataInfoBean bean = JSONUtils.GsonToBean((String) model, UserDataInfoBean.class);
                UserInfo.putString("user_lettory", bean.getUser_lettory());
                UserInfo userInfo = UserInfo.getInstance();
                userInfo.setUser_money(bean.getUser_money());
                userInfo.setUser_lettory(bean.getUser_lettory());
                userInfo.setNick_name(bean.getNick_name());
                userInfo.setUser_tel(bean.getUser_tel());
                userInfo.setUser_header(bean.getUser_header());
                userInfo.setSex(bean.getSex());
                userInfo.setInvite_code(bean.getInvite_code());
                UserInfo.getInstance().writeToCache(getActivity());
                PictureUtils.loadRoundImageView(getActivity(), userInfo.getUser_header(), header, R.drawable.pictx);
                tv_preson_data.setText(bean.getNick_name());
                tv_id.setText("ID:" + bean.getId());
                tv_yqm.setText("邀请码:" +bean.getInvite_code());
                tv_preson_balance.setText(bean.getUser_money());
                tv_preson_conpon.setText(bean.getUser_lettory() + "张");
                canPress = true;
            }
        }

    }

    @Override
    public void getDataFail(String msg, int action) {

    }

    //
    @Override
    protected MyPresenter createPresenter() {
        return new MyPresenter(this);
    }

    @Override
    public void OnHomeButtonClick(int position) {
        if (position == 4) {
            tv_preson_data.setText("登录/注册");
            tv_id.setText("");
            tv_yqm.setText("");
            tv_preson_balance.setText("");
            tv_preson_conpon.setText("");
            header.setImageResource(R.drawable.icon_logo);
            canPress = false;
            UserInfo.getInstance().getIdCache(getActivity());
            if (!TextUtils.isEmpty(UserInfo.getInstance().getId())) {
                mvpPresenter.getUserData(ACTION_DEFAULT + 1, UserInfo.getInstance().getId());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventRefresh(String action) {
        if (Constant.EVENT_REFRESH_USER.equals(action) || Constant.EVENT_REFRESH_MONEY.equals(action)) {
            if (!TextUtils.isEmpty(UserInfo.getInstance().getId())) {
                mvpPresenter.getUserData(ACTION_DEFAULT + 1, UserInfo.getInstance().getId());
            } else {
                onRefreshUserImageHeader();
            }
        }
    }

    public void onRefreshUserImageHeader() {
        if (TextUtils.isEmpty(UserInfo.getInstance().getId())) {
            header.setImageResource(R.drawable.icon_logo);
            tv_preson_data.setText("登录/注册");
            tv_id.setText("");
            tv_yqm.setText("");
            tv_preson_balance.setText("");
            tv_preson_conpon.setText("");
        }
    }
}
