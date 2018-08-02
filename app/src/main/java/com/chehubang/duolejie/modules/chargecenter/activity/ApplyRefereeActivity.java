//package com.chehubang.duolejie.modules.chargecenter.activity;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.alipay.sdk.app.PayTask;
//import com.chehubang.duolejie.NearbyMerchants.OnItemClickListener;
//import com.chehubang.duolejie.R;
//import com.chehubang.duolejie.base.BaseActivity;
//import com.chehubang.duolejie.config.Constant;
//import com.chehubang.duolejie.config.UserInfo;
//import com.chehubang.duolejie.model.PayResult;
//import com.chehubang.duolejie.model.RadioListBean;
//import com.chehubang.duolejie.model.UserDataInfoBean;
//import com.chehubang.duolejie.model.ViewPagerPicBean;
//import com.chehubang.duolejie.model.plusMoneyListBean;
//import com.chehubang.duolejie.model.advertisementList;
//import com.chehubang.duolejie.modules.chargecenter.presenter.ApplyRefereePresenter;
//import com.chehubang.duolejie.modules.chargecenter.presenter.DayDayAfterPresenter;
//import com.chehubang.duolejie.modules.home.activity.WebviewActivity;
//import com.chehubang.duolejie.modules.payqr.activity.PayQrActivity;
//import com.chehubang.duolejie.utils.BaseRecyclerViewHolder;
//import com.google.gson.Gson;
//
//import org.greenrobot.eventbus.EventBus;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import common.Utils.JSONUtils;
//import common.Utils.ToastUtils;
//import common.mvp.activity.MainView;
//import common.picture.PictureUtils;
//import okhttp3.ResponseBody;
//import rx.Observable;
//
//
///**
// * Created by Beidouht on 2017/12/20.
// */
//
//public class ApplyRefereeActivity extends BaseActivity<ApplyRefereePresenter> implements MainView, View.OnClickListener {
//
//    private String recommender_money;
//    private GridView gridView;
//    private SimpleAdapter adapter;
//    private List<Map<String, Object>> dataList;
//    private ArrayList<plusMoneyListBean> pmlb = new ArrayList<>();
//    private ArrayList<advertisementList> atmlb = new ArrayList<>();
//
//    String name[] = {"500", "1000", "2000", "5000", "10000"};
//    private ARAdapter arAdapter;
//    private RecyclerView rvApplyReferee;
//
//    private String money;
//
//    private TextView tv_pay;
//    private ImageView iv_banner;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_applyreferee);
//        ImageView back = (ImageView) findViewById(R.id.iv_applyreferee_back);
//        View ll_noaccordcondition = (View) findViewById(R.id.ll_noaccordcondition);
//        View ll_accordcondition = (View) findViewById(R.id.ll_accordcondition);
//        back.setOnClickListener(this);
//        ll_noaccordcondition.setOnClickListener(this);
//        ll_accordcondition.setOnClickListener(this);
//
//        tv_pay = (TextView) findViewById(R.id.tv_apply_freree_pay);
//        tv_pay.setOnClickListener(this);
//
//        gridView = (GridView) findViewById(R.id.gridview);
//        rvApplyReferee = (RecyclerView) findViewById(R.id.rvApplyReferee);
//        iv_banner = (ImageView) findViewById(R.id.iv_banner);
//        iv_banner.setOnClickListener(this);
//
//        //初始化数据
//        initData();
////        String[] from={"text"};
////
////        int[] to={R.id.text};
////
////        adapter=new SimpleAdapter(this, dataList, R.layout.gridview_item, from, to);
////
////        gridView.setAdapter(adapter);
////
////        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
////                                    long arg3) {
////
////
////            }
////        });
//
//    }
//
//    void initData() {
//        mvpPresenter.getPulsMoneyList(ACTION_DEFAULT + 4);
//
//        //图标下的文字
//        dataList = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < name.length; i++) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("text", name[i]);
//            dataList.add(map);
//        }
//
//        if(name.length>0){
//            money = name[0];
//        }
//
//        arAdapter = new ARAdapter(name, this);
//        rvApplyReferee.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
//        rvApplyReferee.setAdapter(arAdapter);
//        arAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                arAdapter.changeSelected(position);
//                money = name[position];
//                Log.e("ssssss", "money = " + money);
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_applyreferee_back:
//                finish();
//                break;
//
//            case R.id.ll_noaccordcondition:
//                setResult(10);
//                finish();
//                break;
//
//            case R.id.ll_accordcondition:
//
//                mvpPresenter.getUserData(ACTION_DEFAULT + 1, UserInfo.getInstance().getId());
////                setResult(20);
////                finish();
//                break;
//
//            case R.id.tv_apply_freree_pay:
//                //确认支付
//                if(!TextUtils.isEmpty(money)){
//                    if(!TextUtils.isEmpty(UserInfo.getInstance().getId())){
//                        PayQrActivity.open(this,UserInfo.getInstance().getId(),money);
//
//                    }
//                }else{
//                    ToastUtils.showToast(this,"请选择支付金额");
//                }
//                break;
//            case R.id.iv_banner:
//                //搜索跳转
//                Intent intent = new Intent(getApplicationContext(), WebviewActivity.class);
//                intent.putExtra("url", "http://39.107.14.118/appInterface/aboutUsContentInfo.jhtml?id=19");
//                intent.putExtra("title", "PLUS会员");
//                startActivity(intent);
//                break;
//
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void getDataSuccess(Object model, int action) {
//        if (model != null) {
//            if (action == ACTION_DEFAULT + 1) {
//                UserDataInfoBean bean = JSONUtils.GsonToBean((String) model, UserDataInfoBean.class);
//                recommender_money = bean.getRecommender_money();
//                mvpPresenter.getRecommender(ACTION_DEFAULT + 2, UserInfo.getInstance().getId(), recommender_money);
//            } else if (action == ACTION_DEFAULT + 2) {
//                mvpPresenter.getappClicet(ACTION_DEFAULT + 3, recommender_money, model.toString());
//
//            } else if (action == ACTION_DEFAULT + 3) {
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(model.toString());
//                    String responseBody = jsonObject.getString("responseBody");
//                    mvpPresenter.pay(responseBody);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            } else if (action == ACTION_DEFAULT + 4) {
//                if (model != null) {
//                    pmlb.clear();
//                    atmlb.clear();
//                    try {
//                        JSONObject jsonObject = new JSONObject((String) model);
//                        JSONArray pmlbList = jsonObject.getJSONArray("plusMoneyList");
//                        JSONArray atmlblist = jsonObject.getJSONArray("advertisementList");
////                        Toast.makeText(ApplyRefereeActivity.this,(JSONObject)atmlblist.get(0).toString(),Toast.LENGTH_LONG).show();
//
//                        for (int i = 0; i < pmlbList.length(); i++) {
//                            JSONObject o1 = (JSONObject) pmlbList.get(i);
//                            plusMoneyListBean goodsHotListBean = JSONUtils.GsonToBean(o1.toString(), plusMoneyListBean.class);
//                            pmlb.add(goodsHotListBean);
//                        }
//                        Toast.makeText(ApplyRefereeActivity.this, pmlb.get(0).getValue(), Toast.LENGTH_LONG).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//    }
//
//    @Override
//    public void getDataFail(String msg, int action) {
//
//    }
//
//    @Override
//    protected ApplyRefereePresenter createPresenter() {
//        return new ApplyRefereePresenter(this);
//    }
//
//    //支付成功，回传成功信息！
//    public void successPay() {
//        setResult(20);
//        finish();
//    }
//
//
//    private class ARAdapter extends RecyclerView.Adapter<ARAdapter.MyViewHolder> {
//        private String[] strings;
//        private Context mContext;
//        private OnItemClickListener onItemClickListener;
//
//        private int mSelect = 0;
//
//        public ARAdapter(String[] strings, Context mContext) {
//            this.strings = strings;
//            this.mContext = mContext;
//        }
//
//        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//            this.onItemClickListener = onItemClickListener;
//        }
//
//        /**
//         * 刷新方法
//         *
//         * @param positon
//         */
//        public void changeSelected(int positon) {
//            if (positon != mSelect) {
//                mSelect = positon;
//                notifyDataSetChanged();
//            }
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_apply_referee, parent, false));
//            return vh;
//        }
//
//        @Override
//        public void onBindViewHolder(final MyViewHolder holder, int position) {
//            if (onItemClickListener != null) {
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int position = holder.getLayoutPosition();
//                        onItemClickListener.onItemClick(holder.itemView, position);
//                    }
//                });
//            }
//            //点击改变背景
//            if (mSelect == position) {
//                holder.textView.setBackgroundResource(R.drawable.ic_ar002);
//                holder.textView.setTextColor(Color.WHITE);
//            } else {
//                holder.textView.setBackgroundResource(R.drawable.ic_ar001);
//                holder.textView.setTextColor(Color.GRAY);
//            }
//            holder.textView.setText(strings[position]+"元");
//        }
//
//        @Override
//        public int getItemCount() {
//            return strings.length;
//        }
//
//        public class MyViewHolder extends BaseRecyclerViewHolder {
//            private TextView textView;
//
//            public MyViewHolder(View view) {
//                super(view);
//                textView = view.findViewById(R.id.item_tv_ar);
//            }
//        }
//    }
//}

package com.chehubang.duolejie.modules.chargecenter.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chehubang.duolejie.NearbyMerchants.OnItemClickListener;
import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.model.BannerBean;
import com.chehubang.duolejie.model.PlusMoney;
import com.chehubang.duolejie.model.UserDataInfoBean;
import com.chehubang.duolejie.model.plusMoneyListBean;
import com.chehubang.duolejie.model.advertisementList;
import com.chehubang.duolejie.modules.chargecenter.presenter.ApplyRefereePresenter;
import com.chehubang.duolejie.modules.home.activity.WebviewActivity;
import com.chehubang.duolejie.utils.BaseRecyclerViewHolder;
import com.chehubang.duolejie.utils.GsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Utils.JSONUtils;
import common.Utils.ToastUtils;
import common.mvp.activity.MainView;


/**
 * Created by Beidouht on 2017/12/20.
 */

public class ApplyRefereeActivity extends BaseActivity<ApplyRefereePresenter> implements MainView, View.OnClickListener {

    private String recommender_money;
    private GridView gridView;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> dataList;
    private ArrayList<plusMoneyListBean> pmlb = new ArrayList<>();
    private ArrayList<advertisementList> atmlb = new ArrayList<>();

    //String name[] = {"500", "1000", "2000", "5000", "10000"};
    private List<PlusMoney.PlusMoneyListBean> moneyList = new ArrayList<>();
    private ARAdapter arAdapter;
    private RecyclerView rvApplyReferee;

    private String money;

    private TextView tv_pay;
    private ImageView iv_banner;

    private RelativeLayout rel_apply_referee;

    private TextView tv_return_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyreferee);
        ImageView back = (ImageView) findViewById(R.id.iv_applyreferee_back);
        View ll_noaccordcondition = (View) findViewById(R.id.ll_noaccordcondition);
        View ll_accordcondition = (View) findViewById(R.id.ll_accordcondition);
        back.setOnClickListener(this);
        ll_noaccordcondition.setOnClickListener(this);
        ll_accordcondition.setOnClickListener(this);

        tv_pay = (TextView) findViewById(R.id.tv_apply_freree_pay);
        tv_pay.setOnClickListener(this);

        gridView = (GridView) findViewById(R.id.gridview);
        rvApplyReferee = (RecyclerView) findViewById(R.id.rvApplyReferee);
        iv_banner = (ImageView) findViewById(R.id.iv_banner);
        iv_banner.setOnClickListener(this);
        rel_apply_referee = (RelativeLayout) findViewById(R.id.rel_apply_referee);
        rel_apply_referee.setOnClickListener(this);
        tv_return_money= (TextView) findViewById(R.id.tv_apply_freree_return_money);

        //初始化数据
        initData();
    }

    void initData() {
        mvpPresenter.getPlusMoneyList(ACTION_DEFAULT + 4);
       // mvpPresenter.addBanner(ACTION_DEFAULT + 3, "14");

        arAdapter = new ARAdapter(this,moneyList);
        rvApplyReferee.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        rvApplyReferee.setAdapter(arAdapter);
        arAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                arAdapter.changeSelected(position);
                money = moneyList.get(position).getValue();
                Log.e("ssssss", "money = " + money);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_applyreferee_back:
                finish();
                break;

            case R.id.ll_noaccordcondition:
                setResult(10);
                finish();
                break;

            case R.id.ll_accordcondition:
                // mvpPresenter.getUserData(ACTION_DEFAULT + 1, UserInfo.getInstance().getId());
//                setResult(20);
//                finish();
                break;

            case R.id.tv_apply_freree_pay:
                //确认支付
                if(!TextUtils.isEmpty(money)){
                    if(!TextUtils.isEmpty(UserInfo.getInstance().getId())){
                        mvpPresenter.getRecommender(ACTION_DEFAULT+1,UserInfo.getInstance().getId(),money);
                    }
                }else{
                    ToastUtils.showToast(this,"请选择支付金额");
                }
                break;
            case R.id.rel_apply_referee:
                //协议
                setIntent("plus会员协议","http://39.107.14.118/appInterface/aboutUsContentInfo.jhtml?id=20");
                break;
            case R.id.iv_banner:
                //图片
                setIntent("PLUS会员","http://39.107.14.118/appInterface/aboutUsContentInfo.jhtml?id=19");
                break;

            default:
                break;
        }
    }

    /**
     * 跳转web
     * @param title
     * @param url
     */
    public void setIntent(String title, String url){
        Intent intent = new Intent(getApplicationContext(), WebviewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    @Override
    public void getDataSuccess(Object model, int action) {
        if (model != null) {
            if(action == ACTION_DEFAULT+1){
                mvpPresenter.getappClicet(ACTION_DEFAULT+2,money,model.toString());
            }else if(action == ACTION_DEFAULT+2){
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(model.toString());
                    String responseBody = jsonObject.getString("responseBody");
                    mvpPresenter.pay(responseBody);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if (action == ACTION_DEFAULT + 3) {
                Wrapper wrapper = GsonUtil.gsonIntance().gsonToBean(model.toString(), Wrapper.class);
                Glide.with(this).load(wrapper.getAdvertisementList().get(0).getHeader()).into(iv_banner);
            }else if(action == ACTION_DEFAULT+4){
                //金额
                PlusMoney plusMoney = GsonUtil.gsonIntance().gsonToBean(model.toString(),PlusMoney.class);
                tv_return_money.setText(""+plusMoney.getReturn_money());
                Glide.with(this).load(plusMoney.getAdvertisementList().get(0).getHeader()).into(iv_banner);
                moneyList.clear();
                moneyList.addAll(plusMoney.getPlusMoneyList());
                arAdapter.notifyDataSetChanged();
            }
        }

    }
    class Wrapper {
        private List<BannerBean> advertisementList;

        public List<BannerBean> getAdvertisementList() {
            return advertisementList;
        }

        public void setAdvertisementList(List<BannerBean> advertisementList) {
            this.advertisementList = advertisementList;
        }
    }

    @Override
    public void getDataFail(String msg, int action) {

    }

    @Override
    protected ApplyRefereePresenter createPresenter() {
        return new ApplyRefereePresenter(this);
    }

    //支付成功，回传成功信息！
    public void successPay() {
        setResult(20);
        finish();
    }


    private class ARAdapter extends RecyclerView.Adapter<ARAdapter.MyViewHolder> {
       // private String[] strings;
        private Context mContext;
        private List<PlusMoney.PlusMoneyListBean> moneyList;

        public ARAdapter(Context mContext, List<PlusMoney.PlusMoneyListBean> moneyList) {
            this.mContext = mContext;
            this.moneyList = moneyList;
        }

        private OnItemClickListener onItemClickListener;

        private int mSelect = 0;

//        public ARAdapter(String[] strings, Context mContext) {
//            this.strings = strings;
//            this.mContext = mContext;
//        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        /**
         * 刷新方法
         *
         * @param positon
         */
        public void changeSelected(int positon) {
            if (positon != mSelect) {
                mSelect = positon;
                notifyDataSetChanged();
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_apply_referee, parent, false));
            return vh;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
            //点击改变背景
            if (mSelect == position) {
                holder.textView.setBackgroundResource(R.drawable.ic_ar002);
                holder.textView.setTextColor(Color.WHITE);
            } else {
                holder.textView.setBackgroundResource(R.drawable.ic_ar001);
                holder.textView.setTextColor(Color.GRAY);
            }
            holder.textView.setText(moneyList.get(position).getValue()+"元");
        }

        @Override
        public int getItemCount() {
            return moneyList.size();
        }

        public class MyViewHolder extends BaseRecyclerViewHolder {
            private TextView textView;

            public MyViewHolder(View view) {
                super(view);
                textView = view.findViewById(R.id.item_tv_ar);
            }
        }
    }
}

