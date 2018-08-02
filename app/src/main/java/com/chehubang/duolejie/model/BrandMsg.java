package com.chehubang.duolejie.model;

/**
 * 商家信息（实体类）
 * Created by jingang on 2018/7/18.
 */

public class BrandMsg {

    /**
     * goodsBrandMap : {"brand_header":"http://39.107.14.118/upload/2918b1531898613537.jpg","brand_name":"临沂老店了"}
     */

    private GoodsBrandMapBean goodsBrandMap;

    public GoodsBrandMapBean getGoodsBrandMap() {
        return goodsBrandMap;
    }

    public void setGoodsBrandMap(GoodsBrandMapBean goodsBrandMap) {
        this.goodsBrandMap = goodsBrandMap;
    }

    public static class GoodsBrandMapBean {
        /**
         * brand_header : http://39.107.14.118/upload/2918b1531898613537.jpg
         * brand_name : 临沂老店了
         */

        private String brand_header;
        private String brand_name;

        public String getBrand_header() {
            return brand_header;
        }

        public void setBrand_header(String brand_header) {
            this.brand_header = brand_header;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }
    }
}
