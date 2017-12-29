package com.nihalvaidya123.foodcrunch.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nihalvaidya123.foodcrunch.Interface.ItemClickListener;
import com.nihalvaidya123.foodcrunch.R;

/**
 * Created by nnnn on 26/12/2017.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView food_name;
    public ImageView food_image;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(View itemView) {
        super(itemView);

        food_name = itemView.findViewById(R.id.food_name);
        food_image = itemView.findViewById(R.id.food_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
