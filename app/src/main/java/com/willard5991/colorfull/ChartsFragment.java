package com.willard5991.colorfull;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView testingView;
    private PieChart pieChart;

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
        //pieChart.setHoleColor(Color.BLUE);
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Colors");
        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);

        addDataSet();
    /*
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos1 = e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1 + 7);

                Toast.makeText(getActivity(), "Employee ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        */
        return view;
    }


    private void addDataSet() {
        ArrayList<ActivityEntry> colors = getUniqueColors();
        ArrayList<PieEntry> yEntrys = getYData(colors);

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Colors");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(0);

        //add colors to dataset
        ArrayList<Integer> integerColors = new ArrayList<Integer>();
        for(ActivityEntry color: colors)
        {
            integerColors.add(color.getColor());
        }

        pieDataSet.setColors(integerColors);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public ArrayList<ActivityEntry> getUniqueColors(){
        ArrayList<ActivityEntry> colors = new ArrayList<ActivityEntry>();
        RealmResults<ActivityEntry> activities = analysisActivity.myApp.realm.where(ActivityEntry.class).findAll();
        ArrayList<ActivityEntry> activities2 = new ArrayList<ActivityEntry>();
        String choice = analysisActivity.filterSpinner.getSelectedItem().toString();

        if(!choice.equals("All"))
        {
            for(ActivityEntry activity: activities)
            {
                if(activity.getActivityName().equals(choice));
                {
                    activities2.add(activity);
                }
            }
        }
        else
        {
            for(ActivityEntry activity: activities)
            {
                activities2.add(activity);
            }
        }

        for (ActivityEntry activity: activities2)
        {
            if(colors.isEmpty())
            {
                colors.add(activity);
            }
            if(!colors.contains(activity.getColor()))
            {
                colors.add(activity);

            }
        }
        return colors;
    }

    public ArrayList<PieEntry> getYData(ArrayList<ActivityEntry> colors){
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
                if(activity.getActivityName().equals(choice));
                {
                    activities2.add(activity);
                }
            }
        }
        else
        {
            for(ActivityEntry activity: activities)
            {
                activities2.add(activity);
            }
        }

        for(ActivityEntry color: colors)
        {
            for (ActivityEntry activity: activities2)
            {
                if (activity.getColor() == color.getColor())
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
