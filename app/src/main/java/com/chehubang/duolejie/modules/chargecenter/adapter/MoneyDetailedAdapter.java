package com.chehubang.duolejie.modules.chargecenter.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.model.MoreDetailBean;
import com.chehubang.duolejie.model.userMoneyBean;
import com.chehubang.duolejie.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class MoneyDetailedAdapter extends BaseAdapter {
    private final Context mcontext;
    private final List<MoreDetailBean> mlist;

    public MoneyDetailedAdapter(Context context, List<MoreDetailBean> list) {
        this.mcontext = context;
        this.mlist = list;
    }


    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView==null){
            convertView=View.inflate(mcontext,R.layout.moneydetail_item,null);
            mHolder=new ViewHolder();
            mHolder.im_type=convertView.findViewById(R.id.moneydetail_icon);
            mHolder.tv_money=convertView.findViewById(R.id.moneydetail_money);
            mHolder.tv_time=convertView.findViewById(R.id.moneydetail_time);
            mHolder.tv_type=convertView.findViewById(R.id.moneydetail_type);
            convertView.setTag(mHolder);
        }else {
            mHolder= (ViewHolder) convertView.getTag();
        }
        mHolder.tv_type.setText(mlist.get(position).getType());
        if (mlist.get(position).getType().contains("手机充值")||mlist.get(position).getType().contains("购物")){
            mHolder.tv_money.setText("-"+mlist.get(position).getMoney());
        }else {
            mHolder.tv_money.setText("+"+mlist.get(position).getMoney());
        }
        if (mlist.get(position).getType().equals("手机充值")){
            mHolder.im_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.moneydetail_mobeil));
        }else if (mlist.get(position).getType().equals("充值")){
            mHolder.im_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.moneydetail_recharge));

        }else if (mlist.get(position).getType().equals("购物")){
            mHolder.im_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.moneydetail_shop));

        }else if (mlist.get(position).getType().equals("代金券")){
            mHolder.im_type.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.moneydetail_juan));

        }
//        Log.e("Tag","yq--->"+mlist.get(position).getCreate_time());
        mHolder.tv_time.setText(DateUtils.TimeStamp2Date04(Long.parseLong(mlist.get(position).getCreate_time())));

        return convertView;
    }
    class  ViewHolder{
        private TextView tv_money,tv_time,tv_type;
        private ImageView im_type;
    }
}
