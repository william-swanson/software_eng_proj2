package com.willard5991.colorfull;

import io.realm.RealmObject;

/**
 * Created by willard5991 on 11/30/2017.
 */

public class Date extends RealmObject{

    private int day;
    private int month;
    private int year;

    public Date(){
        this.day = 1;
        this.month = 1;
        this.year = 1;
    }
    public Date(int d, int m, int y){
        this.day = d;
        this.month = m;
        this.year = y;
    }
    public int getDay(){ return this.day;}
    public void setDay(int d){
        this.day = d;
        return;
    }
    public int getMonth(){ return this.month;}
    public void setMonth(int m){
        this.month = m;
        return;
    }
    public int getYear(){ return this.year;}
    public void setYear(int y){
        this.year = y;
        return;
    }
}

