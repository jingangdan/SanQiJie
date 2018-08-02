package com.chehubang.duolejie.modules.chargecenter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.config.Constant;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.model.PayResult;
import com.chehubang.duolejie.model.UserDataInfoBean;
import com.chehubang.duolejie.model.userMoneyBean;
import com.chehubang.duolejie.modules.chargecenter.presenter.RechargePresenter;
import com.chehubang.duolejie.modules.chargecenter.presenter.WithdrawalsPresenter;
import com.chehubang.duolejie.utils.CodeUtils;
import com.chehubang.duolejie.utils.NumberUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.Utils.JSONUtils;
import common.Utils.ToastUtils;
import common.http.RequestResult;
import common.mvp.activity.MainView;
import common.picture.PictureUtils;

/**
 * Created by ZZH on 2018/1/30.
 *
 * @Date: 2018/1/30
 * @Email: zzh_hz@126.com
 * @QQ: 1299234582
 * @Author: zzh
 * @Description: 充值
 */
public class WithdrawalsActivity extends BaseActivity<WithdrawalsPresenter> implements MainView,View.OnClickListener{

    final static  int ActivityResultCode=3381;
    private TextView withdrawalsmoney;
    private String money="";
    private String user_money_ids="";
    private EditText et_alipaaccountnumber;
    private EditText et_alipaynikename;
    private TextView tv_user_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawals);
        tv_user_money = findAtyViewById(R.id.tv_user_money);
        et_alipaynikename = findAtyViewById(R.id.et_alipaynikename);
        et_alipaaccountnumber = findAtyViewById(R.id.et_alipaaccountnumber);
        withdrawalsmoney = findAtyViewById(R.id.tv_selectwithdrawals);
        withdrawalsmoney.setOnClickListener(this);
        findAtyViewById(R.id.iv_confirm_withdrawals).setOnClickListener(this);
        findAtyViewById(R.id.iv_withdrawals_right).setOnClickListener(this);
        findAtyViewById(R.id.iv_withdrawals_back).setOnClickListener(this);
    }

    @Override
    protected WithdrawalsPresenter createPresenter() {
        return new WithdrawalsPresenter(this);
    }

    @Override
    public void getDataSuccess(Object model, int action) {

        if (action == ACTION_DEFAULT + 1) {
            try {
                RequestResult result = (RequestResult) model;
                if (TextUtils.equals(Constant.request_success, result.getStatus())) {
                    Toast.makeText(this,result.getDesc(),Toast.LENGTH_SHORT).show();
                    withdrawalsmoney.setText("请选择提现金额");
                    mvpPresenter.getUserData(ACTION_DEFAULT + 2, UserInfo.getInstance().getId()); //账户金额修改，更新一遍书

                }else {
                    Toast.makeText(this,result.getDesc(),Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){

            }

        }else if(action == ACTION_DEFAULT + 2){
            RequestResult result = (RequestResult) model;
            UserDataInfoBean bean = JSONUtils.GsonToBean(result.getData(), UserDataInfoBean.class);
            tv_user_money.setText(bean.getUser_money());
        }
    }


    @Override
    public void getDataFail(String msg, int action) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case ActivityResultCode+1:
                    money = data.getStringExtra("money");
                    user_money_ids = data.getStringExtra("user_money_ids");
                    withdrawalsmoney.setText(money);
                    break;
            }
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tv_selectwithdrawals:
                intent=new Intent(this,SelectMoneyActivity.class);
                startActivityForResult(intent,ActivityResultCode+1);
                break;

            case R.id.iv_confirm_withdrawals:
                final String alipaaccountnumber = et_alipaaccountnumber.getText().toString();
                if(NumberUtils.isNum(money)&&Double.parseDouble(money)<100){
                    ToastUtils.showToast(this,"暂时不支持小于100元的提现");
                }else if(TextUtils.isEmpty(alipaaccountnumber)&&TextUtils.isEmpty(et_alipaynikename.getText().toString())){
                    Toast.makeText(this,"请检查帐号输入是否正确！",Toast.LENGTH_SHORT).show();
                }else{
                    mvpPresenter.getExchangeData(ACTION_DEFAULT+1,UserInfo.getInstance().getId(),money,"2",alipaaccountnumber,user_money_ids);
                }

                break;

            case R.id.iv_withdrawals_right:
                intent=new Intent(this,WithdrawalsHistoryActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_withdrawals_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvpPresenter.getUserData(ACTION_DEFAULT + 2, UserInfo.getInstance().getId());
    }
}
