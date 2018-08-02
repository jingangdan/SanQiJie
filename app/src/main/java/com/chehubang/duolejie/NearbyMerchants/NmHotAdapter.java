package com.chehubang.duolejie.NearbyMerchants;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.base.BaseRecyclerViewHolder;
import com.chehubang.duolejie.model.HotKey;

import java.util.List;

/**
 * 附近商户（热门搜索词）
 * Created by jingang on 2018/7/20.
 */
public class NmHotAdapter extends RecyclerView.Adapter<NmHotAdapter.MyViewHolder> {
    private Context mContext;
    private List<HotKey.HotSearchListBean> list;
    private OnItemClickListener onItemClickListener;

    private int mSelect = 0;

    public NmHotAdapter(Context mContext, List<HotKey.HotSearchListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

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
        MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_nm_hot, parent, false));
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
            holder.tv.setBackgroundResource(R.drawable.kuang3);
            holder.tv.setTextColor(Color.WHITE);
        } else {
            holder.tv.setBackgroundResource(R.drawable.kuang2);
            holder.tv.setTextColor(Color.GRAY);
        }

        holder.tv.setText(list.get(position).getSearch_key());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends BaseRecyclerViewHolder {
        private TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.item_tv_nm);
        }
    }
}
