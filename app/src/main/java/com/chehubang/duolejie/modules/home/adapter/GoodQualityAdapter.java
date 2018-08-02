package com.chehubang.duolejie.modules.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.model.GoodQualityBean;

import java.util.List;

import common.picture.PictureUtils;

/**
 * 品质好物
 */
public class GoodQualityAdapter extends BaseAdapter {
    private List<GoodQualityBean> list;
    private Context context;
    //为三种布局定义一个标识
    private final int TYPE1 = 0;
    private final int TYPE2 = 1;
    private LayoutInflater inflater;

    public GoodQualityAdapter(List<GoodQualityBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list.size()>2){
            return 2;
        }else {
            return list.size();
        }
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (position % 2 == 0) {
            return TYPE1;
        } else {
            return TYPE2;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;

        int type = getItemViewType(position);


        switch (type) {
            case TYPE1:
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.home_goodquality_left, null, false);
                    convertView = View.inflate(context, R.layout.home_goodquality_left, null);
                    viewHolder1 = new ViewHolder1();
                    viewHolder1.icon_pic1 = convertView.findViewById(R.id.goodQuality_lefts_icon);
                    viewHolder1.tv_content_text1 = convertView.findViewById(R.id.goodQuality_lefts_content);
                    viewHolder1.tv_good_price_text1 = convertView.findViewById(R.id.goodQuality_lefts_money);
                    convertView.setTag(viewHolder1);
                } else {
                    viewHolder1 = (ViewHolder1) convertView.getTag();

                }
                break;
            case TYPE2:
                if (convertView == null) {

                    convertView = View.inflate(context, R.layout.home_goodquality_right, null);
                    viewHolder2 = new ViewHolder2();
                    viewHolder2.icon_pic2 = convertView.findViewById(R.id.goodQuality_right_icon);
                    viewHolder2.tv_content_text2 = convertView.findViewById(R.id.goodQuality_right_content);
                    viewHolder2.tv_good_price_text2 = convertView.findViewById(R.id.goodQuality_right_money);
                    convertView.setTag(viewHolder2);
                } else {
                    viewHolder2 = (ViewHolder2) convertView.getTag();

                }
                break;
        }
        switch (type) {
            case TYPE1:
                PictureUtils.loadPicture(context, list.get(position).getGood_header(), viewHolder1.icon_pic1, R.drawable.pic_cycjjl);
                viewHolder1.tv_content_text1.setText(list.get(position).getGood_name());
                viewHolder1.tv_good_price_text1.setText("￥"+list.get(position).getGood_price());
                break;
            case TYPE2:
                PictureUtils.loadPicture(context, list.get(position).getGood_header(), viewHolder2.icon_pic2, R.drawable.pic_cycjjl);
                viewHolder2.tv_content_text2.setText(list.get(position).getGood_name());
                viewHolder2.tv_good_price_text2.setText("￥"+list.get(position).getGood_price());

                break;

        }

        return convertView;
    }

    class ViewHolder1 {
        ImageView icon_pic1;
        TextView icon_title1;
        TextView tv_good_price_text1;
        TextView tv_content_text1;

    }

    class ViewHolder2 {
        ImageView icon_pic2;
        TextView icon_title2;
        TextView tv_good_price_text2;
        TextView tv_content_text2;

    }
}
