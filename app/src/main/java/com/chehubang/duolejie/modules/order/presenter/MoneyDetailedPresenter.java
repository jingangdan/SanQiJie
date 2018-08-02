package com.chehubang.duolejie.modules.order.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.chehubang.duolejie.base.MvpPresenter;
import com.chehubang.duolejie.config.Constant;
import com.chehubang.duolejie.model.OrderBean;
import com.chehubang.duolejie.modules.login.activity.LoginActivity;
import com.chehubang.duolejie.modules.order.activity.AfterSalesActivity;
import com.chehubang.duolejie.modules.order.activity.MoneyDetailedActivity;
import com.chehubang.duolejie.utils.RsaTool;
import com.chehubang.duolejie.utils.log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import common.Utils.JSONUtils;
import common.http.LoadDataSubscriber;
import common.http.RequestResult;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by Thinkpad on 2017/12/26.
 */

public class MoneyDetailedPresenter extends MvpPresenter<MoneyDetailedActivity> {

    public MoneyDetailedPresenter(MoneyDetailedActivity activity) {
        attachView(activity);
    }

    public void getMoreDetail(int action, String user_id,String type,String pageNum,String limitNum) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("type", type);
        map.put("pageNum", pageNum);
        map.put("limitNum", limitNum);
        String time = System.currentTimeMillis() + "";
        try {
            String s = user_id + "|$|" + type + "|$|" +pageNum + "|$|" +limitNum + "|$|" + time;
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
        Observable<ResponseBody> verificationCode = service.getMoreDetailList(map);
        loadData(action, verificationCode);
    }




    public void loadData(final int action, Observable<ResponseBody> observable) {
        addSubscription(observable, new LoadDataSubscriber() {

            @Override
            protected void _error(String message) {
                log.d(message);
                log.e("Tag " + message);


                mvpView.getDataFail(message, action);
            }

            @Override
            protected void _success(Object o) {
                RequestResult result = (RequestResult) o;
                log.d("_success: " + result);
//                log.e("Tag " + result);
                String data = result.getData();
                String status = result.getStatus();
                if (TextUtils.equals(status, Constant.request_success)) {
                    mvpView.getDataSuccess(data, action);
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
