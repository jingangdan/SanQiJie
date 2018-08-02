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
 * Created by jingang on 2018/7/18.
 */

public class NMChildPresenter extends MvpPresenter<NMChildFragment> {
    public NMChildPresenter(NMChildFragment fragment) {
        attachView(fragment);
    }

    /**
     * 获取热门搜索词
     *
     * @param action
     * @param storeId
     */
    public void getHotSearchDataBrand(int action, String storeId) {
        Map<String, String> map = new HashMap<>();
        String time = System.currentTimeMillis() + "";
        map.put("brand_type_id", storeId);
        try {
            String s = storeId + "|$|" + time;
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
        Observable<ResponseBody> brandMsg = service.getHotSearchDataBrand(map);
        loadData(action, brandMsg);
    }

    /**
     * 获取商家信息
     *
     * @param action
     * @param data
     */
    public void getLineBrandList(int action, String... data) {
        Map<String, String> map = new HashMap<>();
        map.put("brand_type_id", data[0]);
        map.put("hot_search", data[1]);
        map.put("user_address", data[2]);
        map.put("pageNum", data[3]);
        map.put("limitNum", data[4]);
        String time = System.currentTimeMillis() + "";
        try {
            String s = data[0] + "|$|" + data[1] + "|$|" + data[2] + "|$|" + data[3] + "|$|" + data[4] + "|$|" + time;
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
        Observable<ResponseBody> verificationCode = service.getLineBrandList(map);
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
