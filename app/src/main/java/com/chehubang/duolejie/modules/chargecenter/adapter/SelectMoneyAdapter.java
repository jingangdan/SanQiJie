package com.chehubang.duolejie.modules.chargecenter.adapter;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.model.ChargeOrderGoodsBean;
import com.chehubang.duolejie.model.userMoneyBean;

import java.util.List;

import common.picture.PictureUtils;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class SelectMoneyAdapter extends RecyclerView.Adapter {
    private final Context mcontext;
    private final List<userMoneyBean> mlist;
    private OnItemBack onItemBack;

    public SelectMoneyAdapter(Context context, List<userMoneyBean> list) {
        this.mcontext = context;
        this.mlist = list;
    }

    public void setOnItemBack(OnItemBack onItemBack){
        this.onItemBack=onItemBack;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_selectmoney, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder hd = (ViewHolder) holder;
        final userMoneyBean userMoneyBean = mlist.get(position);
        hd.tv_create_time.setText(userMoneyBean.getCreate_time());
        hd.tv_money.setText(userMoneyBean.getMoney()+"元");
        hd.select_money.setChecked(userMoneyBean.getSelectd());
        hd.select_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(hd.select_money.isChecked()){
                    hd.select_money.setChecked(true);
                    userMoneyBean.setSelectd(true);
                }else {
                    hd.select_money.setChecked(false);
                    userMoneyBean.setSelectd(false);

                }

                notifyDataSetChanged();
                if(onItemBack!=null){
                    onItemBack.checkitemBack();
                }
            }
        });

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
            select_money = (CheckBox) itemView.findViewById(R.id.select_money);
        }
    }

    public interface OnItemBack{
        void checkitemBack();
    }
}
