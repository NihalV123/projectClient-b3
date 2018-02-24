package a123.vaidya.nihal.foodcrunchclient.ViewHolder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.FoodList;
import a123.vaidya.nihal.foodcrunchclient.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchclient.OrderDetail;
import a123.vaidya.nihal.foodcrunchclient.OrderStatus;
import a123.vaidya.nihal.foodcrunchclient.R;

/**
 * Created by nnnn on 26/12/2017.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView food_name,food_price;
    public ImageView food_image,fav_image,share,like,add_to_cart;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {

         this.itemClickListener = itemClickListener;

    }

    public FoodViewHolder(View itemView) {
        super(itemView);

        food_name =(TextView) itemView.findViewById(R.id.food_name);
        food_image = (ImageView) itemView.findViewById(R.id.food_image);
        share = (ImageView)itemView.findViewById(R.id.share);
        like = (ImageView)itemView.findViewById(R.id.like);
        add_to_cart=(ImageView)itemView.findViewById(R.id.add_to_crat);
        fav_image =(ImageView) itemView.findViewById(R.id.fav);
        food_price = (TextView)itemView.findViewById(R.id.food_Price);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
