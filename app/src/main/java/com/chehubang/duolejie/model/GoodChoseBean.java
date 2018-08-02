package com.chehubang.duolejie.model;

public class GoodChoseBean {
    private String goods_service,good_header,good_name,good_price,id;

    @Override
    public String toString() {
        return "GoodChoseBean{" +
                "goods_service='" + goods_service + '\'' +
                ", good_header='" + good_header + '\'' +
                ", good_name='" + good_name + '\'' +
                ", good_price='" + good_price + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getGoods_service() {
        return goods_service;
    }

    public void setGoods_service(String goods_service) {
        this.goods_service = goods_service;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
