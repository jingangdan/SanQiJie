package com.chehubang.duolejie.NearbyMerchants;

import android.text.TextUtils;
import android.util.Log;

import com.chehubang.duolejie.base.MvpPresenter;
import com.chehubang.duolejie.config.Constant;
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
 * Created by asus on 2018/7/23.
 */

public class NmSreachPresenter extends MvpPresenter<NmSreachActivity> {
    public NmSreachPresenter(NmSreachActivity fragment) {
        attachView(fragment);
    }

    /**
     * 根据搜索内容获取商家信息
     *
     * @param action
     * @param search
     * @param user_address
     */
    public void getBrandTypeListBySerarch(int action, String search, String user_address) {
        Map<String, String> map = new HashMap<>();
        String time = System.currentTimeMillis() + "";
        map.put("search", search);
        map.put("user_address", user_address);
        try {
            String s = search + "|$|" + user_address + "|$|" + time;
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
        Observable<ResponseBody> brandMsg = service.getBrandTypeListBySerarch(map);
        loadData(action, brandMsg);
    }


    public void loadData(final int action, Observable<ResponseBody> observable) {
        addSubscription(observable, new LoadDataSubscriber() {
            @Override
            protected void _error(String message) {
                Log.e("ssssss", "error: " + message);
                mvpView.getDataFail(message, action);
            }

            @Override
            protected void _success(Object o) throws JSONException {
                RequestResult result = (RequestResult) o;
                Log.e("ssssss", "data = " + result.getData());
                String data = result.getData();
                String status = result.getStatus();
                if (TextUtils.equals(Constant.request_success, status)) {
                    if (action == 1) {
                        mvpView.getDataSuccess(data, action);
                    }
                }
            }
        });
    }

}
