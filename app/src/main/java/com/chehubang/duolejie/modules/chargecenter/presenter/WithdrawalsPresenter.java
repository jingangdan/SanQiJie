package com.chehubang.duolejie.modules.chargecenter.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.chehubang.duolejie.base.MvpPresenter;
import com.chehubang.duolejie.config.Constant;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.modules.chargecenter.activity.RechargeActivity;
import com.chehubang.duolejie.modules.chargecenter.activity.WithdrawalsActivity;
import com.chehubang.duolejie.modules.login.activity.LoginActivity;
import com.chehubang.duolejie.utils.RsaTool;

import java.util.HashMap;
import java.util.Map;

import common.Utils.JSONUtils;
import common.http.LoadDataSubscriber;
import common.http.RequestResult;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by ZZH on 2018/1/30.
 *
 * @Date: 2018/1/30
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description:
 */
public class WithdrawalsPresenter extends MvpPresenter<WithdrawalsActivity> {
    public WithdrawalsPresenter(WithdrawalsActivity activity) {
        attachView(activity);
    }

    public void getUserData(int action, String id) {
        Map<String, String> map = new HashMap<>();
        String time = System.currentTimeMillis() + "";
        map.put("user_id", id);
        map.put("token", UserInfo.getToken());
        try {
            String s = id + "|$|" + UserInfo.getToken() + "|$|" + time;
            String sign = RsaTool.encrypt(Constant.OUR_RSA_PUBLIC, s);
            map.put("sign", sign);
        } catch (Exception e) {
            map.put("sign", "");
            e.printStackTrace();
        }
        map.put("request_time", time);
        String params = JSONUtils.mapToJson(map);
        map.clear();
        map.put("param", params);
        Observable<ResponseBody> userData = service.getUserData(map);
        loadData(action, userData);
    }


    public void getExchangeData(int action, String id,String money,String type,String account,String user_money_ids) {
        Map<String, String> map = new HashMap<>();
        String time = System.currentTimeMillis() + "";
        map.put("user_id", id);
        map.put("money", money);
        map.put("type", type);
        map.put("account", account);
        map.put("user_money_ids", user_money_ids);
        map.put("token", UserInfo.getToken());
        try {
            String s = id + "|$|"  + money + "|$|"+ type + "|$|"+ account + "|$|"+ user_money_ids + "|$|"+ UserInfo.getToken() + "|$|" + time;
            String sign = RsaTool.encrypt(Constant.OUR_RSA_PUBLIC, s);
            map.put("sign", sign);
        } catch (Exception e) {
            map.put("sign", "");
            e.printStackTrace();
        }
        map.put("request_time", time);
        String params = JSONUtils.mapToJson(map);
        map.clear();
        map.put("param", params);
        Observable<ResponseBody> userData = service.getExchangeData(map);
        loadData(action, userData);
    }


    public void loadData(final int action, Observable<ResponseBody> observable) {
        addSubscription(observable, new LoadDataSubscriber() {

            @Override
            protected void _error(String message) {
                mvpView.getDataFail(message, action);

            }

            @Override
            protected void _success(Object o) {
                RequestResult result = (RequestResult) o;
                String data = result.getData();
                String status = result.getStatus();
                mvpView.getDataSuccess(o, action);

            }
            @Override
            public void jumpLogin() {
                Intent intent = new Intent(mvpView, LoginActivity.class);
                mvpView.startActivity(intent);
            }
        });
    }


}
