package com.chehubang.duolejie.model;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class userMoneyBean {

    //"用户主键ID"
    private String user_id;

    //"时间"
    private String create_time;

    //"状态"
    private String status;

    //可用现金
    private double money;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    //"已使用金额"
    private String out_money;

    //"用户余额列表主键ID"
    private String id;

    //"收入金额"
    private String in_money;

    private boolean selectd=false;

    public boolean isSelectd() {
        return selectd;
    }

    public void setSelectd(boolean selectd) {
        this.selectd = selectd;
    }

    public boolean getSelectd() {
        return selectd;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOut_money() {
        return out_money;
    }

    public void setOut_money(String out_money) {
        this.out_money = out_money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIn_money() {
        return in_money;
    }

    public void setIn_money(String in_money) {
        this.in_money = in_money;
    }
}
