package com.chehubang.duolejie.NearbyMerchants;

import java.util.List;

/**
 * 附近商户-商户信息（实体类）
 * Created by jingang on 2018/7/20.
 */

public class LineBrand {

    private List<LineBrandListBean> lineBrandList;

    public List<LineBrandListBean> getLineBrandList() {
        return lineBrandList;
    }

    public void setLineBrandList(List<LineBrandListBean> lineBrandList) {
        this.lineBrandList = lineBrandList;
    }

    public static class LineBrandListBean {
        /**
         * id : 654332
         * distance : 1.854
         * star_num : 5
         * consume_num : 0
         * brand_address : 山东省临沂市兰山区银雀山街道北辰教育(临沂校区)金鼎国际广场
         * brand_type : 休闲娱乐
         * longitude : null
         * latitude : null
         * brand_header : http://39.107.14.118/upload/19cac1532056721550.jpg
         * brand_name : 旋转小火锅
         */

        private String id;
        private double distance;
        private String star_num;
        private String consume_num;
        private String brand_address;
        private String brand_type;
        private Object longitude;
        private Object latitude;
        private String brand_header;
        private String brand_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getStar_num() {
            return star_num;
        }

        public void setStar_num(String star_num) {
            this.star_num = star_num;
        }

        public String getConsume_num() {
            return consume_num;
        }

        public void setConsume_num(String consume_num) {
            this.consume_num = consume_num;
        }

        public String getBrand_address() {
            return brand_address;
        }

        public void setBrand_address(String brand_address) {
            this.brand_address = brand_address;
        }

        public String getBrand_type() {
            return brand_type;
        }

        public void setBrand_type(String brand_type) {
            this.brand_type = brand_type;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

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
