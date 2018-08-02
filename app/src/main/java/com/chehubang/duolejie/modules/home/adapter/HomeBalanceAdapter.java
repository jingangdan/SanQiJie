package com.chehubang.duolejie.modules.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.model.RadioListBean;
import com.chehubang.duolejie.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import common.picture.PictureUtils;

public class HomeBalanceAdapter extends BaseAdapter {
    private List<RadioListBean> list = new ArrayList<>();
    private Context context;

    public HomeBalanceAdapter(List<RadioListBean> list, Context context) {
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
            convertView = View.inflate(context, R.layout.homebalance_item, null);
            mHolder = new ViewHolder();
            mHolder.tv_address = convertView.findViewById(R.id.homebalance_address);
            mHolder.tv_money = convertView.findViewById(R.id.homebalance_money);
            mHolder.tv_name = convertView.findViewById(R.id.homebalance_name);
            mHolder.tv_time = convertView.findViewById(R.id.homebalance_time);
            mHolder.im_icon = convertView.findViewById(R.id.homebalance_icon);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        Log.e("Tag", "yq--->" + list.toString());
        PictureUtils.loadPicture(context, list.get(position).getUser_header(), mHolder.im_icon, R.drawable.pic_cycjjl);
        mHolder.tv_money.setText(list.get(position).getCoupon_num());
        mHolder.tv_time.setText(DateUtils.TimeStamp2Date04(Long.parseLong(list.get(position).getCreate_time())));
        mHolder.tv_name.setText(list.get(position).getNick_name());
        mHolder.tv_address.setText(list.get(position).getUser_ip_address());
        return convertView;
    }

    class ViewHolder {
        TextView tv_name, tv_time, tv_address, tv_money;
        ImageView im_icon;
    }
}
