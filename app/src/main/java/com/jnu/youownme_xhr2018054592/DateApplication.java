package com.jnu.youownme_xhr2018054592;

import java.util.HashMap;

import android.app.Application;

public class DateApplication extends Application{

    private int year;
    private int month;
    private int day;

    private static DateApplication instance=null;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public static DateApplication getInstance(){
        return instance;
    }

    public DateApplication() {
        super();
    }

    public DateApplication(int year,int month,int day) {
        super();
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        this.instance=this;
    }

}