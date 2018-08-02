package com.chehubang.duolejie.modules.chargecenter.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.chehubang.duolejie.base.MvpPresenter;
import com.chehubang.duolejie.config.Constant;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.modules.chargecenter.activity.SelectMoneyActivity;
import com.chehubang.duolejie.modules.chargecenter.activity.WithdrawalsActivity;
import com.chehubang.duolejie.modules.login.activity.LoginActivity;
import com.chehubang.duolejie.utils.RsaTool;
import com.chehubang.duolejie.utils.log;

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
public class SelectMoneyPresenter extends MvpPresenter<SelectMoneyActivity> {
    public SelectMoneyPresenter(SelectMoneyActivity activity) {
        attachView(activity);
    }


    public void getUserMoneyData(int action, String id) {
        Map<String, String> map = new HashMap<>();
        String time = System.currentTimeMillis() + "";
        map.put("user_id", id);
        map.put("token", UserInfo.getToken());
        try {
            String s = id + "|$|"  + UserInfo.getToken() + "|$|" + time;
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
        Observable<ResponseBody> userData = service.getUserMoneyData(map);
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
                log.d(result.toString());
                String data = result.getData();
                String status = result.getStatus();

                if (TextUtils.equals(Constant.request_success, status)) {
                    if (action == ACTION_DEFAULT + 1) {
                        mvpView.getDataSuccess(data, action);
                    } else if (action == ACTION_DEFAULT + 2) {
                        mvpView.getDataSuccess(data, action);
                    } else if (action == ACTION_DEFAULT + 3) {
                        mvpView.getDataSuccess(data, action);
                    }
                }
            }
            @Override
            public void jumpLogin() {
                Intent intent = new Intent(mvpView, LoginActivity.class);
                mvpView.startActivity(intent);
            }
        });
    }


}
