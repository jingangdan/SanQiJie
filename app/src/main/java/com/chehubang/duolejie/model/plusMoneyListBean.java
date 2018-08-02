package com.chehubang.duolejie.model;

import java.io.Serializable;

/**
 * Created by Thinkpad on 2017/12/14.
 */

public class plusMoneyListBean implements Serializable{


    /**
     * good_name : http://192.168.3.25:8080   /GetTreasureAppJinDong/FtpUpload/140.png
     * good_period : 3
     * id : 522860656942
     * nick_name : 张三丰
     */
    private String  value;
    private String type;
    private int id;

    @Override
    public String toString() {
        return "BannerBean{" +"value='" + value + '\'' +", type='" + type + '\''  +", id=" + id +'}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
