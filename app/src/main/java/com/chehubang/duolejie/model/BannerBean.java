package com.chehubang.duolejie.model;

/**
 * Created by Administrator on 2017/12/17 0017.
 */

public class BannerBean {
    private String  header;
    private String url;
    private String is_jump;
    private int id;

    @Override
    public String toString() {
        return "BannerBean{" +
                "header='" + header + '\'' +
                ", url='" + url + '\'' +
                ", is_jump='" + is_jump + '\'' +
                ", id=" + id +
                '}';
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIs_jump() {
        return is_jump;
    }

    public void setIs_jump(String is_jump) {
        this.is_jump = is_jump;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
