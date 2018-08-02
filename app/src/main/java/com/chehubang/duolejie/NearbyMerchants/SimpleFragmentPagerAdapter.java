package com.chehubang.duolejie.NearbyMerchants;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chehubang.duolejie.model.BrandType;

import java.util.List;

/**
 * Created by jingang on 2016/11/2.
 * 店铺 TabLayout
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<Fragment> fragments;
    private String[] titles;
    private List<BrandType.BrandTypeListBean> list;


    public SimpleFragmentPagerAdapter(FragmentManager fm, Context mContext, List<Fragment> fragments, List<BrandType.BrandTypeListBean> list) {
        super(fm);
        this.mContext = mContext;
        this.fragments = fragments;
//        this.titles = titles;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getBrand_type();
    }

    @Override
    public long getItemId(int position) {
        super.getItemId(position);
        if (fragments != null) {
            if (position < fragments.size()) {
                return fragments.get(position).hashCode();       //important
            }
        }

        System.out.println("position = "+position );
        return super.getItemId(position);
    }



}
