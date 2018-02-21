package a123.vaidya.nihal.foodcrunchclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.OrderDetailsAdapter;
import a123.vaidya.nihal.foodcrunchclient.Model.Request;

public class OrderDetail extends AppCompatActivity {

    TextView order_id,order_phone,order_address,order_total,comment_details,order_status;
    String order_id_value ="";
    RecyclerView listFood;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        order_id = (TextView)findViewById(R.id.order_id);
        order_phone = (TextView)findViewById(R.id.order_phone);
        order_address = (TextView)findViewById(R.id.order_address);
        order_total = (TextView)findViewById(R.id.order_total);
        comment_details = (TextView)findViewById(R.id.comment_details);
        order_status = (TextView)findViewById(R.id.order_status);
        listFood = (RecyclerView)findViewById(R.id.listFood);
        listFood.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listFood.setLayoutManager(layoutManager);

        if(getIntent()!=null)
            order_id_value = getIntent().getStringExtra("OrderId");
        //set  values
        order_id.setText("Order id : "+order_id_value);
        order_phone.setText("Phone No : "+ Common.currentRequest.getPhone());
        order_total.setText("Total Ammount : Rs "+Common.currentRequest.getTotal());
        order_address.setText("Shipping Address : "+Common.currentRequest.getAddress());
        comment_details.setText("Comments : "+Common.currentRequest.getComment());
        //order_status.setText(Common.currentRequest.getStatus());


        OrderDetailsAdapter adapter = new OrderDetailsAdapter(Common.currentRequest.getFoods());
        adapter.notifyDataSetChanged();
        listFood.setAdapter(adapter);


    }
}
