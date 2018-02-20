package a123.vaidya.nihal.foodcrunchclient.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import a123.vaidya.nihal.foodcrunchclient.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchclient.OrderStatus;
import a123.vaidya.nihal.foodcrunchclient.R;

/**
 * Created by nnnn on 28/12/2017.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder
        //implements View.OnClickListener
       {

    public TextView txtOrderId,txtOrderStatus,txtOrderPhonw,txtOrderAddress,txtOrderComment;
   // private ItemClickListener itemClickListener;


    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderId =(TextView)itemView.findViewById(R.id.order_id);
        txtOrderStatus=(TextView)itemView.findViewById(R.id.order_status);
        txtOrderPhonw=(TextView)itemView.findViewById(R.id.order_phone);
        txtOrderAddress=(TextView)itemView.findViewById(R.id.order_address);
        txtOrderComment=(TextView)itemView.findViewById(R.id.comment_details);
        //itemView.setOnClickListener(this);
    }

//    public void setItemClickListener(ItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }
//
//    @Override
//    public void onClick(View v) {
//        itemClickListener.onClick(v,getAdapterPosition(),false);
//        return ;
//    }
//
//    public boolean onLongClick(View v) {
//        itemClickListener.onClick(v,getAdapterPosition(),true);
//        return true;
//    }
}
