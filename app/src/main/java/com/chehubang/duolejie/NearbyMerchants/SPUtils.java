package com.chehubang.duolejie.NearbyMerchants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chehubang.duolejie.R;

/**
 * @author gengqiufang
 * @describtion 存取数据 公共类
 * @created at 2017/4/26 0026
 */

public class SPUtils {
    public static void setLevel(LinearLayout linearLayout, Context mContext, int star) {
        linearLayout.removeAllViews();
        for (int i = 0; i < star; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_level_image, null);
            ImageView item_iv = (ImageView) view.findViewById(R.id.item_img_level);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(item_iv.getLayoutParams());
            float dimension = mContext.getResources().getDimension(R.dimen.space_24);
            int round = Math.round(dimension);
            lp.setMargins(round, 0, 0, 0);
            item_iv.setLayoutParams(lp);
            linearLayout.addView(view);
        }
    }
}
