package com.willard5991.colorfull;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by willard5991 on 11/26/2017.
 */

public class ActivityRecyclerViewAdapter extends RecyclerView.Adapter<ActivityRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<ActivityName> mData = new ArrayList<ActivityName>();
    private int backgroundColor;
    private SparseBooleanArray selected = new SparseBooleanArray();
    private int selectedItem;

    private LayoutInflater mInflater;
    private ActivityRecyclerViewAdapter.ItemClickListener mClickListener;


    // stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView activityButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            activityButton = (TextView) itemView.findViewById(R.id.activityRect);
            if(backgroundColor == -1){
//                activityButton.setBackgroundColor(Color.parseColor("#e6e7e8"));
                activityButton.setBackgroundResource(R.drawable.activity_box_white);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            if (selected.get(getAdapterPosition(), false)) {
                selected.delete(getAdapterPosition());
                activityButton.setSelected(false);
            }
            else {
                selected.put(getAdapterPosition(), true);
                activityButton.setSelected(true);
                selectedItem = getAdapterPosition();
            }
            notifyDataSetChanged();
//            if(view.isSelected()){
//                view.setSelected(false);
//            } else {
//                view.setSelected(true);
//            }
//            Log.i("TAG","clicked item");
//            selected = getAdapterPosition();
//            if(backgroundColor == -1){
//                activityButton.setBackgroundColor(Color.LTGRAY);
//            } else {
//                activityButton.setBackgroundColor(Color.WHITE);
//            }
        }
    }

    public ActivityRecyclerViewAdapter(ArrayList<ActivityName> data, int bc) {
        this.mData = data;
        this.backgroundColor = bc;
    }

    // inflates the cell layout from xml when needed
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_activity_choice, parent, false);
        return new MyViewHolder(view);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ActivityName entry = mData.get(position);
        String name = entry.getName();
        holder.activityButton.setText(name);

        for(int i = 0; i< getItemCount(); i++){
            if(i != selectedItem){
                selected.put(i,false);
            }
        }

        holder.activityButton.setSelected(selected.get(position,false));

    }

    // convenience method for getting data at click position
    public ActivityName getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }



    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
