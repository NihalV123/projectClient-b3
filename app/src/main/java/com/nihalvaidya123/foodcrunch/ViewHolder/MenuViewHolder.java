package com.nihalvaidya123.foodcrunch.ViewHolder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nihalvaidya123.foodcrunch.Interface.ItemClickListener;
import com.nihalvaidya123.foodcrunch.R;

/**
 * Created by nnnn on 26/12/2017.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

public TextView txtMenuName;
public ImageView imageView;
private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MenuViewHolder(View itemView) {
        super(itemView);

        txtMenuName = itemView.findViewById(R.id.menu_name);
        imageView = itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.ocClick(v,getAdapterPosition(),false);
    }
}
