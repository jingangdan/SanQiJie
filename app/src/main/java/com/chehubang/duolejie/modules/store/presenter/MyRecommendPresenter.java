package com.chehubang.duolejie.modules.store.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.chehubang.duolejie.base.MvpPresenter;
import com.chehubang.duolejie.config.Constant;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.modules.store.activity.MyRecommedActivity;
import com.chehubang.duolejie.utils.RsaTool;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import common.Utils.JSONUtils;
import common.http.LoadDataSubscriber;
import common.http.RequestResult;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by Administrator on 2018/7/17 0017.
 */

public class MyRecommendPresenter extends MvpPresenter<MyRecommedActivity> {
    public MyRecommendPresenter(MyRecommedActivity activity) {
        attachView(activity);
    }

    public void getRecommendList(int action, int page) {
        Map<String, String> map = new HashMap<>(8);
        String time = System.currentTimeMillis() + "";
        String id = UserInfo.getInstance().getId();
        map.put("user_id", id);
        map.put("pageNum", String.valueOf(page));
        map.put("limitNum", "10");
        try {
            String s = id + "|$|" + page + "|$|" + "10" + "|$|" + time;
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
        Observable<ResponseBody> verificationCode = service.getRecommendListData(map);
        loadData(action, verificationCode);
    }

    public void loadData(final int action, Observable<ResponseBody> observable) {
        addSubscription(observable, new LoadDataSubscriber() {

            @Override
            protected void _error(String message) {
                mvpView.getDataFail(message, action);
            }

            @Override
            protected void _success(Object o) throws JSONException {
                RequestResult result = (RequestResult) o;
                String data = result.getData();
                String status = result.getStatus();
                if (action == 1) {
                    if (TextUtils.equals(Constant.request_success, status)) {
                        mvpView.getDataSuccess(data, action);
                    } else {
                        mvpView.getDataFail(data, action);
                    }

                    Log.e("ssssss","data = "+data);
                }
            }
        });
    }


}
