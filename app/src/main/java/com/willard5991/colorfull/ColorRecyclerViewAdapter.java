package com.willard5991.colorfull;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jennapfingsten on 11/19/17.
 */

public class ColorRecyclerViewAdapter extends RecyclerView.Adapter<ColorRecyclerViewAdapter.ViewHolder> {

    private int[] mData = new int[0];
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    private ImageView colorButton;

    //TODO: shouldn't this be public?
    // data is passed into the constructor
    ColorRecyclerViewAdapter(Context context, int[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_color, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        String animal = mData[position];
//        holder.myTextView.setText(animal);
        //Drawable background = colorButton.getBackground();
        GradientDrawable circleBG = (GradientDrawable) colorButton.getBackground();
        circleBG.setColor(mData[position]);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ViewHolder(View itemView) {
            super(itemView);
            colorButton = (ImageView) itemView.findViewById(R.id.colorCircle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    int getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
