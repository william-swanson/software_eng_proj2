package com.willard5991.colorfull;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by mitch on 12/3/2017.
 */

public class MyApplication extends Application {
    public Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("myrealm.realm")
                .build();
        Realm.setDefaultConfiguration(config);
        realm = realm.getDefaultInstance();
    }


    @Override
    public void onTerminate(){
        super.onTerminate();
        realm.close();
    }
}
