package com.chehubang.duolejie.utils;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 设备状态栏margin
 * Created by jingang on 2018/7/24.
 */
public class TatusBarUtils {

    public static void setTopMargin(Context mContext, View view) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(view.getLayoutParams());
        lp.setMargins(0, getStatusBarHeight(mContext), 0, 0);
        view.setLayoutParams(lp);
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
