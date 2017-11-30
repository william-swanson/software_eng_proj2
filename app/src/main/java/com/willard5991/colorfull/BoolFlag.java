package com.willard5991.colorfull;

import io.realm.RealmObject;

/**
 * Created by willard5991 on 11/29/2017.
 */

public class BoolFlag extends RealmObject{

    //flag for if the app has been opened before
    private boolean flag;

    public BoolFlag(){
        this.flag = false;
    }

    public boolean getFlag(){
        return this.flag;
    }

    public void setFlag(boolean f){
        this.flag = f;
    }

}
