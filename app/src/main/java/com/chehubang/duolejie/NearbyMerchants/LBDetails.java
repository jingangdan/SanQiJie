package com.chehubang.duolejie.NearbyMerchants;

import java.util.List;

/**
 * Created by asus on 2018/7/25.
 */

public class LBDetails {

    private List<LinebrandListBean> linebrandList;

    public List<LinebrandListBean> getLinebrandList() {
        return linebrandList;
    }

    public void setLinebrandList(List<LinebrandListBean> linebrandList) {
        this.linebrandList = linebrandList;
    }

    public static class LinebrandListBean {
        /**
         * id : 654332
         * brand_address : 山东省临沂市兰山区银雀山街道北辰教育(临沂校区)金鼎国际广场
         * brand_tel : 13522136973
         * guide_img :
         * brand_name : 旋转小火锅
         * brand_introduction : 小火锅
         */

        private int id;
        private String brand_address;
        private String brand_tel;
        private String guide_img;
        private String brand_name;
        private String brand_introduction;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBrand_address() {
            return brand_address;
        }

        public void setBrand_address(String brand_address) {
            this.brand_address = brand_address;
        }

        public String getBrand_tel() {
            return brand_tel;
        }

        public void setBrand_tel(String brand_tel) {
            this.brand_tel = brand_tel;
        }

        public String getGuide_img() {
            return guide_img;
        }

        public void setGuide_img(String guide_img) {
            this.guide_img = guide_img;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getBrand_introduction() {
            return brand_introduction;
        }

        public void setBrand_introduction(String brand_introduction) {
            this.brand_introduction = brand_introduction;
        }
    }
}
