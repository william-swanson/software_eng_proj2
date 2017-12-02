package com.willard5991.colorfull;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by willard5991 on 11/26/2017.
 */

public class ActivityRecyclerViewAdapter extends RecyclerView.Adapter<ActivityRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<ActivityEntry> mData = new ArrayList<ActivityEntry>();
    private int backgroundColor;

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
        }
    }

    public ActivityRecyclerViewAdapter(ArrayList<ActivityEntry> data, int bc) {
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

        ActivityEntry entry = mData.get(position);
        String name = entry.getActivityName();
        holder.activityButton.setText(name);

    }

    // convenience method for getting data at click position
    public ActivityEntry getItem(int id) {
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
