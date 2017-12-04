package com.willard5991.colorfull;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.realm.RealmResults;


import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment implements OnClickListener {

    private AnalysisActivity analysisActivity;
    private TextView currentMonth;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    private int month, year;
    private static final String dateTemplate = "MMMM yyyy";
    String flag ="abc";
    String date_month_year;
    //private ActivityEntry activity1;
    RealmResults<ActivityEntry> allActivities;
    private ArrayList<ActivityEntry> todaysActivities;

    public CalendarFragment() {
        // Required empty public constructor
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);


        prevMonth = (ImageView) view.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);

        currentMonth = (TextView) view.findViewById(R.id.currentMonth);
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));

        nextMonth = (ImageView) view.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);

        calendarView = (GridView) view.findViewById(R.id.calendar);

        // Initialised
        //CHANGED from getApplicationContext
        adapter = new GridCellAdapter(getContext(), R.id.calendar_day_gridcell, month, year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

        analysisActivity = (AnalysisActivity) this.getActivity();
        allActivities = analysisActivity.realm.where(ActivityEntry.class).findAll();
        

        /*
        //this is how you add test data
        analysisActivity.realm.beginTransaction();
        ActivityEntry activity4 = analysisActivity.realm.createObject(ActivityEntry.class);

        activity4.setActivityName("running");
        activity4.setYear(2017);
        activity4.setDay(10);
        activity4.setMonth(11);
        activity4.setColor(getResources().getColor(R.color.color1));
        activity4.setId("1234");

        analysisActivity.realm.commitTransaction();
        */
        //Log.i("Color", Integer.toString(getResources().getColor(R.color.color1)));


        return view;
    }

    private void setGridCellAdapterToDate(int month, int year){
        //CHANGED from getApplicationContext
        adapter = new GridCellAdapter(getContext(), R.id.calendar_day_gridcell, month, year);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v){
        if (v == prevMonth){
            if (month <= 1){
                month = 12;
                year--;
            }
            else
                month--;
            setGridCellAdapterToDate(month, year);
        }
        if (v == nextMonth){
            if (month > 11){
                month = 1;
                year++;
            }
            else
                month++;
            setGridCellAdapterToDate(month, year);
        }

    }

    public class GridCellAdapter extends BaseAdapter implements OnClickListener {
        private final Context _context;

        private final List<String> list;
        private static final int DAY_OFFSET = 1;
        private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        private int daysInMonth;
        private int currentDayOfMonth;
        private int currentWeekDay;
        private Button gridcell;
        private TextView num_of_day;
        private ImageView rect1;
        private ImageView rect2;
        private ImageView rect3;
        private ImageView rect4;



        private TextView num_events_per_day;
        private final HashMap<String, Integer> eventsPerMonthMap;


        // Days in Current Month
        public GridCellAdapter(Context context, int textViewResourceId, int month, int year){
            super();
            this._context = context;
            this.list = new ArrayList<String>();
            Calendar calendar = Calendar.getInstance();
            setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
            setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));

            // Print Month
            printMonth(month, year);

            // Find Number of Events
            eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
        }

        private String getMonthAsString(int i){
            return months[i];
        }

        private int getNumberOfDaysOfMonth(int i){
            return daysOfMonth[i];
        }

        public String getItem(int position){
            return list.get(position);
        }

        @Override
        public int getCount(){
            return list.size();
        }

        private void printMonth(int mm, int yy) {
            int trailingSpaces = 0;
            int daysInPrevMonth = 0;
            int prevMonth = 0;
            int prevYear = 0;
            int nextMonth = 0;
            int nextYear = 0;

            int currentMonth = mm - 1;
            daysInMonth = getNumberOfDaysOfMonth(currentMonth);


            // Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
            GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

            if (currentMonth == 11) {
                prevMonth = currentMonth - 1;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 0;
                prevYear = yy;
                nextYear = yy + 1;
            } else if (currentMonth == 0) {
                prevMonth = 11;
                prevYear = yy - 1;
                nextYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 1;
            } else {
                prevMonth = currentMonth - 1;
                nextMonth = currentMonth + 1;
                nextYear = yy;
                prevYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            }

            int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
            trailingSpaces = currentWeekDay;

            if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1) {
                ++daysInMonth;
            }

            // Trailing Month days
            for (int i = 0; i < trailingSpaces; i++) {
                list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
            }

            // Current Month Days
            for (int i = 1; i <= daysInMonth; i++) {
                if (i == getCurrentDayOfMonth())
                    list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                else
                    list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
            }

            // Leading Month days
            for (int i = 0; i < list.size() % 7; i++) {
                list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
            }
        }

        private HashMap<String, Integer> findNumberOfEventsPerMonth(int year, int month){
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            return map;
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View row = convertView;
            if (row == null){
                LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.calendar_day_gridcell, parent, false);
            }

            // Get a reference to the Day gridcell
            gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
            gridcell.setOnClickListener(this);

            num_of_day = (TextView) row.findViewById(R.id.num_of_day);
            //num_of_day.setOnClickListener(this);








            //Spacing issues
            String[] day_color = list.get(position).split("-");
            String theday = day_color[0];
            String themonth = day_color[2];
            String theyear = day_color[3];
            if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)){
                if (eventsPerMonthMap.containsKey(theday)){
                    num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
                    Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                    num_events_per_day.setText(numEvents.toString());
                }
            }

            //TODO: (maybe) try to set text to textview num_of_day. Currently not working.
            // Set the Day GridCell
            gridcell.setText(theday);
            //num_of_day.setText("Hello");

            gridcell.setTag(theday + "-" + themonth + "-" + theyear);
            //Log.i("DAy", theday);
            //num_of_day.setTag(theday + "-" + themonth + "-" + theyear);

            if (day_color[1].equals("GREY"))
                gridcell.setTextColor(Color.GRAY);
                //num_of_day.setTextColor(Color.GRAY);

            if (day_color[1].equals("WHITE"))
                gridcell.setTextColor(Color.WHITE);
                //num_of_day.setTextColor(Color.WHITE);


            if (day_color[1].equals("BLUE"))
                gridcell.setTextColor(getResources().getColor(R.color.color20));
                //num_of_day.setTextColor(getResources().getColor(R.color.color20));

            // Changing background colors //

            rect1 = (ImageView) row.findViewById(R.id.rectangle1);
            rect2 = (ImageView) row.findViewById(R.id.rectangle2);
            rect3 = (ImageView) row.findViewById(R.id.rectangle3);
            rect4 = (ImageView) row.findViewById(R.id.rectangle4);

            GradientDrawable rectBG = (GradientDrawable) rect1.getBackground();
            GradientDrawable rect2BG = (GradientDrawable) rect2.getBackground();
            GradientDrawable rect3BG = (GradientDrawable) rect3.getBackground();
            GradientDrawable rect4BG = (GradientDrawable) rect4.getBackground();


            //TODO: dynamically change colors in calendar based on dates from activity

            //TODO: function that returns the number of activities for the day

            int numActivities = 0;

            todaysActivities = new ArrayList<ActivityEntry>();
            Log.i("Today's day: ", theday);

            for(ActivityEntry activity : allActivities) {
                if((activity.getDay() == Integer.parseInt(theday)) && (activity.getMonth() == monthToInt(themonth))) { //&& activity.getYear() == Integer.parseInt(theyear)) {
                    //Log.i("Today's activitiy", theday + " has an activity");
                    todaysActivities.add(activity);
                    //rectBG.setColor(todaysActivities.get(0).getColor());
                }
            }

            numActivities = activitiesPerDay(todaysActivities);
            Log.i("number activities today", Integer.toString(numActivities));

            //if function = 1
            if(numActivities == 1) {
                rectBG.setColor(todaysActivities.get(0).getColor());
                rect2BG.setColor(todaysActivities.get(0).getColor());
                rect3BG.setColor(todaysActivities.get(0).getColor());
                rect4BG.setColor(todaysActivities.get(0).getColor());

            }
            //if function = 2
            else if(numActivities == 2) {
                rectBG.setColor(todaysActivities.get(0).getColor());
                rect2BG.setColor(todaysActivities.get(0).getColor());
                rect3BG.setColor(todaysActivities.get(1).getColor());
                rect4BG.setColor(todaysActivities.get(1).getColor());
                Log.i("2 activities?", "true");
            }

            //if function = 3
            else if(numActivities == 3) {

                rectBG.setColor(todaysActivities.get(0).getColor());
                rect2BG.setColor(todaysActivities.get(1).getColor());
                rect3BG.setColor(todaysActivities.get(2).getColor());
                rect4BG.setColor(getResources().getColor(R.color.color32)); //base color
            }

            //if function = 4+
            else if(numActivities >= 4) {
                rectBG.setColor(todaysActivities.get(0).getColor());
                rect2BG.setColor(todaysActivities.get(1).getColor());
                rect3BG.setColor(todaysActivities.get(2).getColor());
                rect4BG.setColor(todaysActivities.get(3).getColor()); //base color
            }

            //set back to normal
            else {
                rectBG.setColor(getResources().getColor(R.color.color32));
                rect2BG.setColor(getResources().getColor(R.color.color32));
                rect3BG.setColor(getResources().getColor(R.color.color32));
                rect4BG.setColor(getResources().getColor(R.color.color32));
            }





            return row;
        }
         public int activitiesPerDay(ArrayList<ActivityEntry> today) {
            //This is giving me a memory leak
            int numActivities = today.size();
            //Log.i("Number activities", Integer.toString(numActivities));
            return numActivities;
         }


        @Override
        public void onClick(View view){
            date_month_year = (String) view.getTag();
            flag ="Date selected ...";
            //selectedDayMonthYearButton.setText("Selected: " + date_month_year);
            Log.d(TAG, "onClick: " + date_month_year);

        }

        public int getCurrentDayOfMonth(){
            return currentDayOfMonth;
        }

        private void setCurrentDayOfMonth(int currentDayOfMonth){
            this.currentDayOfMonth = currentDayOfMonth;
        }
        public void setCurrentWeekDay(int currentWeekDay){
            this.currentWeekDay = currentWeekDay;
        }
        public int getCurrentWeekDay(){
            return currentWeekDay;
        }
        public int monthToInt(String month) {
            int monthNum = 0;
            if(month.equals("January")) {
                monthNum = 0;
            }
            if(month.equals("February")) {
                monthNum = 1;
            }
            if(month.equals("March")) {
                monthNum = 2;
            }
            if(month.equals("April")) {
                monthNum = 3;
            }
            if(month.equals("May")) {
                monthNum = 4;
            }
            if(month.equals("June")) {
                monthNum = 5;
            }
            if(month.equals("July")) {
                monthNum = 6;
            }
            if(month.equals("August")) {
                monthNum = 7;
            }
            if(month.equals("September")) {
                monthNum = 8;
            }
            if(month.equals("October")) {
                monthNum = 9;
            }
            if(month.equals("November")) {
                monthNum = 10;
            }
            if(month.equals("December")) {
                monthNum = 11;
            }
            return monthNum;

        }
    }


    }










