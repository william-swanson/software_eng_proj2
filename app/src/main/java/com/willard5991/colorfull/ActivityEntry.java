package com.willard5991.colorfull;

import android.graphics.Color;

import java.sql.Time;
import java.util.Date;

/**
 * Created by willard5991 on 11/15/2017.
 */

public class ActivityEntry {

    private int id;
    private Date date;
    private Time time;
    private String activityName;
    private Color color;

    public ActivityEntry(){
        this.date = new Date();
        this.time = new Time(0); //no empty constructor, apparently
        this.activityName = new String();
        this.color = new Color();
    }

    public ActivityEntry(Date d, Time t, String a, Color c){
        this.date = d;
        this.time = t;
        this.activityName = a;
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

    public Time getTime(){
        return this.time;
    }
    public void setTime(Time t){
        this.time = t;
    }

    public String getActivityName(){
        return this.activityName;
    }
    public void setActivityName(String a){
        this.activityName = a;
    }

    public Color getColor(){
        return this.color;
    }
    public void setColor(Color c){
        this.color = c;
    }

}
