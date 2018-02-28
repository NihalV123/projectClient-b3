package a123.vaidya.nihal.foodcrunchclient.ViewHolder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import a123.vaidya.nihal.foodcrunchclient.Database.Database;
import a123.vaidya.nihal.foodcrunchclient.Model.Order;
import a123.vaidya.nihal.foodcrunchclient.OrderDetail;
import a123.vaidya.nihal.foodcrunchclient.OrderStatus;
import a123.vaidya.nihal.foodcrunchclient.R;

class MyViewHolder extends RecyclerView.ViewHolder{
    public final TextView name;
    public final TextView quantity;
    public final TextView price;
    public final TextView discount;
    public ImageView cart_image;

    public MyViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.product_name);
        quantity = itemView.findViewById(R.id.product_quantitiy);
        price = itemView.findViewById(R.id.product_price);
        discount = itemView.findViewById(R.id.product_discount);
        cart_image = itemView.findViewById(R.id.product_image1);

    }
}

public class OrderDetailsAdapter  extends RecyclerView.Adapter<MyViewHolder>{
    private final List<Order> myOrders;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_detail_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = myOrders.get(position);
        holder.name.setText(String.format("Name : %s",order.getProductName()));
        holder.quantity.setText(String.format("Quantity : %s",order.getQuantity()));
        holder.discount.setText(String.format("Discount : %s",order.getDiscount()));
        holder.price.setText(String.format("Price : %s",order.getPrice()));
//        Picasso.with(this.getBaseContext())
//                .load(myOrders.get(position).getImage())
//                .resize(70,70)
//                .centerCrop()
//                .into(holder.cart_image);
//

    }

    public OrderDetailsAdapter(List<Order> myOrders) {
        this.myOrders = myOrders;
    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }
}
