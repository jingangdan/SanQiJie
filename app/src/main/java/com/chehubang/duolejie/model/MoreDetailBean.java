package com.chehubang.duolejie.model;

/**
 * 账单
 *
 */
public class MoreDetailBean {
    private String type;
    private String money;
    private String create_time;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "MoreDetailBean{" +
                "type='" + type + '\'' +
                ", money='" + money + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
