package com.willard5991.colorfull;

import io.realm.RealmObject;

/**
 * Created by jennapfingsten on 12/2/17.
 */

public class ActivityName extends RealmObject{
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
