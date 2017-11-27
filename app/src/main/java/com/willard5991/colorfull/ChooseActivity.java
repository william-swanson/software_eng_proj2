package com.willard5991.colorfull;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ChooseActivity extends AppCompatActivity implements ActivityRecyclerViewAdapter.ItemClickListener{

    Button submitButton;
    RecyclerView recyclerView;
    ActivityRecyclerViewAdapter adapter;
    ArrayList<ActivityEntry> data;
    ActivityEntry activitySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        submitButton = (Button) findViewById(R.id.submit_button);

        data = new ArrayList<ActivityEntry>();

        Intent transitionIntent = getIntent();
        final int colorValue = (int) transitionIntent.getIntExtra("color",0);
        Log.i("TAG", "Color selected: " + Integer.toString(colorValue));

        ConstraintLayout v = (ConstraintLayout) findViewById(R.id.screen);
//        View root = v.getRootView();
        CoordinatorLayout l = (CoordinatorLayout) findViewById(R.id.back);
        l.setBackgroundColor(colorValue);

        //TODO: do we want to make the activity options and/or the submit button the same color what was chosen on the previous screen?
        //submitButton.setBackgroundColor(colorValue);

        Color chosenColor = Color.valueOf(colorValue);

        //Test data:
        ActivityEntry addActivity = new ActivityEntry();
        addActivity.setDate(new Date(100));
        addActivity.setActivityName("Add Activity +");
        addActivity.setTime(new Time(0));
        addActivity.setColor(chosenColor);
        data.add(addActivity);
        for(int i = 0; i<10; i++){
            ActivityEntry newActivity = new ActivityEntry();
            newActivity.setActivityName("Activity" + Integer.toString(i));
            data.add(newActivity);
        }

        recyclerView = (RecyclerView) findViewById(R.id.actRView);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new ActivityRecyclerViewAdapter(data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ChooseActivity.class);
                intent.putExtra("color",colorValue);
                startActivity(intent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getBaseContext(),AnalysisActivity.class);
                //startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
        //GradientDrawable background = (GradientDrawable) submitButton.getBackground();
        //background.setColor(data[position]);
        activitySelected = data.get(position);
        Log.i("TAG","Color selected: " + activitySelected.getActivityName());
    }

}
