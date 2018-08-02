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
 * Created by Administrator on 2018/7/25 0025.
 */

public class NmLineBrandDetailsPresenter extends MvpPresenter<NmLineBrandDetailsActivity> {
    public NmLineBrandDetailsPresenter(NmLineBrandDetailsActivity fragment) {
        attachView(fragment);
    }

    /**
     * 获取商家详情
     * @param action
     * @param data
     */
    public void getLineBrandByIdList(int action, String... data) {
        Map<String, String> map = new HashMap<>();
        map.put("line_brand_id", data[0]);
        String time = System.currentTimeMillis() + "";
        try {
            String s = data[0] + "|$|" + time;
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
        Observable<ResponseBody> verificationCode = service.getLineBrandByIdList(map);
        loadData(action, verificationCode);
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
