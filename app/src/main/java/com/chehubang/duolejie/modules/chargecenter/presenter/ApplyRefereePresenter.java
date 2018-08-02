package com.chehubang.duolejie.modules.chargecenter.presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.chehubang.duolejie.base.MvpPresenter;
import com.chehubang.duolejie.config.Constant;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.model.PayResult;
import com.chehubang.duolejie.modules.chargecenter.activity.ApplyRefereeActivity;
import com.chehubang.duolejie.modules.chargecenter.activity.DayDayAfterActivity;
import com.chehubang.duolejie.modules.login.activity.LoginActivity;
import com.chehubang.duolejie.utils.RsaTool;
import com.chehubang.duolejie.utils.log;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import common.Utils.JSONUtils;
import common.Utils.ToastUtils;
import common.http.LoadDataSubscriber;
import common.http.RequestResult;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by Beidouht on 2017/12/20.
 */

public class ApplyRefereePresenter extends MvpPresenter<ApplyRefereeActivity> {
    public ApplyRefereePresenter(ApplyRefereeActivity activity) {
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

    /**
     * 获取图片
     * @param action
     * @param type
     */
    public void addBanner(int action, String type) {
        Map<String, String> map = new HashMap<>();
        String time = System.currentTimeMillis() + "";
        map.put("type", type);
        try {
            String s = type + "|$|" + time;
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
        Observable<ResponseBody> responseBodyObservable = service.getCarLifeBanner(map);
        loadData(action, responseBodyObservable);
    }


    /**
     * 获取金额
     * @param action
     */
//    public void getPulsMoneyList(int action) {
//        Map<String, String> map = new HashMap<>();
//        String params = JSONUtils.mapToJson(map);
//        map.clear();
//        map.put("param", params);
//        Observable<ResponseBody> userData = service.recommender(map);
//        loadData(action, userData);
//    }



    public void getPlusMoneyList(int action) {
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
        Observable<ResponseBody> verificationCode = service.getPlusMoneyList(map);
        loadData(action, verificationCode);
    }

    /**
     * 获取订单info
     * @param action
     * @param id
     * @param money
     */
    public void getRecommender(int action, String id,String money) {
        Map<String, String> map = new HashMap<>();
        String time = System.currentTimeMillis() + "";
        map.put("user_id", id);
        map.put("money", money);
        map.put("token", UserInfo.getToken());
        try {
            String s = id + "|$|" +money + "|$|" + UserInfo.getToken() + "|$|" + time;
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
        Observable<ResponseBody> userData = service.recommender(map);
        loadData(action, userData);
    }


    /**
     * 支付
     * @param action
     * @param money
     * @param outTradeNo
     */
    public void getappClicet(int action, String money,String outTradeNo) {
        Map<String, String> map = new HashMap<>();
        String time = System.currentTimeMillis() + "";
        map.put("totalAmount", money);
        map.put("outTradeNo", outTradeNo);
        map.put("token", UserInfo.getToken());
        try {
            String s = money + "|$|" +outTradeNo + "|$|" + UserInfo.getToken() + "|$|" + time;
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
        Observable<ResponseBody> userData = service.getappClicet(map);
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
                Log.e("ssssss","data = "+result.toString());
                String data = result.getData();
                String status = result.getStatus();

                if (TextUtils.equals(Constant.request_success, status)) {
                    if (action == ACTION_DEFAULT + 1) {
                        mvpView.getDataSuccess(data, action);
                    } else if (action == ACTION_DEFAULT + 2) {
                        mvpView.getDataSuccess(data, action);
                    } else if (action == ACTION_DEFAULT + 3) {
                        mvpView.getDataSuccess(data, action);
                    }else if (action == ACTION_DEFAULT + 4) {
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




    private static final int SDK_PAY_FLAG = 1;
    public void pay(final String orderInfo) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(mvpView);
                // 调用支付接口，获取支付结果
                String result = payTask.pay(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {    //同步支付请求
                    PayResult payResult = new PayResult((String) msg.obj);
//                       PayResultActivity payResult = new PayResultActivity((String) msg.obj);
                    /** 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知*/
                    String resultStatus = payResult.getResultStatus();
                    //     payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //表示支付成功
                        EventBus.getDefault().post(Constant.EVENT_REFRESH_USER);
                        ToastUtils.centerToastWhite(mvpView, "支付成功");
                        mvpView.successPay();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtils.centerToastWhite(mvpView, "支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtils.centerToastWhite(mvpView, "支付失败");
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
}
