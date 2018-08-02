package com.chehubang.duolejie.model;

import java.util.List;

/**
 * Created by asus on 2018/7/30.
 */

public class PlusMoney {

    /**
     * advertisementList : [{"id":122,"is_jump":"n","header":"http://39.107.14.118/upload/e1abf1532762250.jpg","url":"1"}]
     * return_money : plus会员即得15%的消费鼓励金
     * plusMoneyList : [{"id":76,"type":"plusMoney_500","value":"500"},{"id":77,"type":"plusMoney_1000","value":"1000"},{"id":78,"value":"2000","type":"plusMoney_2000"},{"value":"5000","id":79,"type":"plusMoney_5000"},{"id":80,"value":"10000","type":"plusMoney_10000"}]
     */

    private String return_money;
    private List<AdvertisementListBean> advertisementList;
    private List<PlusMoneyListBean> plusMoneyList;

    public String getReturn_money() {
        return return_money;
    }

    public void setReturn_money(String return_money) {
        this.return_money = return_money;
    }

    public List<AdvertisementListBean> getAdvertisementList() {
        return advertisementList;
    }

    public void setAdvertisementList(List<AdvertisementListBean> advertisementList) {
        this.advertisementList = advertisementList;
    }

    public List<PlusMoneyListBean> getPlusMoneyList() {
        return plusMoneyList;
    }

    public void setPlusMoneyList(List<PlusMoneyListBean> plusMoneyList) {
        this.plusMoneyList = plusMoneyList;
    }

    public static class AdvertisementListBean {
        /**
         * id : 122
         * is_jump : n
         * header : http://39.107.14.118/upload/e1abf1532762250.jpg
         * url : 1
         */

        private String id;
        private String is_jump;
        private String header;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIs_jump() {
            return is_jump;
        }

        public void setIs_jump(String is_jump) {
            this.is_jump = is_jump;
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
    }

    public static class PlusMoneyListBean {
        /**
         * id : 76
         * type : plusMoney_500
         * value : 500
         */

        private String id;
        private String type;
        private String value;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
