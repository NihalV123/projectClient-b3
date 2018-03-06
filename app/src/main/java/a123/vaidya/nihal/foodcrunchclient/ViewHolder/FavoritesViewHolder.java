package a123.vaidya.nihal.foodcrunchclient.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import a123.vaidya.nihal.foodcrunchclient.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchclient.R;

public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final TextView food_name;
    public final TextView food_price;
    public final ImageView food_image;
   // public final ImageView fav_image;
    public RelativeLayout viewBackground;
    public RelativeLayout view_foreground2;
        public final ImageView share;
//    public final ImageView like;
   // public final RatingBar ratingbar;
    public final ImageView add_to_cart;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {

        this.itemClickListener = itemClickListener;

    }

    public FavoritesViewHolder(View itemView) {
        super(itemView);

        food_name = itemView.findViewById(R.id.food_name);
        food_image = itemView.findViewById(R.id.food_image);
        share = itemView.findViewById(R.id.share);
       // ratingbar = itemView.findViewById(R.id.ratingbarrr);
//        like = itemView.findViewById(R.id.like);
        add_to_cart= itemView.findViewById(R.id.add_to_crat);
       // fav_image = itemView.findViewById(R.id.fav);
        food_price = itemView.findViewById(R.id.food_Price);
        viewBackground = (RelativeLayout)itemView.findViewById(R.id.viewBackgroundfav);
        view_foreground2 =(RelativeLayout) itemView.findViewById(R.id.view_foregroundfav);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}

