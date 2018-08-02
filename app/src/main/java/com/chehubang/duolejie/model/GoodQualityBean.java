package com.chehubang.duolejie.model;

public class GoodQualityBean {
    private String good_header,good_name,good_price,content,id;

    @Override
    public String toString() {
        return "GoodQualityBean{" +
                "good_header='" + good_header + '\'' +
                ", good_name='" + good_name + '\'' +
                ", good_price='" + good_price + '\'' +
                ", content='" + content + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getGood_header() {
        return good_header;
    }

    public void setGood_header(String good_header) {
        this.good_header = good_header;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getGood_price() {
        return good_price;
    }

    public void setGood_price(String good_price) {
        this.good_price = good_price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
