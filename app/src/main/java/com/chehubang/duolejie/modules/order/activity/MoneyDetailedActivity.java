package com.chehubang.duolejie.modules.order.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseActivity;
import com.chehubang.duolejie.config.UserInfo;
import com.chehubang.duolejie.model.MoneyHistoryBean;
import com.chehubang.duolejie.model.MoreDetailBean;
import com.chehubang.duolejie.model.OrderBean;
import com.chehubang.duolejie.model.userMoneyBean;
import com.chehubang.duolejie.modules.chargecenter.adapter.MoneyDetailedAdapter;
import com.chehubang.duolejie.modules.chargecenter.adapter.SelectMoneyAdapter;
import com.chehubang.duolejie.modules.order.adapter.AfterSalesAdapter;
import com.chehubang.duolejie.modules.order.presenter.AfterSalesPresenter;
import com.chehubang.duolejie.modules.order.presenter.MoneyDetailedPresenter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import common.Utils.JSONUtils;
import common.Utils.ToastUtils;
import common.mvp.activity.MainView;

/**
 * Created by ZZH on 2018/1/31
 *
 * @date: 2018/1/31 上午11:52
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @Description: 售后/退换货
 */
public class MoneyDetailedActivity extends BaseActivity<MoneyDetailedPresenter> implements MainView, View.OnClickListener {

    private ListView order_list;
    private AfterSalesAdapter orderAdapter;
    private ArrayList<MoreDetailBean> mList = new ArrayList<>();
    private ListView moneydetailed_content;
    private MoneyDetailedAdapter mAdapter;
    private TextView tv_detailsofexpenditure, tv_cashcoupon, tv_recharge;
    private MoneyDetailedPresenter mvpPresenter;
    private String action = "";
    private int pageNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moneydetailed);
        mvpPresenter = new MoneyDetailedPresenter(MoneyDetailedActivity.this);
        order_list = (ListView) findViewById(R.id.lv_after_sales_list);
        findAtyViewById(R.id.iv_moneydetaile_back).setOnClickListener(this);
        tv_detailsofexpenditure = findAtyViewById(R.id.tv_detailsofexpenditure);
        tv_cashcoupon = findAtyViewById(R.id.tv_cashcoupon);
        tv_recharge = findAtyViewById(R.id.tv_recharge);
        moneydetailed_content = findAtyViewById(R.id.rv_moneydetailed_content);
        mvpPresenter.getMoreDetail(ACTION_DEFAULT + 1, UserInfo.getInstance().getId(), "1", pageNum + "", "10");

//        moneydetailed_content.setLayoutManager(new LinearLayoutManager(this));
//        moneydetailed_content.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new MoneyDetailedAdapter(this, mList);
        moneydetailed_content.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        setOnScrollListnear();
        tv_cashcoupon.setOnClickListener(this);
        tv_detailsofexpenditure.setOnClickListener(this);
        tv_recharge.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Drawable drawable = getResources().getDrawable(R.drawable.scroll_line);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (view.getId()) {
            case R.id.iv_moneydetaile_back:
                finish();
                break;

            case R.id.tv_detailsofexpenditure:
                mList.clear();
                pageNum = 0;
                mAdapter.notifyDataSetChanged();
                tv_detailsofexpenditure.setTextColor(getResources().getColor(R.color.color_db5044));
                tv_detailsofexpenditure.setCompoundDrawables(null, null, null, drawable);

                tv_recharge.setTextColor(getResources().getColor(R.color.black));
                tv_recharge.setCompoundDrawables(null, null, null, null);
                tv_cashcoupon.setTextColor(getResources().getColor(R.color.black));
                tv_cashcoupon.setCompoundDrawables(null, null, null, null);
                action = (ACTION_DEFAULT + 1) + "";
                mvpPresenter.getMoreDetail(ACTION_DEFAULT + 1, UserInfo.getInstance().getId(), "1", "0", "10");
                break;

            case R.id.tv_cashcoupon:
                mList.clear();
                pageNum = 0;
                mAdapter.notifyDataSetChanged();

                tv_cashcoupon.setTextColor(getResources().getColor(R.color.color_db5044));
                tv_cashcoupon.setCompoundDrawables(null, null, null, drawable);

                tv_detailsofexpenditure.setTextColor(getResources().getColor(R.color.black));
                tv_detailsofexpenditure.setCompoundDrawables(null, null, null, null);

                tv_recharge.setTextColor(getResources().getColor(R.color.black));
                tv_recharge.setCompoundDrawables(null, null, null, null);
                action = (ACTION_DEFAULT + 2) + "";

                mvpPresenter.getMoreDetail(ACTION_DEFAULT + 2, UserInfo.getInstance().getId(), "2", "0", "10");
                break;

            case R.id.tv_recharge:
                mList.clear();
                pageNum = 0;
                mAdapter.notifyDataSetChanged();
                tv_recharge.setTextColor(getResources().getColor(R.color.color_db5044));
                tv_recharge.setCompoundDrawables(null, null, null, drawable);

                tv_detailsofexpenditure.setTextColor(getResources().getColor(R.color.black));
                tv_detailsofexpenditure.setCompoundDrawables(null, null, null, null);
                tv_cashcoupon.setTextColor(getResources().getColor(R.color.black));
                tv_cashcoupon.setCompoundDrawables(null, null, null, null);
                action = (ACTION_DEFAULT + 3) + "";

                mvpPresenter.getMoreDetail(ACTION_DEFAULT + 3, UserInfo.getInstance().getId(), "3", "0", "10");
                break;
            default:
                break;
        }
    }

    @Override
    public void getDataSuccess(Object model, int action) {
        if (model != null) {
            if (action == ACTION_DEFAULT + 1) {
                try {
                    String userMoneyExchangeList = new JSONObject(model.toString()).getString("listResult");
                    ArrayList<MoreDetailBean> mlist = JSONUtils.GsonjsonToArrayList(userMoneyExchangeList, MoreDetailBean.class);
                    mList.addAll(mlist);
                    Log.e("Tag", "yq--->" + mlist.toString());
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
            } else if (action == ACTION_DEFAULT + 2) {
                try {
                    String userMoneyExchangeList = new JSONObject(model.toString()).getString("listResult");
                    ArrayList<MoreDetailBean> mlist = JSONUtils.GsonjsonToArrayList(userMoneyExchangeList, MoreDetailBean.class);
                    mList.addAll(mlist);
                    Log.e("Tag", "yq--->" + mlist.toString());
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
            } else if (action == ACTION_DEFAULT + 3) {
                try {
                    String userMoneyExchangeList = new JSONObject(model.toString()).getString("listResult");
                    ArrayList<MoreDetailBean> mlist = JSONUtils.GsonjsonToArrayList(userMoneyExchangeList, MoreDetailBean.class);
                    mList.addAll(mlist);
                    Log.e("Tag", "yq--->" + mlist.toString());
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 主ListView滚动监听事件
     */
    private void setOnScrollListnear() {
        moneydetailed_content.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

                if (i == SCROLL_STATE_IDLE) {
                    if (absListView.getLastVisiblePosition() == (absListView.getCount() - 1)) {

                        pageNum++;
                        if (mList.size() < 10) {
                            ToastUtils.centerToastWhite(getApplicationContext(), "已加载全部数据");
                            return;
                        }

                        if (action.equals((ACTION_DEFAULT + 1) + "")) {
                            mvpPresenter.getMoreDetail(ACTION_DEFAULT + 1, UserInfo.getInstance().getId(), "1", pageNum + "", "10");

                        } else if (action.equals((ACTION_DEFAULT + 2) + "")) {
                            mvpPresenter.getMoreDetail(ACTION_DEFAULT + 2, UserInfo.getInstance().getId(), "2", pageNum + "", "10");

                        } else if (action.equals((ACTION_DEFAULT + 3) + "")) {
                            mvpPresenter.getMoreDetail(ACTION_DEFAULT + 3, UserInfo.getInstance().getId(), "3", pageNum + "", "10");

                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void getDataFail(String msg, int action) {

    }

    @Override
    protected MoneyDetailedPresenter createPresenter() {
        return new MoneyDetailedPresenter(this);
    }
}
