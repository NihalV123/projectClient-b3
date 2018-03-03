package a123.vaidya.nihal.foodcrunchclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.util.Objects;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchclient.Model.Request;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.OrderViewHolder;
import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class  OrderStatus extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;
    private FirebaseDatabase database;
    private DatabaseReference requests;
    //caligraphy font install
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //caligraphy font init
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/restaurant_font.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_order_status);

        //firebase
        swipeRefreshLayout = findViewById(R.id.swipe_order_list);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadOrders(Common.currentUser.getPhone());
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadOrders(Common.currentUser.getPhone());
            }
        });
        Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("6ep60jj09lvUcHncYM3yCoIMr", "WXvH93jw1urHD9IzIk6FDRmKW0X5LGZgmMCDo67XFk2uDf2LGJ"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

            loadOrders(Common.currentUser.getPhone());

    }

    private void loadOrders(String phone) {

        Query getOrderByUserQuery = requests.orderByChild("phone").equalTo(phone);
        FirebaseRecyclerOptions<Request> orderoptions = new FirebaseRecyclerOptions.Builder<Request>()
                .setQuery(getOrderByUserQuery, Request.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(orderoptions) {
            @Override
            public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemview = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_layout, parent, false);
                return new OrderViewHolder(itemview);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder viewHolder,@NonNull final int position,
                                            @NonNull final Request model) {
                viewHolder.txtOrderId.setText("Order Id : "+adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText("Status : "+Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText("EMAIL : "+model.getEmail());
               viewHolder.txtOrderPhonw.setText("Phone No : "+model.getPaymentState());
                viewHolder.txtOrderComment.setText("Comment : "+model.getPaymentmethod());

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
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }



    @Override
    protected void onResume() {
        super.onResume();
        loadOrders(Common.currentUser.getPhone());
        if (adapter!= null){
            adapter.startListening();}
        Objects.requireNonNull(adapter).notifyDataSetChanged();
    }
    @Override
    protected void onStop() {
        super.onStop();
        //adapter.stopListening();
    }

    private String convertCodeToStatus(String status) {
        switch (status) {
            case "0":
                return "Placed";
            case "1":
                return "Your food is on the way ";
            default:
                return "Shipped!!";
        }
    }
}
