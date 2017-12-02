package com.willard5991.colorfull;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
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

    private MainActivity mainActivity;
    private TextView testingView;
    private PieChart pieChart;

    public ChartsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        pieChart = (PieChart) getActivity().findViewById(R.id.pie_chart);


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
        //More options just check out the documentation!

        //JENNA COMMENT
        //addDataSet();

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts, container, false);
        testingView = (TextView) view.findViewById(R.id.textViewTest);
        return view;
    }


    private void addDataSet() {
        ArrayList<Color> colors = getUniqueColors();
        ArrayList<PieEntry> yEntrys = getYData(colors);

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Employee Sales");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(0);

        //add colors to dataset
        ArrayList<Integer> integerColors = new ArrayList<>();
        for(Color color: colors)
        {
            integerColors.add(color.toArgb());
        }

        pieDataSet.setColors(integerColors);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public ArrayList<Color> getUniqueColors(){
        ArrayList<Color> colors = new ArrayList<Color>();
        RealmResults<ActivityEntry> activities = mainActivity.realm.where(ActivityEntry.class).findAll();
        for (ActivityEntry activity: activities)
        {
            if(colors.isEmpty())
            {
                colors.add(activity.getColor());
            }
            for(Color colorX: colors)
            {
                if (activity.getColor() != colorX)
                {
                    colors.add(activity.getColor());
                }
            }
        }
        return colors;
    }

    public ArrayList<PieEntry> getYData(ArrayList<Color> colors){
        ArrayList<PieEntry> yData = new ArrayList<PieEntry>();
        RealmResults<ActivityEntry> activities = mainActivity.realm.where(ActivityEntry.class).findAll();
        int sum = 0;
        int i = 0;
        for(Color color: colors)
        {
            for (ActivityEntry activity: activities)
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
