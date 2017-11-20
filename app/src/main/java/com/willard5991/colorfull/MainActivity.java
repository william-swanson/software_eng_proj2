package com.willard5991.colorfull;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements ColorRecyclerViewAdapter.ItemClickListener {

    ColorRecyclerViewAdapter adapter;
    Context context = this;
    int[] data;
    Button submitButton;
    RecyclerView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // data to populate the RecyclerView with
        //String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35"};
        data = context.getResources().getIntArray(R.array.colorPicker);
        submitButton = (Button) findViewById(R.id.submit_button);
        myView = (RecyclerView) findViewById(R.id.rvColors);

        // set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvColors);
        int numberOfColumns = 5;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new ColorRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
        //GradientDrawable background = (GradientDrawable) submitButton.getBackground();
        //background.setColor(data[position]);
        //submitButton.setBackgroundResource(R.color.colorAccent);
    }



}
