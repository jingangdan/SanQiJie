package com.chehubang.duolejie.model;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class MoneyHistoryBean {

    //提现时间
    private String create_time;

    //提现金额

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getGet_money() {
        return get_money;
    }

    public void setGet_money(String get_money) {
        this.get_money = get_money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String get_money;

    //提现时间
    private String status;


}
