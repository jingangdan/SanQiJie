package com.chehubang.duolejie.model;

import java.util.List;

/**
 * 我的-我的推荐人（实体类）
 * Created by jingang on 2018/7/18.
 */

public class Recommend {


    private List<UserRecommendListBean> userRecommendList;

    public List<UserRecommendListBean> getUserRecommendList() {
        return userRecommendList;
    }

    public void setUserRecommendList(List<UserRecommendListBean> userRecommendList) {
        this.userRecommendList = userRecommendList;
    }

    public static class UserRecommendListBean {
        /**
         * nick_name : 多乐乐
         * returnMoney : 0.0
         * user_id : 11692
         * user_header : http://39.107.14.118/upload/userheader.png
         */

        private String nick_name;
        private String returnMoney;
        private String user_id;
        private String user_header;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getReturnMoney() {
            return returnMoney;
        }

        public void setReturnMoney(String returnMoney) {
            this.returnMoney = returnMoney;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_header() {
            return user_header;
        }

        public void setUser_header(String user_header) {
            this.user_header = user_header;
        }
    }
}
