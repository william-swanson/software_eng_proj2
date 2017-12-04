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
import android.widget.ImageButton;
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
    public MyApplication myApp;
    private ImageButton xOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myApp = (MyApplication) this.getApplication();

//        Uncomment if we need to clear Realm
//        myApp.realm.beginTransaction();
//        myApp.realm.deleteAll();
//        myApp.realm.commitTransaction();
//
//        if(myApp.realm != null){
//            myApp.realm.close();
//            myApp.realm.deleteRealm(myApp.realm.getConfiguration());
//        }
//        myApp.realm = myApp.realm.getDefaultInstance();

        BoolFlag bool = myApp.realm.where(BoolFlag.class).findFirst();
        if(bool == null){
            //set first open flag
            myApp.realm.beginTransaction();
            BoolFlag flag = myApp.realm.createObject(BoolFlag.class);
            flag.setFlag(true);
            myApp.realm.commitTransaction();
            Log.i("TAG","initial setting flag");

            //Add option for creating custom activityentries
            //Add example activityentries
            myApp.realm.beginTransaction();

            ActivityName addActivity = myApp.realm.createObject(ActivityName.class);
            addActivity.setName("Add Activity +");

            ActivityName workedOut = myApp.realm.createObject(ActivityName.class);
            workedOut.setName("Worked Out");
            ActivityName running = myApp.realm.createObject(ActivityName.class);
            running.setName("Running");
            ActivityName ateDinner = myApp.realm.createObject(ActivityName.class);
            ateDinner.setName("Ate Dinner");
            ActivityName ateBreakfast = myApp.realm.createObject(ActivityName.class);
            ateBreakfast.setName("Ate Breakfast");

            myApp.realm.commitTransaction();

        }


        submitButton = (Button) findViewById(R.id.submit_button);
        xOut = (ImageButton) findViewById(R.id.x_button);

        xOut.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(view.getContext(), AnalysisActivity.class);
                startActivity(intent);
            }
        });

        data = new ArrayList<ActivityName>();

        Intent transitionIntent = getIntent();
        final int colorValue = (int) transitionIntent.getIntExtra("color",0);

        if(colorValue == -1){
            submitButton.setBackgroundColor(Color.parseColor("#e6e7e8"));
        }
        if(colorValue == -16777216){
            xOut.setBackgroundResource(R.drawable.exit_icon);
        }
        CoordinatorLayout l = (CoordinatorLayout) findViewById(R.id.back);
        l.setBackgroundColor(colorValue);

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

                if(activitySelected == null){
                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    alert.setMessage("Please select an activity before proceeding.");
                    alert.setTitle("No Activity Selected");

                    alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });

                    alert.show();

                } else {

                    Calendar c = Calendar.getInstance();
                    int m = c.get(Calendar.MONTH);
                    int d = c.get(Calendar.DAY_OF_MONTH);
                    int y = c.get(Calendar.YEAR);

                    myApp.realm.beginTransaction();
                    ActivityEntry newActivity = myApp.realm.createObject(ActivityEntry.class);
                    newActivity.setActivityName(activitySelected.getName());
                    newActivity.setColor(colorValue);
                    if (myApp.realm.where(ActivityEntry.class).findAllSorted("id").isEmpty()) {
                        newActivity.setId("0");
                    } else {
                        newActivity.setId(myApp.realm.where(ActivityEntry.class).findAllSorted("id").last().getId() + 1);
                    }
                    newActivity.setDay(d);
                    newActivity.setMonth(m);
                    newActivity.setYear(y);
                    myApp.realm.commitTransaction();

                    Intent intent = new Intent(getBaseContext(), AnalysisActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        activitySelected = data.get(position);

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
                    myApp.realm.beginTransaction();
                    ActivityName newActivity = myApp.realm.createObject(ActivityName.class);
                    newActivity.setName(userInput);
                    myApp.realm.commitTransaction();
                    data.add(newActivity);
                    adapter.notifyDataSetChanged();
                    activitySelected = data.get(data.size()-1);
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
        RealmResults<ActivityName> results = myApp.realm.where(ActivityName.class).findAll();
        for(ActivityName r : results){
            data.add(r);
        }
    }

}
