package com.willard5991.colorfull;

import io.realm.RealmObject;

/**
 * Created by willard5991 on 11/15/2017.
 */

public class ActivityEntry extends RealmObject{

    private int id;
    private Date date;
    private long time;
    private String activityName;
    private int color;


    public ActivityEntry(){
        this.date = new Date();
        this.time = 0;
        this.activityName = new String();
        this.color = -1; //set to white
    }

    public ActivityEntry(Date d, long t, String n, int c){
        this.date = d;
        this.time = t;
        this.activityName = n;
        this.color = c;
    }

    public int getId() { return this.id; }
    public void setId(int i) { this.id = i; }

    public Date getDate(){
        return this.date;
    }
    public void setDate(Date d){
        this.date = d;
    }

    public long getTime(){
        return this.time;
    }
    public void setTime(long t){
        this.time = t;
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
