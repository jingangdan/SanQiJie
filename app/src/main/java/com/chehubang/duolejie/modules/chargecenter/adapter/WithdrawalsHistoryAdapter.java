package com.chehubang.duolejie.modules.chargecenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.model.MoneyHistoryBean;
import com.chehubang.duolejie.model.userMoneyBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class WithdrawalsHistoryAdapter extends RecyclerView.Adapter {
    private final Context mcontext;
    private final List<MoneyHistoryBean> mlist;
    private OnItemBack onItemBack;

    public WithdrawalsHistoryAdapter(Context context, List<MoneyHistoryBean> list) {
        this.mcontext = context;
        this.mlist = list;
    }

    public void setOnItemBack(OnItemBack onItemBack){
        this.onItemBack=onItemBack;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_withdrawalshistory, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder hd = (ViewHolder) holder;
        final MoneyHistoryBean bean = mlist.get(position);
        hd.tv_create_time.setText(bean.getCreate_time());
        hd.tv_money.setText(bean.getGet_money()+"元");


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {


        public  TextView tv_money;
        public  TextView tv_create_time;
        public CheckBox select_money;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_create_time = (TextView) itemView.findViewById(R.id.tv_create_time);
        }
    }

    public interface OnItemBack{
        void checkitemBack();
    }
}
