package com.jnu.youownme_xhr2018054592.dataprocessor;

import java.io.Serializable;

public class Accept implements Serializable {
    private String name;
    private int year;
    private int month;
    private int day;
    private int money;
    private String why;

    public Accept(String name, int year, int month, int day, int money, String why) {
        this.name=name;
        this.year=year;
        this.month=month;
        this.day=day;
        this.money=money;
        this.why=why;
    }

    public String getName() {
        return name;
    }
    public void setName(String YouOwnMe_name) {this.name = YouOwnMe_name;}

    public int getYear() {
        return year;
    }
    public void setYear(int YouOwnMe_year) {this.year = YouOwnMe_year;}

    public int getMonth() {
        return month;
    }
    public void setMonth(int YouOwnMe_month) {this.month = YouOwnMe_month;}

    public int getDay() {
        return day;
    }
    public void setDay(int YouOwnMe_day) {this.day = YouOwnMe_day;}

    public int getMoney() {
        return money;
    }
    public void setMoney(int YouOwnMe_money) {this.money = YouOwnMe_money;}

    public String getWhy() {
        return why;
    }
    public void setWhy(String YouOwnMe_why) {this.why = YouOwnMe_why;}

}
