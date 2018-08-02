package com.chehubang.duolejie.modules.chargecenter.presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.chehubang.duolejie.base.MvpPresenter;
import com.chehubang.duolejie.config.Constant;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.model.PayResult;
import com.chehubang.duolejie.modules.chargecenter.activity.ApplyRefereeActivity;
import com.chehubang.duolejie.modules.chargecenter.activity.GiftActivity;
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

public class GiftPresenter extends MvpPresenter<GiftActivity> {
    public GiftPresenter(GiftActivity activity) {
        attachView(activity);
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
