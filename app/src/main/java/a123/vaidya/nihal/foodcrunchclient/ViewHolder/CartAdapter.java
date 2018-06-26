package a123.vaidya.nihal.foodcrunchclient.ViewHolder;

<<<<<<< HEAD
import android.app.Activity;
import android.content.Context;
=======
>>>>>>> old/master
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
<<<<<<< HEAD
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;
=======
>>>>>>> old/master
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import a123.vaidya.nihal.foodcrunchclient.Cart;
import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Database.Database;
import a123.vaidya.nihal.foodcrunchclient.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchclient.Model.Order;
import a123.vaidya.nihal.foodcrunchclient.R;

/**
 * Created by nnnn on 27/12/2017.
 */

<<<<<<< HEAD


public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{
    private List<Order> listData;
    private ShareDialog shareDialog;
    private Context context;
    private CallbackManager callbackManager;
    private TextView textView;
    private final Cart cart;

=======
class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    public TextView txt_cart_name;
    public final TextView txt_price;
    public final ImageView img_cart_count;
    public final ElegantNumberButton add_to_cart;
    public ImageView cart_image;
    private ItemClickListener itemClickListener;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cart_name= (TextView) itemView.findViewById(R.id.cart_item_name);
        txt_price= (TextView) itemView.findViewById(R.id.cart_item_Price);
        img_cart_count= (ImageView) itemView.findViewById(R.id.cart_item_count);
        add_to_cart =(ElegantNumberButton) itemView.findViewById(R.id.cart_number);
        cart_image = (ImageView) itemView.findViewById(R.id.cartimage);
        itemView.setOnCreateContextMenuListener(this);
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

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listData;
    private final Cart cart;



>>>>>>> old/master
    public CartAdapter(List<Order> listData, Cart cart) {
        this.listData = listData;
        this.cart = cart;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cart);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
<<<<<<< HEAD
//        new CallbackManager.Factory();
//        callbackManager = CallbackManager.Factory.create();
//        shareDialog = new ShareDialog((Activity) context);
=======
>>>>>>> old/master
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, final int position) {
        Picasso.with(cart.getBaseContext())
                .load(listData.get(position).getImage())
                .resize(70,70)
                .centerCrop()
                .into(holder.cart_image);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+listData.get(position).getQuantity(), Color.LTGRAY);
        holder.img_cart_count.setImageDrawable(drawable);
        holder.add_to_cart.setNumber(listData.get(position).getQuantity());
        holder.add_to_cart.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Order order = listData.get(position);
                order.setQuantity(String.valueOf(newValue));
                new Database(cart).updateCart(order);

                //update total on button press
                int total = 0;
<<<<<<< HEAD
                List<Order> orders = new Database(cart).getCarts(Common.currentUser.getPhone());
=======
                List<Order> orders = new Database(cart).getCarts();
>>>>>>> old/master
                for(Order item:orders)
                    total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
                Locale locale = new Locale("en","BU");
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                cart.txtTotalPrice.setText(fmt.format(total)
                        .replace("$","")
                        .replace("¤","")
                        .replace(",",""));//do not add replaceor cart will not work
            }
        });
        Locale locale = new Locale("en","BU");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        //calculating price
        int price = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));

        holder.txt_price.setText(fmt.format(price));
        holder.txt_cart_name.setText(listData.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
<<<<<<< HEAD

    public Order getItem(int position)
    {
//        return favoritesList.size();
        return listData.get(position);
    }

    public void removeitem(int position)
    {
        listData.remove(position);
        notifyItemRemoved(position);

    }

    public void restoreitem(Order item,int position)
    {
        listData.add(position,item);
        notifyItemInserted(position);

    }
=======
>>>>>>> old/master
}
