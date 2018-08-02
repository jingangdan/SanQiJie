package com.chehubang.duolejie.modules.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.model.GoodsGuestBean;

import java.util.List;

import common.picture.PictureUtils;

public class GoodGuestAdapter extends BaseAdapter {
    private List<GoodsGuestBean> list;
    private Context context;

    public GoodGuestAdapter(List<GoodsGuestBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.goodguest, null);
            mHolder = new ViewHolder();
            mHolder.tvMoney = convertView.findViewById(R.id.goodguest_money);
            mHolder.tvName = convertView.findViewById(R.id.goodguest_title);
            mHolder.tvContent = convertView.findViewById(R.id.goodguest_contnet);
            mHolder.imIcon=convertView.findViewById(R.id.goodguest_icons);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.tvContent.setText(list.get(position).getGood_name());
        mHolder.tvMoney.setText("￥"+list.get(position).getGood_price()+"元");
        mHolder.tvName.setText(list.get(position).getGoods_service());
        PictureUtils.loadPicture(context,list.get(position).getGood_header(),mHolder.imIcon, R.drawable.pic_cycjjl);
        return convertView;
    }

    class ViewHolder {
        TextView tvName,tvContent,tvMoney;
        ImageView imIcon;
    }
}
