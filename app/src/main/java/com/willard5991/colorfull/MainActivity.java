package com.willard5991.colorfull;

import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements ColorRecyclerViewAdapter.ItemClickListener {

    ColorRecyclerViewAdapter adapter;
    Context context = this;
    int[] data;
    Button submitButton;
    RecyclerView myView;
    ImageButton xOut;
    int position;
    public Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     

        // data to populate the RecyclerView with
        data = context.getResources().getIntArray(R.array.colorPicker);
        submitButton = (Button) findViewById(R.id.submit_button);
        myView = (RecyclerView) findViewById(R.id.rvColors);
        xOut = (ImageButton) findViewById(R.id.x_button);

        // set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvColors);
        int numberOfColumns = 5;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new ColorRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        xOut.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(view.getContext(), AnalysisActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClick(View view, int colorPosition) {
        position = colorPosition;

        //if black button, button text white
        if(position == 34) {
            submitButton.setTextColor(Color.parseColor("#ffffff"));
        }
        else {
            submitButton.setTextColor(Color.parseColor("#000000"));
        }
        submitButton.setBackgroundColor(data[position]);

        submitButton.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(view.getContext(), ChooseActivity.class);
                intent.putExtra("color", data[position]);

                startActivity(intent);
            }
        });
    }



}
