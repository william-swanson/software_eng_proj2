package com.willard5991.colorfull;

import io.realm.RealmObject;

import io.realm.RealmModel;

/**
 * Created by willard5991 on 11/15/2017.
 */

public class ActivityEntry extends RealmObject{

    private String id;
    //private Date date;
    private String activityName;
    private int color;
    private int day;
    private int month;
    private int year;


    public ActivityEntry(){
        this.day = 1;
        this.month = 0;
        this.year = 2017;
        this.activityName = new String();
        this.color = -1; //set to white
    }

    public ActivityEntry(int d, int m, int y, String n, int c){
        this.day = d;
        this.month = m;
        this.year = y;
        this.activityName = n;
        this.color = c;
    }

    public String getId() { return this.id; }
    public void setId(String i) { this.id = i; }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getActivityName(){
        return this.activityName;
    }
    public void setActivityName(String a){
        this.activityName = a;
    }

    public int getColor(){
        return this.color;
    }
    public void setColor(int c){
        this.color = c;
    }



}
