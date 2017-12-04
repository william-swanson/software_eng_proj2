package com.willard5991.colorfull;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartsFragment extends Fragment {

    private AnalysisActivity analysisActivity;
    private PieChart pieChart;
    private Spinner dateSpinner;
    private PieData pieData;

    public ChartsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts, container, false);
        analysisActivity = (AnalysisActivity) this.getActivity();

        pieChart = (PieChart) view.findViewById(R.id.pie_chart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(0f);
        pieChart.setTransparentCircleAlpha(0);

        //NOTE: This section is not implemented in the filtering, it just shows how we would do the spinner for the filter
        //Begin "version 2" spinner
        dateSpinner = (Spinner) view.findViewById(R.id.date_spinner);
        ArrayList<String> dateList = new ArrayList<String>();
        dateList.add("All");
        dateList.add("Week");
        dateList.add("Month");
        dateList.add("Year");

        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(analysisActivity.getApplicationContext(), android.R.layout.simple_spinner_item, dateList);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(dateAdapter);
        dateSpinner.setSelection(0);

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addDataSet();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //End Version 2 spinner

        analysisActivity.filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                addDataSet();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos1 = e.toString().indexOf("(sum): ");
                String total = e.toString().substring(pos1 + 7);

                Toast.makeText(getActivity(), "Total: " + total, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


        return view;
    }


    private void addDataSet() {
        ArrayList<Integer> colors = getUniqueColors();
        ArrayList<PieEntry> yEntrys = getYData(colors);

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Colors");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(0);
        pieDataSet.setColors(colors);

        //create pie data object
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieData.notifyDataChanged();
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }

    public ArrayList<Integer> getUniqueColors(){
        ArrayList<Integer> colors = new ArrayList<Integer>();
        RealmResults<ActivityEntry> activities = analysisActivity.myApp.realm.where(ActivityEntry.class).findAll();
        ArrayList<ActivityEntry> activities2 = new ArrayList<ActivityEntry>();
        String choice = analysisActivity.filterSpinner.getSelectedItem().toString();

        if(!choice.equals("All"))
        {
            for(ActivityEntry activity: activities)
            {
                if(activity.getActivityName().equals(choice))
                {
                    activities2.add(activity);
                }
            }
        }
        else
        {
           activities2.addAll(activities);
        }

        for (ActivityEntry activity: activities2)
        {
            if(colors.isEmpty())
            {
                colors.add(activity.getColor());
            }
            if(!colors.contains(activity.getColor()))
            {
                colors.add(activity.getColor());

            }
        }
        return colors;
    }

    public ArrayList<PieEntry> getYData(ArrayList<Integer> colors){
        ArrayList<PieEntry> yData = new ArrayList<PieEntry>();
        RealmResults<ActivityEntry> activities = analysisActivity.myApp.realm.where(ActivityEntry.class).findAll();
        int sum = 0;
        int i = 0;
        ArrayList<ActivityEntry> activities2 = new ArrayList<ActivityEntry>();
        String choice = analysisActivity.filterSpinner.getSelectedItem().toString();

        if(!choice.equals("All"))
        {
            for(ActivityEntry activity: activities)
            {
                if(activity.getActivityName().equals(choice))
                {
                    activities2.add(activity);
                }
            }
        }
        else
        {
            activities2.addAll(activities);
        }

        for(Integer color: colors)
        {
            for (ActivityEntry activity: activities2)
            {
                if (activity.getColor() == color)
                {
                    sum ++;
                }
            }
            yData.add(new PieEntry(sum , i));
            i++;
            sum =0;
        }
        return yData;
    }

}
