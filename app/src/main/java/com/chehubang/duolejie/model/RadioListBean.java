package com.chehubang.duolejie.model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Thinkpad on 2017/12/14.
 */

public class RadioListBean implements Serializable{


    /**
     * good_name : http://192.168.3.25:8080   /GetTreasureAppJinDong/FtpUpload/140.png
     * good_period : 3
     * id : 522860656942
     * nick_name : 张三丰
     */

    private String coupon_img;
    private String id;
    private String period;
    private String coupon_num;
    private String nick_name;
    private String user_header;
    private String  user_ip_address,create_time;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUser_ip_address() {
        return user_ip_address;
    }

    public void setUser_ip_address(String user_ip_address) {
        this.user_ip_address = user_ip_address;
    }

    public String getUser_header() {
        return user_header;
    }

    public void setUser_header(String user_header) {
        this.user_header = user_header;
    }

    @Override
    public String toString() {
        return "RadioListBean{" +
                "coupon_img='" + coupon_img + '\'' +
                ", id='" + id + '\'' +
                ", period='" + period + '\'' +
                ", coupon_num='" + coupon_num + '\'' +
                ", nick_name='" + nick_name + '\'' +
                '}';
    }

    public String getCoupon_num() {
        return coupon_num;
    }

    public void setCoupon_num(String coupon_num) {
        this.coupon_num = coupon_num;
    }

    public String getCoupon_img() {
        return coupon_img;
    }

    public void setCoupon_img(String coupon_img) {
        this.coupon_img = coupon_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id

                = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
