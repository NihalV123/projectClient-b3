package a123.vaidya.nihal.foodcrunchclient;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import org.json.JSONException;
import org.json.JSONObject;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Common.Config;
import a123.vaidya.nihal.foodcrunchclient.Database.Database;
import a123.vaidya.nihal.foodcrunchclient.Model.MyResponse;
import a123.vaidya.nihal.foodcrunchclient.Model.Notification;
import a123.vaidya.nihal.foodcrunchclient.Model.Order;
import a123.vaidya.nihal.foodcrunchclient.Model.Request;
import a123.vaidya.nihal.foodcrunchclient.Model.Sender;
import a123.vaidya.nihal.foodcrunchclient.Model.Token;
import a123.vaidya.nihal.foodcrunchclient.Remote.APIService;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.CartAdapter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity {
    private static final int PAYPAL_REQUEST_CODE = 9999;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtTotalPrice;

    FButton btnPlace;
    APIService mservice;
    //Config the paypal sdk!!!
        static PayPalConfiguration config = new PayPalConfiguration()
        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
        String address,comment;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //init the paypal sdk!!
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        //startActivity(intent);


        //firebase code
        Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("6ep60jj09lvUcHncYM3yCoIMr", "WXvH93jw1urHD9IzIk6FDRmKW0X5LGZgmMCDo67XFk2uDf2LGJ"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //start service
        mservice = Common.getFCMService();

        //start view
        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);


        //getting address
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.size() > 0)
                showAlertDailog();
                else
                    Toast.makeText(Cart.this,"Your shopping cart is empty",Toast.LENGTH_LONG).show();
            }
        });

        loadListFood();

    }

    private void showAlertDailog() {

        AlertDialog.Builder alertdailog = new AlertDialog.Builder(Cart.this);
        alertdailog.setTitle("One Last Step!!");
        alertdailog.setMessage("Enter your Address :   ");

        final LayoutInflater inflater = this.getLayoutInflater();
        View order_address_comment = inflater.inflate(R.layout.order_address_comment,null);

        final MaterialEditText edtAddress = (MaterialEditText)order_address_comment.findViewById(R.id.edtAddress);
        final MaterialEditText edtComment = (MaterialEditText)order_address_comment.findViewById(R.id.edtComment);

        alertdailog.setView(order_address_comment);
        alertdailog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertdailog.setPositiveButton("YES!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                address = edtAddress.getText().toString();
                comment = edtComment.getText().toString();

                String formatAmmount = txtTotalPrice.getText().toString()
                        .replace("¤","")
                        .replace("$","")//replace regional barriers
                        .replace(",","");

                PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(formatAmmount),
                "USD","Food Crunch Order",PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent =new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
                startActivityForResult(intent,PAYPAL_REQUEST_CODE);

            }
        });

        alertdailog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertdailog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PAYPAL_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null)
                {
                    try
                    {
                        String paymentDetail = confirmation.toJSONObject().toString(4);
                        JSONObject jsonObject = new JSONObject(paymentDetail);

                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        address,
                        txtTotalPrice.getText().toString()
                                .replace("$","")//replace regional barriers
                                .replace("¤","")
                                .replace(",",""),
                        "0",  //for status in request model
                       comment,
                        jsonObject.getJSONObject("response").getString("state"),
                        cart);

                //if yes submitting to the firebase using current time down to milliseconds!!
                String order_number = String.valueOf(System.currentTimeMillis());
                requests.child(order_number)
                        //requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                sendNotificatinOrder(order_number);

                        ///send the motherfucking email

//                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//                String[] recipients = new String[]{"nhlvcam@gmail.com.com", "",};
//                //emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,"nareshdcam@gmail.con");
//                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test");
//                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "This is email's message");
//                emailIntent.setType("text/plain");
//                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        Toast.makeText(Cart.this,"Thank you for shopping\n\nYour order email has been sent!!\n\nActually not LOOOOL", Toast.LENGTH_SHORT).show();
                        //delete cart
                        new Database(getBaseContext()).clearCart();

                        finish();


                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else if(resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(Cart.this,"Payment canceled", Toast.LENGTH_LONG).show();
                finish();
            }else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            {
                Toast.makeText(Cart.this,"Invalid Payment Please Log in again and try", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void sendNotificatinOrder(final String order_number) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query data = tokens.orderByChild("isServerToken").equalTo(true);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
                {
                    Token serverToken = postSnapShot.getValue(Token.class);
                    //create raw payload
                    Notification notification = new Notification("Food-Crunch","You have new Order : "+order_number);
                    Sender content = new Sender(serverToken.getToken(),notification);
                    mservice.sendNotification(content)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    //crash fix only run when you see client
                                    if(response.code() == 200){
                                    if(response.body().success == 1)
                                    {
                                           Toast.makeText(Cart.this,"Thank you for shopping Order placed!",
                                           Toast.LENGTH_SHORT).show();
                                           finish();
                                    }else
                                    {
                                        Toast.makeText(Cart.this,"PLEASE TRY AGAIN!!!",
                                                Toast.LENGTH_SHORT).show();
                                    }}
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {
                                    Toast.makeText(Cart.this,"Houston there's a problem!!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        //calculate price
        int total = 0;

        //undo this sa soon as possible

        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
//        //latest error 2
        Locale locale = new Locale("en","BU");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.DELETE))
        deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int position) {
        cart.remove(position);//remove from ui
        new Database(this).clearCart();//remove from firebase
        //update database
        for(Order item:cart)
            new Database(this).addToCart(item);
        //refresh ui
        loadListFood();

    }
}
