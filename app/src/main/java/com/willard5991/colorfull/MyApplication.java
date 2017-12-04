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
        //UNCOMMENT TO ADD TEST DATA
        createTestData();
    }


    @Override
    public void onTerminate(){
        super.onTerminate();
        realm.close();
    }

    public void createTestData(){
        realm.beginTransaction();
        ActivityEntry act1 = realm.createObject(ActivityEntry.class);
        act1.setActivityName("Act1");
        act1.setColor(getResources().getColor(R.color.color1));
        act1.setDay(27);
        act1.setMonth(10);
        act1.setYear(2017);
        ActivityEntry act2 = realm.createObject(ActivityEntry.class);
        act2.setActivityName("Act2");
        act2.setColor(getResources().getColor(R.color.color2));
        act2.setDay(28);
        act2.setMonth(10);
        act2.setYear(2017);
        ActivityEntry act3 = realm.createObject(ActivityEntry.class);
        act3.setActivityName("Act3");
        act3.setColor(getResources().getColor(R.color.color3));
        act3.setDay(28);
        act3.setMonth(10);
        act3.setYear(2017);
        ActivityEntry act4 = realm.createObject(ActivityEntry.class);
        act4.setActivityName("Act4");
        act4.setColor(getResources().getColor(R.color.color4));
        act4.setDay(29);
        act4.setMonth(10);
        act4.setYear(2017);
        ActivityEntry act5 = realm.createObject(ActivityEntry.class);
        act5.setActivityName("Act5");
        act5.setColor(getResources().getColor(R.color.color1));
        act5.setDay(29);
        act5.setMonth(10);
        act5.setYear(2017);
        ActivityEntry act6 = realm.createObject(ActivityEntry.class);
        act6.setActivityName("Act6");
        act6.setColor(getResources().getColor(R.color.color1));
        act6.setDay(29);
        act6.setMonth(10);
        act6.setYear(2017);
        ActivityEntry act7 = realm.createObject(ActivityEntry.class);
        act7.setActivityName("Act7");
        act7.setColor(getResources().getColor(R.color.color5));
        act7.setDay(30);
        act7.setMonth(10);
        act7.setYear(2017);
        ActivityEntry act8 = realm.createObject(ActivityEntry.class);
        act8.setActivityName("Act8");
        act8.setColor(getResources().getColor(R.color.color7));
        act8.setDay(30);
        act8.setMonth(10);
        act8.setYear(2017);
        ActivityEntry act9 = realm.createObject(ActivityEntry.class);
        act9.setActivityName("Act9");
        act9.setColor(getResources().getColor(R.color.color8));
        act9.setDay(30);
        act9.setMonth(10);
        act9.setYear(2017);
        ActivityEntry act10 = realm.createObject(ActivityEntry.class);
        act10.setActivityName("Act10");
        act10.setColor(getResources().getColor(R.color.color8));
        act10.setDay(30);
        act10.setMonth(10);
        act10.setYear(2017);
        ActivityEntry act11 = realm.createObject(ActivityEntry.class);
        act11.setActivityName("Act11");
        act11.setColor(getResources().getColor(R.color.color11));
        act11.setDay(1);
        act11.setMonth(11);
        act11.setYear(2017);
        realm.commitTransaction();

    }
}
