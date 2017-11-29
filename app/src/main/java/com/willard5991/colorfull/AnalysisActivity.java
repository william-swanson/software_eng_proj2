package com.willard5991.colorfull;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import io.realm.RealmResults;

public class AnalysisActivity extends AppCompatActivity {

    private Spinner filterSpinner;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Calendar"));
        tabLayout.addTab(tabLayout.newTab().setText("Charts"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab){

            }
        });

        filterSpinner = (Spinner) findViewById(R.id.filter_spinner);

        ArrayList<String> arrayList = getUniqueActivities();

        ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(stringAdapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public ArrayList<String> getUniqueActivities(){
        ArrayList<String> activityNames = new ArrayList<String>();
        RealmResults<ActivityEntry> activities = mainActivity.realm.where(ActivityEntry.class).findAll();
        activityNames.add("All");
        for (ActivityEntry activity: activities)
        {
            for(String activityNameX: activityNames)
            {
                if (!activity.getActivityName().equals(activityNameX))
                {
                    activityNames.add(activity.getActivityName());
                }
            }
        }
        return activityNames;
    }


}
