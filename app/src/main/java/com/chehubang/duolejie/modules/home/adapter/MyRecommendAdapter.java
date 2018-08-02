package com.chehubang.duolejie.modules.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseRecyclerViewHolder;
import com.chehubang.duolejie.model.Recommend;
import com.chehubang.duolejie.modules.gift.activity.GlideCircleTransform;

import java.util.List;

/**
 * 我的-我的推荐人（适配器）
 * Created by jingang on 2018/7/18.
 */

public class MyRecommendAdapter extends RecyclerView.Adapter<MyRecommendAdapter.MyViewHolder> {
    private List<Recommend.UserRecommendListBean> list;
    private Context mContext;

    public MyRecommendAdapter(List<Recommend.UserRecommendListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recommend, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(mContext));

        Glide.with(mContext)
                .load(list.get(position).getUser_header())
                .apply(options)
                .into(holder.header);

        holder.tvName.setText(list.get(position).getNick_name());
        holder.tvId.setText("ID:" + list.get(position).getUser_id());
        holder.tvMoney.setText(list.get(position).getReturnMoney() + "元");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends BaseRecyclerViewHolder {
        private ImageView header;
        private TextView tvName, tvId, tvMoney;

        public MyViewHolder(View view) {
            super(view);
            header = view.findViewById(R.id.item_iv_recommend);
            tvName = view.findViewById(R.id.item_tv_recommend_name);
            tvId = view.findViewById(R.id.item_tv_recommend_id);
            tvMoney = view.findViewById(R.id.item_tv_recommend_money);
        }
    }
}
