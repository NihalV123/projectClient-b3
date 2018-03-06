package a123.vaidya.nihal.foodcrunchclient.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchclient.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    public TextView txt_cart_name;
    public final TextView txt_price;
    public final ImageView img_cart_count;
    public final ElegantNumberButton add_to_cart;
    public ImageView cart_image;
//    public final ImageView share;
    private ItemClickListener itemClickListener;
    public RelativeLayout viewBackground;
    public LinearLayout view_foreground;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cart_name= (TextView) itemView.findViewById(R.id.cart_item_name);
        txt_price= (TextView) itemView.findViewById(R.id.cart_item_Price);
//        share = itemView.findViewById(R.id.share);
        img_cart_count= (ImageView) itemView.findViewById(R.id.cart_item_count);
        add_to_cart =(ElegantNumberButton) itemView.findViewById(R.id.cart_number);
        cart_image = (ImageView) itemView.findViewById(R.id.cartimage);
        itemView.setOnCreateContextMenuListener(this);
        viewBackground = (RelativeLayout)itemView.findViewById(R.id.viewBackground);
        view_foreground =(LinearLayout)itemView.findViewById(R.id.view_foreground);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select Action");
        menu.add(0,0,getAdapterPosition(), Common.DELETE);
    }
}