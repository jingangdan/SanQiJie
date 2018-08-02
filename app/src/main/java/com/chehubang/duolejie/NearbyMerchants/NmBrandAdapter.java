package com.chehubang.duolejie.NearbyMerchants;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * 附近商户-商户信息（适配器）
 * Created by jingang on 2018/7/20.
 */

public class NmBrandAdapter extends RecyclerView.Adapter<NmBrandAdapter.MyViewHolder> {
    private List<LineBrand.LineBrandListBean> list;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.0");

    public NmBrandAdapter(List<LineBrand.LineBrandListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_line_brand, parent, false));
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

        Glide.with(mContext)
                .load(list.get(position).getBrand_header())
                .into(holder.img);

        //星级
        if (!TextUtils.isEmpty(list.get(position).getStar_num())) {
            SPUtils.setLevel(holder.itemFishingLlLevel, mContext, Integer.parseInt(list.get(position).getStar_num()));
        }

        holder.brand_name.setText(list.get(position).getBrand_name());
        holder.brand_type.setText(list.get(position).getBrand_type());

        holder.consume_num.setText(list.get(position).getConsume_num() + "人消费");
        holder.distance.setText(myformat.format(list.get(position).getDistance()) + "km");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends BaseRecyclerViewHolder {
        private ImageView img;
        private TextView brand_name, brand_type, distance, consume_num;
        LinearLayout itemFishingLlLevel;

        public MyViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.item_iv_brand_header);
            brand_name = view.findViewById(R.id.item_tv_brand_name);
            brand_type = view.findViewById(R.id.item_tv_brand_type);
            consume_num = view.findViewById(R.id.item_tv_consume_num);

            itemFishingLlLevel = view.findViewById(R.id.item_fishing_ll_level);
            distance = view.findViewById(R.id.item_tv_distance);

        }
    }

}
