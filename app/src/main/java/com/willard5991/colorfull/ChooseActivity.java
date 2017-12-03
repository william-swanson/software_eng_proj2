package com.willard5991.colorfull;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class ChooseActivity extends AppCompatActivity implements ActivityRecyclerViewAdapter.ItemClickListener{

    private Button submitButton;
    private RecyclerView recyclerView;
    private ActivityRecyclerViewAdapter adapter;
    private ArrayList<ActivityName> data;
    private ActivityName activitySelected;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = realm.getDefaultInstance();
        //Use if we need to clear Realm
        //realm.beginTransaction();
        //realm.deleteAll();
        //realm.commitTransaction();

//        if(realm != null){
//            realm.close();
//            realm.deleteRealm(realm.getConfiguration());
//        }
//        realm = realm.getDefaultInstance();

        BoolFlag bool = realm.where(BoolFlag.class).findFirst();
        if(bool == null){
            //set first open flag
            realm.beginTransaction();
            BoolFlag flag = realm.createObject(BoolFlag.class);
            flag.setFlag(true);
            realm.commitTransaction();
            Log.i("TAG","initial setting flag");

            //Add option for creating custom activityentries
            //Add example activityentries
            realm.beginTransaction();

            ActivityName addActivity = realm.createObject(ActivityName.class);
            addActivity.setName("Add Activity +");
            /*
            ActivityEntry workedOut = realm.createObject(ActivityEntry.class);
            workedOut.setActivityName("Worked Out");
            ActivityEntry running = realm.createObject(ActivityEntry.class);
            running.setActivityName("Running");
            ActivityEntry ateDinner = realm.createObject(ActivityEntry.class);
            ateDinner.setActivityName("Ate Dinner");
            ActivityEntry ateBreakfast = realm.createObject(ActivityEntry.class);
            ateBreakfast.setActivityName("Ate Breakfast");
            */

            realm.commitTransaction();
        } else {
            Log.i("TAG","flag already set");
        }

//        try {
//            RealmResults<BoolFlag> boolRes = realm.where(BoolFlag.class).findAll();
//            Log.i("TAG", "Num entries: " + Integer.toString(boolRes.size()));
//        } catch (RealmMigrationNeededException e){
//            //if does not exist in the database yet
//            realm.beginTransaction();
//            BoolFlag flag = realm.createObject(BoolFlag.class);
//            flag.setFlag(true);
//            realm.commitTransaction();
//        }

        submitButton = (Button) findViewById(R.id.submit_button);

        data = new ArrayList<ActivityName>();

        Intent transitionIntent = getIntent();
        final int colorValue = (int) transitionIntent.getIntExtra("color",0);
        Log.i("TAG", "Color selected: " + Integer.toString(colorValue));

        if(colorValue == -1){
            submitButton.setBackgroundColor(Color.parseColor("#e6e7e8"));
        }
        CoordinatorLayout l = (CoordinatorLayout) findViewById(R.id.back);
        l.setBackgroundColor(colorValue);

//        ActivityEntry addActivity = realm.createObject(ActivityEntry.class);
//        addActivity.setActivityName("Add Activity +");
//        data.add(addActivity);
//
//        fillTestData();
        getAvailableActivities();

        recyclerView = (RecyclerView) findViewById(R.id.actRView);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new ActivityRecyclerViewAdapter(data,colorValue);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);
                int y = c.get(Calendar.YEAR);
                Date today = new Date(d, m, y);

                realm.beginTransaction();
                ActivityEntry newActivity = realm.createObject(ActivityEntry.class);
                newActivity.setActivityName(activitySelected.getName());
                newActivity.setColor(colorValue);
                if(realm.where(ActivityEntry.class).findAllSorted("id").isEmpty()) {
                    newActivity.setId("0");
                }
                else {
                    newActivity.setId(realm.where(ActivityEntry.class).findAllSorted("id").last().getId()+1);
                }


                Log.i("TAG", Integer.toString(d));
                Log.i("TAG", Integer.toString(m));
                Log.i("TAG", Integer.toString(y));
                newActivity.setDay(d);
                newActivity.setMonth(m);
                newActivity.setYear(y);
                realm.commitTransaction();

                realm.close();
                realm.close(); //not sure why, but seems to only run with close() twice
                Intent intent = new Intent(getBaseContext(),AnalysisActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
        activitySelected = data.get(position);
        Log.i("TAG","Color selected: " + activitySelected.getName());

        //If the user selects the "Add Activity +" option
        if(position == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText edittext = new EditText(this.getApplicationContext());
            alert.setMessage("Type a brief (1-3 words) description of your activity:");
            alert.setTitle("Add Activity");

            alert.setView(edittext);

            alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String userInput = edittext.getText().toString();
                    realm.beginTransaction();
                    ActivityName newActivity = realm.createObject(ActivityName.class);
                    newActivity.setName(userInput);
                    realm.commitTransaction();
                    data.add(newActivity);
                    adapter.notifyDataSetChanged();
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //exit the dialog box
                }
            });

            alert.show();
        }
    }

    private void getAvailableActivities(){
        RealmResults<ActivityName> results = realm.where(ActivityName.class).findAll();
        for(ActivityName r : results){
            data.add(r);
        }
    }

    private void fillTestData(){
        /*
        ActivityEntry workedOut = new ActivityEntry();
        workedOut.setActivityName("Worked Out");
        data.add(workedOut);
        ActivityEntry running = new ActivityEntry();
        running.setActivityName("Running");
        data.add(running);
        ActivityEntry ateDinner = new ActivityEntry();
        ateDinner.setActivityName("Ate Dinner");
        data.add(ateDinner);
        ActivityEntry ateBreakfast = new ActivityEntry();
        ateBreakfast.setActivityName("Ate Breakfast");
        data.add(ateBreakfast);
        ActivityEntry ateASnack = new ActivityEntry();
        ateASnack.setActivityName("Ate a Snack");
        data.add(ateASnack);
        ActivityEntry ateLunch = new ActivityEntry();
        ateLunch.setActivityName("Ate Lunch");
        data.add(ateLunch);
        ActivityEntry cooked = new ActivityEntry();
        cooked.setActivityName("Cooked");
        data.add(cooked);
        ActivityEntry sick = new ActivityEntry();
        sick.setActivityName("Sick");
        data.add(sick);
        ActivityEntry nightOut = new ActivityEntry();
        nightOut.setActivityName("Night Out");
        data.add(nightOut);
        ActivityEntry watchAMovie = new ActivityEntry();
        watchAMovie.setActivityName("Watch a Movie");
        data.add(watchAMovie);
        ActivityEntry sawAShow = new ActivityEntry();
        sawAShow.setActivityName("Saw a Show");
        data.add(sawAShow);
        ActivityEntry wentShopping = new ActivityEntry();
        wentShopping.setActivityName("Went Shopping");
        data.add(wentShopping);
        ActivityEntry groceryStore = new ActivityEntry();
        groceryStore.setActivityName("Grocery Store");
        data.add(groceryStore);
        ActivityEntry lateToWork = new ActivityEntry();
        lateToWork.setActivityName("Late to Work");
        data.add(lateToWork);
        ActivityEntry meeting = new ActivityEntry();
        meeting.setActivityName("Meeting");
        data.add(meeting);


        for(int i = 0; i<10; i++){
            ActivityEntry newAct = new ActivityEntry();
            newAct.setActivityName("Activity"+Integer.toString(i));
            data.add(newAct);
        }
        */

        return;
    }

}
