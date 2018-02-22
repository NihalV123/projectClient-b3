package a123.vaidya.nihal.foodcrunchclient;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchclient.Model.Request;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.OrderViewHolder;
import dmax.dialog.SpotsDialog;


public class  OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public  RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //firebase
        Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("6ep60jj09lvUcHncYM3yCoIMr", "WXvH93jw1urHD9IzIk6FDRmKW0X5LGZgmMCDo67XFk2uDf2LGJ"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //do not delete may cause issues in viewing orders in client side and server side
//        if(getIntent()==null)
//        {
            loadOrders(Common.currentUser.getPhone());
//        }else
//        {
//            loadOrders(getIntent().getStringExtra("userPhone"));
//        }


    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class, R.layout.order_layout, OrderViewHolder.class,
                requests
                        //.orderByChild("phone").equalTo(phone)
                ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, int position) {
                viewHolder.txtOrderId.setText("Order Id : "+adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText("Status : "+Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText("\n Address : "+model.getAddress());
                viewHolder.txtOrderPhonw.setText("Phone No : "+model.getPhone());
                viewHolder.txtOrderComment.setText("\n Comment : "+model.getComment());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View v, int position, boolean isLongClick) {
                        if(!isLongClick)
                        {
                            final SpotsDialog dialog = new SpotsDialog(OrderStatus.this);
                            dialog.show();
                            Intent orderDetail = new Intent(OrderStatus.this,OrderDetail.class);
                            Common.currentRequest = model;
                            orderDetail.putExtra("OrderId",adapter.getRef(position).getKey());
                            startActivity(orderDetail);
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(OrderStatus.this,"Not Yet Implemeneted",Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }

        };

        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Placed";
        else if (status.equals("1"))
            return "Your food is on the way ";
        else
            return "Shipped!!";
    }
};
