package com.chehubang.duolejie.modules.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.chehubang.duolejie.R;
import com.chehubang.duolejie.listener.OnHomeIconClickListener;
import com.chehubang.duolejie.listener.OnItemClickListener;
import com.chehubang.duolejie.model.GoodChoseBean;
import com.chehubang.duolejie.model.GoodQualityBean;
import com.chehubang.duolejie.model.GoodQualityBottomBean;
import com.chehubang.duolejie.model.GoodsGuestBean;
import com.chehubang.duolejie.model.GoodsHotListBean;
import com.chehubang.duolejie.model.GoodsListBean;
import com.chehubang.duolejie.model.RadioListBean;
import com.chehubang.duolejie.model.ViewPagerPicBean;
import com.chehubang.duolejie.modules.gooddetails.activity.GoodsDetailsActivity;
import com.chehubang.duolejie.modules.gooddetails.adapter.HeaderViewPager;
import com.chehubang.duolejie.modules.home.activity.HomeBalanceActivity;
import com.chehubang.duolejie.widget.HorizontalScrollListView;
import com.chehubang.duolejie.widget.IconGridView;
import com.chehubang.duolejie.widget.MarqueeTextView;
import com.chehubang.duolejie.widget.MyListview;
import com.chehubang.duolejie.widget.RecyclerViewBanner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import common.Log.LogUtils;
import common.Utils.ToastUtils;
import common.picture.PictureUtils;

/**
 * Created by Thinkpad on 2017/12/10.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mcontext;
    private OnHomeIconClickListener onHomeIconClick;
    private OnItemClickListener clickListener;

    private final int TYPE_HEADER = 1000;
    private final int TYPE_ICON = 1001;
    private final int TYPE_MUSTBUY = 1002;
    private final int TYPE_CONTENT = 1005;
    private final int TYPE_GOODQULITY = 1006;
    private final int TYPE_GOODQULITYBOTTOM = 1007;
    private final int TYPE_YOULIKE = 1008;
    private final int TYPE_GOODCHOSE = 1009;
    private List<ViewPagerPicBean> mlist = new ArrayList<>();
    private List<GoodsHotListBean> mgoodhotlist = new ArrayList<>();
    private List<GoodsListBean> mgoodlist = new ArrayList<>();
    private List<RadioListBean> marqueelist = new ArrayList<>();
    private List<GoodQualityBean> mqualitylist = new ArrayList<>();
    private List<GoodQualityBottomBean> mqualityBottomlist = new ArrayList<>();
    private List<GoodsGuestBean> mgoogguest = new ArrayList<>();
    private List<GoodChoseBean> mgoodchose = new ArrayList<>();

    private List<String> banners = new ArrayList<>();
    //显示那种类型的item


    public HomeAdapter(Context mcontext, List<ViewPagerPicBean> list, List<GoodsHotListBean> goodhotlist, List<GoodsListBean> goodlist
            , List<GoodQualityBean> mqualitylist, List<GoodQualityBottomBean> mqualityBottomlist, List<GoodsGuestBean> mgoogguest, List<GoodChoseBean> mgoodchose, ArrayList<RadioListBean> radioListBeans, OnItemClickListener clickListener) {
        this.mcontext = mcontext;
        this.mlist = list;
        this.mgoodlist = goodlist;
        this.mgoodhotlist = goodhotlist;
        this.marqueelist = radioListBeans;
        this.clickListener = clickListener;
        this.mqualitylist = mqualitylist;
        this.mqualityBottomlist = mqualityBottomlist;
        this.mgoogguest = mgoogguest;
        this.mgoodchose = mgoodchose;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() != 0) {
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == 1) {
                return TYPE_ICON;
            } else if (position == 2) {
                return TYPE_CONTENT;
            } else if (position == 3) {
                return TYPE_MUSTBUY;
            } else if (position == 4) {
                return TYPE_GOODQULITY;
            } else if (position == 5) {
                return TYPE_GOODQULITYBOTTOM;
            } else if (position == 6) {
                return TYPE_YOULIKE;
            } else if (position == 7) {
                return TYPE_GOODCHOSE;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mcontext);
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_HEADER:
                View header = mInflater.inflate(R.layout.item_home_header_viewpager, parent, false);
                holder = new HeaderViewHolder(header);
                break;
            case TYPE_ICON:
                View icon = mInflater.inflate(R.layout.item_home_icon, parent, false);
                holder = new IconViewHolder(icon);
                break;
            case TYPE_MUSTBUY:
                View goods = mInflater.inflate(R.layout.item_home_goodlist, parent, false);
                holder = new MustBuyViewHolder(goods);
                break;
            case TYPE_CONTENT:
                View content = mInflater.inflate(R.layout.item_home_goodlist_pinpai, parent, false);
                holder = new GoodsViewHolder(content);
                break;
            case TYPE_GOODQULITY:

                View quality = mInflater.inflate(R.layout.item_home_goodquality, parent, false);
                holder = new GoodQualityHolder(quality);
                break;
            case TYPE_GOODQULITYBOTTOM:

                View qualitybottom = mInflater.inflate(R.layout.goodquality_bottome_left, parent, false);
                holder = new GoodQualityBottomHolder(qualitybottom);
                break;
            case TYPE_YOULIKE:
                View like = mInflater.inflate(R.layout.item_home_goodguest, parent, false);
                holder = new YouLikeViewHolder(like);
                break;
            case TYPE_GOODCHOSE:
                View goodchose = mInflater.inflate(R.layout.home_goodchose, parent, false);
                holder = new GoodChoseHolder(goodchose);
                break;
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            banners.clear();
            for (int i = 0; i < mlist.size(); i++) {
                banners.add(mlist.get(i).getHeader());
            }
            ((HeaderViewHolder) holder).headerViewpager.isShowIndicator(true);
            ((HeaderViewHolder) holder).headerViewpager.setRvBannerData(banners);
            ((HeaderViewHolder) holder).headerViewpager.setOnSwitchRvBannerListener(new RecyclerViewBanner.OnSwitchRvBannerListener() {
                @Override
                public void switchBanner(int position, AppCompatImageView bannerView) {
                    PictureUtils.loadPicture(mcontext, banners.get(position % banners.size()), bannerView, R.drawable.pic_cycjjl);
                }
            });
            ((HeaderViewHolder) holder).headerViewpager.setOnRvBannerClickListener(new RecyclerViewBanner.OnRvBannerClickListener() {
                @Override
                public void onClick(int position) {
                    //点击事件
                    if (TextUtils.equals("y", mlist.get(position).getIs_jump())) {
                        Intent intent = new Intent(mcontext, GoodsDetailsActivity.class);
                        intent.putExtra("id", mlist.get(position).getUrl());
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        mcontext.startActivity(intent);
                    }
                }
            });


        } else if (holder instanceof IconViewHolder) {
            ((IconViewHolder) holder).icon_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onHomeIconClick.OnHomeIconClick(0);
                }
            });
            ((IconViewHolder) holder).icon_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onHomeIconClick.OnHomeIconClick(1);
                }
            });
            ((IconViewHolder) holder).icon_charge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onHomeIconClick.OnHomeIconClick(2);
                }
            });
            ((IconViewHolder) holder).icon_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onHomeIconClick.OnHomeIconClick(3);
                }
            });

            if (marqueelist.size() > 0) {
                for (int i = 0; i < marqueelist.size(); i++) {
                    View view = LayoutInflater.from(mcontext).inflate(R.layout.item_flipper_view, null);
                    TextView tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
                    TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
                    TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
                    tv_nickname.setText(marqueelist.get(i).getNick_name());
                    tv_num.setText("第" + marqueelist.get(i).getPeriod() + "期");
                    tv_price.setText(marqueelist.get(i).getCoupon_num());
                    ((IconViewHolder) holder).tv_marquee.addView(view);
                }
            }
            // 设置文字in/out的动画效果
            ((IconViewHolder) holder).tv_marquee.startFlipping();
            ((IconViewHolder) holder).tv_marquee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, HomeBalanceActivity.class);
                    mcontext.startActivity(intent);
                }
            });


        } else if (holder instanceof GoodQualityBottomHolder) {
            if (mqualityBottomlist.size()>0){
                for (int i=0;i<mqualityBottomlist.size();i++){
                    if (i==0){
                        ((GoodQualityBottomHolder) holder).icon_title1.setText(mqualityBottomlist.get(0).getGood_name());
                        ((GoodQualityBottomHolder) holder).tv_content_text1.setText(mqualityBottomlist.get(0).getGoods_service());
                        PictureUtils.loadPicture(mcontext, mqualityBottomlist.get(0).getGood_header(), ((GoodQualityBottomHolder) holder).icon_pic1, R.drawable.pic_cycjjl);

                    }
                    if (i==1){
                        ((GoodQualityBottomHolder) holder).icon_title2.setText(mqualityBottomlist.get(1).getGood_name());
                        ((GoodQualityBottomHolder) holder).tv_content_text2.setText(mqualityBottomlist.get(1).getGoods_service());
                        PictureUtils.loadPicture(mcontext, mqualityBottomlist.get(1).getGood_header(), ((GoodQualityBottomHolder) holder).icon_pic2, R.drawable.pic_cycjjl);
                        ((GoodQualityBottomHolder) holder).tv_good_price_text2.setText(mqualityBottomlist.get(1).getGood_price() + "元起");

                    }
                    if (i==2){
                        ((GoodQualityBottomHolder) holder).icon_title3.setText(mqualityBottomlist.get(2).getGood_name());
                        ((GoodQualityBottomHolder) holder).tv_content_text3.setText(mqualityBottomlist.get(2).getGoods_service());
                        PictureUtils.loadPicture(mcontext, mqualityBottomlist.get(2).getGood_header(), ((GoodQualityBottomHolder) holder).icon_pic3, R.drawable.pic_cycjjl);
                        ((GoodQualityBottomHolder) holder).tv_good_price_text3.setText(mqualityBottomlist.get(2).getGood_price() + "元起");

                    }

                }
            }

//            ((GoodQualityBottomHolder) holder).icon_title2.setText(mqualityBottomlist.get(2).getGood_name());
//            ((GoodQualityBottomHolder) holder).icon_title3.setText(mqualityBottomlist.get(1).getGood_name());
//
//            PictureUtils.loadPicture(mcontext, mqualityBottomlist.get(1).getGood_header(), ((GoodQualityBottomHolder) holder).icon_pic2, R.drawable.pic_cycjjl);
//            PictureUtils.loadPicture(mcontext, mqualityBottomlist.get(2).getGood_header(), ((GoodQualityBottomHolder) holder).icon_pic3, R.drawable.pic_cycjjl);
//
//            ((GoodQualityBottomHolder) holder).tv_content_text2.setText(mqualityBottomlist.get(1).getGoods_service());
//            ((GoodQualityBottomHolder) holder).tv_content_text3.setText(mqualityBottomlist.get(2).getGoods_service());
//
//            ((GoodQualityBottomHolder) holder).tv_good_price_text2.setText(mqualityBottomlist.get(1).getGood_price() + "元起");
//            ((GoodQualityBottomHolder) holder).tv_good_price_text3.setText(mqualityBottomlist.get(2).getGood_price() + "元起");


            ((GoodQualityBottomHolder) holder).right_rlb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, GoodsDetailsActivity.class);
                    intent.putExtra("id", mqualityBottomlist.get(2).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mcontext.startActivity(intent);
                }
            });
            ((GoodQualityBottomHolder) holder).left_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, GoodsDetailsActivity.class);
                    intent.putExtra("id", mqualityBottomlist.get(0).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mcontext.startActivity(intent);
                }
            });
            ((GoodQualityBottomHolder) holder).right_rlt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, GoodsDetailsActivity.class);
                    intent.putExtra("id", mqualityBottomlist.get(1).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mcontext.startActivity(intent);
                }
            });

        } else if (holder instanceof MustBuyViewHolder) {
            GoodHotListAdapter goodHotListAdapter = new GoodHotListAdapter(mcontext, mgoodhotlist);
            ((MustBuyViewHolder) holder).goodshotItem.setAdapter(goodHotListAdapter);
            ((MustBuyViewHolder) holder).goodshotItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(mcontext, GoodsDetailsActivity.class);
                    intent.putExtra("id", mgoodhotlist.get(i).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mcontext.startActivity(intent);

                }
            });
        } else if (holder instanceof GoodsViewHolder) {
            GoodListAdapter goodHotListAdapter = new GoodListAdapter(mcontext, mgoodlist);
            ((GoodsViewHolder) holder).goodsItem.setAdapter(goodHotListAdapter);
            ((GoodsViewHolder) holder).goodsItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(mcontext, GoodsDetailsActivity.class);
                    intent.putExtra("id", mgoodlist.get(i).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mcontext.startActivity(intent);
                }
            });
        } else if (holder instanceof GoodQualityHolder) {
            GoodQualityAdapter goodHotListAdapter = new GoodQualityAdapter(mqualitylist, mcontext);
            ((GoodQualityHolder) holder).goodshotItem.setAdapter(goodHotListAdapter);
            ((GoodQualityHolder) holder).goodshotItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(mcontext, GoodsDetailsActivity.class);
                    intent.putExtra("id", mqualitylist.get(i).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mcontext.startActivity(intent);

                }
            });
        } else if (holder instanceof YouLikeViewHolder) {
            GoodGuestAdapter goodHotListAdapter = new GoodGuestAdapter(mgoogguest, mcontext);
            ((YouLikeViewHolder) holder).goodshotItem.setAdapter(goodHotListAdapter);
            ((YouLikeViewHolder) holder).goodshotItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(mcontext, GoodsDetailsActivity.class);
                    intent.putExtra("id", mgoogguest.get(i).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mcontext.startActivity(intent);

                }
            });


        } else if (holder instanceof GoodChoseHolder) {
            GoodChoseAdapter goodHotListAdapter = new GoodChoseAdapter(mcontext, mgoodchose);
            ((GoodChoseHolder) holder).goodshotItem.setAdapter(goodHotListAdapter);
            ((GoodChoseHolder) holder).goodshotItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(mcontext, GoodsDetailsActivity.class);
                    intent.putExtra("id", mgoodchose.get(i).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mcontext.startActivity(intent);

                }
            });


        }
    }

    public void addOnHomeIconClickListener(OnHomeIconClickListener clickListener) {
        this.onHomeIconClick = clickListener;
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        RecyclerViewBanner headerViewpager;

        HeaderViewHolder(View itemView) {
            super(itemView);
            headerViewpager = (RecyclerViewBanner) itemView.findViewById(R.id.vp_home_header);
        }
    }

    public class YouLikeViewHolder extends RecyclerView.ViewHolder {
        HorizontalScrollListView goodshotItem;

        YouLikeViewHolder(View itemView) {
            super(itemView);

            goodshotItem = itemView.findViewById(R.id.gv_home_goods);

        }
    }

    public class GoodChoseHolder extends RecyclerView.ViewHolder {
        IconGridView goodshotItem;

        GoodChoseHolder(View itemView) {
            super(itemView);

            goodshotItem = itemView.findViewById(R.id.gv_home_goods);

        }
    }

    public class IconViewHolder extends RecyclerView.ViewHolder {
        TextView icon_all, icon_car, icon_charge, icon_service;
        ViewFlipper tv_marquee;

        IconViewHolder(View itemView) {
            super(itemView);
            icon_all = (TextView) itemView.findViewById(R.id.tv_home_icon_all);
            icon_car = (TextView) itemView.findViewById(R.id.tv_home_icon_car);
            icon_charge = (TextView) itemView.findViewById(R.id.tv_home_icon_charge);
            icon_service = (TextView) itemView.findViewById(R.id.tv_home_icon_service);
            tv_marquee = (ViewFlipper) itemView.findViewById(R.id.tv_marquee);
        }
    }

    public class GoodQualityBottomHolder extends RecyclerView.ViewHolder {
        ImageView icon_pic1, icon_pic2, icon_pic3;
        TextView icon_title1, icon_title2, icon_title3;
        TextView tv_good_price_text1, tv_good_price_text2, tv_good_price_text3;
        TextView tv_content_text1, tv_content_text2, tv_content_text3;
        RelativeLayout left_rl, right_rlt, right_rlb;

        GoodQualityBottomHolder(View itemView) {
            super(itemView);
            icon_pic1 = itemView.findViewById(R.id.goodQuality_bottom_left_img);
            icon_pic2 = itemView.findViewById(R.id.goodQuality_bottom_right_timg);
            icon_pic3 = itemView.findViewById(R.id.goodQuality_bottom_right_bimg);
            tv_content_text1 = itemView.findViewById(R.id.goodQuality_bottom_left_content);
            tv_content_text2 = itemView.findViewById(R.id.goodQuality_bottom_right_tcontent);
            tv_content_text3 = itemView.findViewById(R.id.goodQuality_bottom_right_bcontent);

            tv_good_price_text2 = itemView.findViewById(R.id.goodQuality_bottom_right_tmoney);
            tv_good_price_text3 = itemView.findViewById(R.id.goodQuality_bottom_right_bmoney);
            icon_title1 = itemView.findViewById(R.id.goodQuality_bottom_left_name);
            icon_title2 = itemView.findViewById(R.id.goodQuality_bottom_right_ttitle);
            icon_title3 = itemView.findViewById(R.id.goodQuality_bottom_right_btitle);

            left_rl=itemView.findViewById(R.id.goodQuality_bottom_left_rl);
            right_rlb=itemView.findViewById(R.id.goodQuality_bottom_right_rlb);
            right_rlt=itemView.findViewById(R.id.goodQuality_bottom_right_rlt);


        }
    }

    public class MustBuyViewHolder extends RecyclerView.ViewHolder {
        IconGridView goodshotItem;

        MustBuyViewHolder(View itemView) {
            super(itemView);
            goodshotItem = (IconGridView) itemView.findViewById(R.id.gv_home_goods);

        }
    }

    public class GoodQualityHolder extends RecyclerView.ViewHolder {
        IconGridView goodshotItem;

        GoodQualityHolder(View itemView) {
            super(itemView);
            goodshotItem = (IconGridView) itemView.findViewById(R.id.gv_home_goods);

        }
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {
        IconGridView goodsItem;

        GoodsViewHolder(View itemView) {
            super(itemView);
            goodsItem = (IconGridView) itemView.findViewById(R.id.gv_home_goods);

        }
    }
}
