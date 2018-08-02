package com.chehubang.duolejie.modules.home.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.chehubang.duolejie.base.MvpPresenter;
import com.chehubang.duolejie.config.Constant;
import com.chehubang.duolejie.modules.home.fragment.NearbyMerchanrsFragment;
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
 * Created by jingang on 2018/7/19.
 */

public class NearbyMerchantsPresenter extends MvpPresenter<NearbyMerchanrsFragment> {
    public NearbyMerchantsPresenter(NearbyMerchanrsFragment fragment) {
        attachView(fragment);
    }

    /**
     * 获取商家类型
     *
     * @param action
     */
    public void getBrandTypeList(int action) {
        Map<String, String> map = new HashMap<>();
        String time = System.currentTimeMillis() + "";
        try {
            String s = time;
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
        Observable<ResponseBody> verificationCode = service.getBrandTypeList(map);
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
                    if (action == ACTION_DEFAULT + 1) {
                        mvpView.getDataSuccess(data, action);
                    } else if (action == ACTION_DEFAULT + 2) {
                        mvpView.getDataSuccess(data, action);
                    }
                }
            }
        });
    }


}
