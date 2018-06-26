package a123.vaidya.nihal.foodcrunchclient;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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
import com.stepstone.apprating.AppRatingDialog;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Common.Config;
import a123.vaidya.nihal.foodcrunchclient.Database.Database;
import a123.vaidya.nihal.foodcrunchclient.Helper.RecyclerItemTouchHelper;
import a123.vaidya.nihal.foodcrunchclient.Interface.RecyclerItemTouchHelperListener;
import a123.vaidya.nihal.foodcrunchclient.Model.DataMessage;
import a123.vaidya.nihal.foodcrunchclient.Model.Food;
import a123.vaidya.nihal.foodcrunchclient.Model.MyResponse;
//import a123.vaidya.nihal.foodcrunchclient.Model.Notification;
import a123.vaidya.nihal.foodcrunchclient.Model.Order;
import a123.vaidya.nihal.foodcrunchclient.Model.Request;
//import a123.vaidya.nihal.foodcrunchclient.Model.Sender;
import a123.vaidya.nihal.foodcrunchclient.Model.Token;
import a123.vaidya.nihal.foodcrunchclient.Model.User;
import a123.vaidya.nihal.foodcrunchclient.Remote.APIService;
import a123.vaidya.nihal.foodcrunchclient.Remote.iGeoCoordinates;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.CartAdapter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import a123.vaidya.nihal.foodcrunchclient.ViewHolder.CartViewHolder;
import dmax.dialog.SpotsDialog;
import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Cart extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        LocationListener,GoogleApiClient.OnConnectionFailedListener, RecyclerItemTouchHelperListener {
    private static final int PAYPAL_REQUEST_CODE = 9999;
    private RecyclerView recyclerView;
    iGeoCoordinates mGoogleMApService;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference requests;
    public TextView txtTotalPrice;
    private ElegantNumberButton cart_number;
    private FButton clear_cart;
    private FButton btnPlace;
    private static final long MIN_CLICK_INTERVAL=600;

    private long mLastClickTime;

    public static boolean isViewClicked = false;


    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private final static int LOCATION_PERMISSION_REQUEST = 1001;
    private static final int UPDATE_INTERVAL = 5000;
    private static final int FASTEST_INTERVAL = 3000;
    private static final int DISPLACEMENT = 10;
    private static final int LOCATION_REQUEST_CODE = 9999;
    private static final int PLAY_SERVICE_REQUEST = 9997;
    private APIService mservice;
    Place shippingAddress;
    //Config the paypal sdk!!!
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    private String address;
    private String comment;
    private String email;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;


    //caligraphy font install
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private List<Order> cart = new ArrayList<>();
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //caligraphy font init
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/restaurant_font.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        setContentView(R.layout.activity_cart);
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]
                    {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                    }, LOCATION_REQUEST_CODE);
        } else {
            if (checkPlayService())//checks if play service is available onthe device
            {
                buildGoogleApiClient();
                createLocationRequest();
            }
        }

        //map code here
        mGoogleMApService = Common.getGeoCodeService();

        //init the paypal sdk!!
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        //startActivity(intent);


        //firebase code
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout2);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                    @Override
                                                    public void onRefresh() {
                                                        if (cart.size() > 0)
                                                            Toast.makeText(Cart.this, "Cart    Refreshed   !!!", Toast.LENGTH_SHORT).show();
                                                        else
                                                            Toast.makeText(Cart.this, "Your shopping cart is empty", Toast.LENGTH_LONG).show();

                                                        loadListFood();
                                                    }
                                                }
        );
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (cart.size() > 0)
                    showAlertDailog();
                else
                    Toast.makeText(Cart.this, "Your shopping cart is empty", Toast.LENGTH_LONG).show();
                loadListFood();
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

        //start service
        mservice = Common.getFCMService();

        //start view
        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//       swipe to delete view init
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new
                RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);
        clear_cart = findViewById(R.id.btnClerCart);

        //clear cart
        clear_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).clearCart(Common.currentUser.getPhone());
                loadListFood();
                for(Order order:cart) {
                    Integer.parseInt(order.getQuantity());
                    //calculate price
//                    int total = 0;
//                    for(Order order:cart)
//                        total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
////        Request orderm=;
////        (Integer.parseInt(order.getQuantity()))
//                    //add substraction code here
//
//                    Locale locale = new Locale("en","BU");
//                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
//                    txtTotalPrice.setText(fmt.format(total)
//                            .replace("$","")
//                            .replace("¤","")
//                            .replace(",",""));//do not add replaceor cart will not work
                }
                Toast.makeText(Cart.this, "Your shopping cart is empty\n Inventory updated", Toast.LENGTH_LONG).show();
            }
        });

        //getting address
        btnPlace.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    if (cart.size() > 0) {
                    showAlertDailog();
                } else {
                    Toast.makeText(Cart.this, "Your shopping cart is empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        loadListFood();

    }


    private boolean checkPlayService() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(this, "This device is not supported", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;


    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
         mGoogleApiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPlayService())//checks if play service is available onthe device
                    {
                        buildGoogleApiClient();
                        createLocationRequest();
                    }

                }
            }
            break;
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

            }

    private void showAlertDailog() {

        AlertDialog.Builder alertdailog = new AlertDialog.Builder(Cart.this);
        alertdailog.setTitle("One Last Step!!");
        alertdailog.setMessage("Enter address or select option");
        alertdailog.setCancelable(false);

        final LayoutInflater inflater = this.getLayoutInflater();
       View email_address_layout = inflater.inflate(R.layout.email_address_layout,null);

        View order_address_comment = inflater.inflate(R.layout.order_address_comment,null);
        final PlaceAutocompleteFragment edtAddress = (PlaceAutocompleteFragment)getFragmentManager()
                .findFragmentById(R.id.place_autocomplete_fragment);

        //final MaterialEditText edtAddress = order_address_comment.findViewById(R.id.edtAddress);
        //hide searchbar
        edtAddress.getView().findViewById(R.id.place_autocomplete_search_button).setVisibility(View.GONE);
        //hint of autocomplete
        ((EditText)edtAddress.getView().findViewById(R.id.place_autocomplete_search_input))
                .setHint("ENTER YOUR ADDRESS");
//        ((EditText)edtAddress.getView().findViewById(R.id.place_autocomplete_search_input))
//                .setHintTextColor(0xFF00FF00);
//        ((EditText)edtAddress.getView().findViewById(R.id.place_autocomplete_search_input))
//                .setBackgroundColor(0xFFFF0000);

        //set text size
        ((EditText)edtAddress.getView().findViewById(R.id.place_autocomplete_search_input))
                .setTextSize(24);
        //set address from autocomplete
        edtAddress.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                shippingAddress = place;

            }

            @Override
            public void onError(Status status) {
                Toast.makeText(Cart.this, "The place deosnt exist", Toast.LENGTH_LONG).show();
            }
        });
        final MaterialEditText edtemail = order_address_comment.findViewById(R.id.edtemail22);
        final MaterialEditText edtComment = order_address_comment.findViewById(R.id.edtComment);
        final RadioButton rbshiphere =(RadioButton)order_address_comment.findViewById(R.id.rbcurentaddress);
        final RadioButton rbshiphome =(RadioButton)order_address_comment.findViewById(R.id.rbhomeaddress);

        final RadioButton rbsmpaypal =(RadioButton)order_address_comment.findViewById(R.id.paypalpm);
        final RadioButton rbsmcod =(RadioButton)order_address_comment.findViewById(R.id.codpm);
        final RadioButton rbsmfcb =(RadioButton)order_address_comment.findViewById(R.id.foodcrunchbalance);

        rbshiphome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    if((Common.currentUser.getHomeAddress() != null )|| (!TextUtils.isEmpty(Common.currentUser.getHomeAddress())))
                    //home address not null or not empty


                    { address =Common.currentUser.getHomeAddress();
                        ((EditText)edtAddress.getView().findViewById(R.id.place_autocomplete_search_input))
                                .setText(address);}
                    else
                        Toast.makeText(Cart.this,"Please  update your home address",Toast.LENGTH_LONG).show();

                }
            }
        });

        rbshiphere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Toast.makeText(Cart.this,"Trying to get your location Please wait!!",Toast.LENGTH_LONG).show();

                    mGoogleMApService.getAddresName(String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&sensor=false",
                            mLastLocation.getLatitude(),mLastLocation.getLongitude()))
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {

                                    try {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                        JSONArray resultArray = jsonObject.getJSONArray("results");
                                        JSONObject firstObject = resultArray.getJSONObject(0);
                                        address = firstObject.getString("formatted_address");try{
                                        ((EditText)edtAddress.getView().findViewById(R.id.place_autocomplete_search_input))
                                                .setText(address);}catch(Exception e){}

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(Cart.this,""+t.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });


                }
            }
        });




        alertdailog.setView(order_address_comment);
        alertdailog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertdailog.setPositiveButton("YES!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                comment = edtComment.getText().toString();
                email = edtemail.getText().toString();
                if(!rbshiphere.isChecked()&&!rbshiphome.isChecked()&&!rbsmfcb.isChecked())
                if(shippingAddress != null)
                    address = shippingAddress.getAddress().toString();
                else
                {
                    Toast.makeText(Cart.this,"ADDRESS CANNOT BE EMPTY ",Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById
                            (R.id.place_autocomplete_fragment)).commit();
                    return;

                }
//                if(rbshiphome.isChecked() && shippingAddress == null)
//                {
//                    Toast.makeText(Cart.this,"ADDRESS CANNOT BE EMPTY ",Toast.LENGTH_LONG).show();
//                    getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById
//                            (R.id.place_autocomplete_fragment)).commit();
//                    return;

//                }

                if(TextUtils.isEmpty(address))
                {
                    Toast.makeText(Cart.this,"Please enter options or address from text utils",Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById
                            (R.id.place_autocomplete_fragment)).commit();
                    return;
                }

                        //check payment
                if(!rbsmpaypal.isChecked() && !rbsmcod.isChecked() && !rbsmfcb.isChecked())
                {
                    Toast.makeText(Cart.this,"Please payment options ",Toast.LENGTH_LONG).show();
                    //remove fragment after close
                    getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById
                            (R.id.place_autocomplete_fragment)).commit();
                }else if(rbsmpaypal.isChecked())
                {
                    String formatAmmount = txtTotalPrice.getText().toString()
                            .replace("¤","")
                            .replace("$","")//replace regional barriers
                            .replace(",","");

                    PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(formatAmmount),
                            "USD","Food Crunch Order in INR",PayPalPayment.PAYMENT_INTENT_SALE);
                    Intent intent =new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
                    startActivityForResult(intent,PAYPAL_REQUEST_CODE);

//                    String order_number = String.valueOf(System.currentTimeMillis());
//                    requests.child(order_number)
//
//                            .setValue(request);
//                    sendNotificatinOrder(order_number);
//                    sendordersemailUSER(order_number);
//                    new Database(getBaseContext()).clearCart();
//                    loadListFood();
                    try{
                        //remove fragment after close
                        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById
                                (R.id.place_autocomplete_fragment)).commit();
                    }catch (Exception exception){

                    }
       } else if(rbsmcod.isChecked())
           {
               Request request = new Request(
                       Common.currentUser.getPhone(),
                       Common.currentUser.getName(),
                       address,
                       txtTotalPrice.getText().toString()
                               .replace("$", "")//replace regional barriers
                               .replace("¤", "")
                               .replace(",", ""),
                       "0",  //for status in request model
                       comment,"COD",email,String.format("%s,%s",mLastLocation.getLatitude(),
                       mLastLocation.getLongitude()),
                       "UNPAID",
                       cart);

               //if yes submitting to the firebase using current time down to milliseconds!!
               String order_number = String.valueOf(System.currentTimeMillis());
               requests.child(order_number)

                       .setValue(request);

               //write code to send emial here

               sendNotificatinOrder(order_number);
               sendordersemailUSER(order_number);
               new Database(getBaseContext()).clearCart(Common.currentUser.getPhone());
               loadListFood();
               finish();

           }else if(rbsmfcb.isChecked())
                {
                    double ammount = 0;//local avriable to store ammount
                    // ammount = Common.formatCurrency(txtTotalPrice.getText().toString(),Locale.US).doubleValue();
                    ammount = Double.valueOf(txtTotalPrice.getText().toString());
                    //compare with balance
                    if(Double.parseDouble(Common.currentUser.getBalance().toString()) >  ammount)
//                    if(Common.currentUser.getBalance() >= ammount)
                    {

                        Request request = new Request(
                                Common.currentUser.getPhone(),
                                Common.currentUser.getName(),
                                address,
                                txtTotalPrice.getText().toString()
                                        .replace("$", "")//replace regional barriers
                                        .replace("¤", "")
                                        .replace(",", ""),
                                "0",  //for status in request model
                                comment,"FOOD CRUNCH BALANCE POINTS",email,String.format("%s,%s",mLastLocation.getLatitude(),
                                mLastLocation.getLongitude()),
                                "PAID",
                                cart);

                        //if yes submitting to the firebase using current time down to milliseconds!!
                        final String order_number = String.valueOf(System.currentTimeMillis());
                        requests.child(order_number)

                                .setValue(request);

                        //write code to send emial here

                        sendNotificatinOrder(order_number);
                        sendordersemailUSER(order_number);
                        new Database(getBaseContext()).clearCart(Common.currentUser.getPhone());
                        loadListFood();
                       //update balance
                        double balance = Double.parseDouble(Common.currentUser.getBalance().toString()) - ammount;
                        //set to database
                        Map<String ,Object> update_balance = new HashMap<>();
                        update_balance.put("balance",balance);

                        //get instance and put
                        FirebaseDatabase.getInstance().getReference("User")
                                .child(Common.currentUser.getPhone())
                                .updateChildren(update_balance)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful())
                                   {
                                       //get instance and update
                                       FirebaseDatabase.getInstance().getReference("User")
                                               .child(Common.currentUser.getPhone())
                                               .addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                       Common.currentUser = dataSnapshot.getValue(User.class);
                                                       sendNotificatinOrder(order_number);
                                                   }

                                                   @Override
                                                   public void onCancelled(DatabaseError databaseError) {

                                                   }
                                               });

                                   }
                                    }
                                });



                    }else
                    {
                        Toast.makeText(Cart.this, "YOU HAVE INSUFFICIENT BALANCE FOR THIS" +
                                        " TRANSACTION \n PLEASE CHOOSE ANOTHER PAYMENT OPTION!!!",
                                Toast.LENGTH_LONG).show();
                        return;

                    }
                }
                //remove fragment after close
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById
                        (R.id.place_autocomplete_fragment)).commit();
            }

        });

        alertdailog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //remove fragment after close
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById
                        (R.id.place_autocomplete_fragment)).commit();

            }
        });
        alertdailog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PAYPAL_REQUEST_CODE)
        {
            switch (resultCode) {
                case RESULT_OK:
                    PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (confirmation != null) {
                        try {
                            String paymentDetail = confirmation.toJSONObject().toString(4);
                            JSONObject jsonObject = new JSONObject(paymentDetail);

                            Request request = new Request(
                                    Common.currentUser.getPhone(),
                                    Common.currentUser.getName(),
                                    address,
                                    txtTotalPrice.getText().toString()
                                            .replace("$", "")//replace regional barriers
                                            .replace("¤", "")
                                            .replace(",", ""),
                                    "0",  //for status in request model
                                    comment,"PayPal",email,
                                    //error solve now
                                    String.format("%s,%s",mLastLocation.getLatitude(),
                                            mLastLocation.getLongitude()),
                                    jsonObject.getJSONObject("response").getString("state"),
                                    cart);

                            //if yes submitting to the firebase using current time down to milliseconds!!
                            String order_number = String.valueOf(System.currentTimeMillis());
                            requests.child(order_number)

                                    .setValue(request);

                            //write code to send emial here

                            sendNotificatinOrder(order_number);
                            sendordersemailUSER(order_number);
                            new Database(getBaseContext()).clearCart(Common.currentUser.getPhone());
                            loadListFood();
                            finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(Cart.this, "Payment canceled", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case PaymentActivity.RESULT_EXTRAS_INVALID:
                    Toast.makeText(Cart.this, "Invalid Payment Please Log in again and try", Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        new Database(getBaseContext()).clearCart();
//        Toast.makeText(Cart.this, "onpause clear caryt engaged!!", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(Cart.this, " CART CLEARED ", Toast.LENGTH_SHORT).show();
      //  new Database(getBaseContext()).clearCart(Common.currentUser.getPhone());
    }

    @Override
    protected void onResume() {
        super.onResume();
       // loadListFood();
    }

    @Override
    protected void onStop() {
        super.onStop();
      //  new Database(getBaseContext()).clearCart();
    }

    private void sendordersemailUSER(String order_number) {
        DatabaseReference tokens = database.getReference("Tokens");
        tokens.orderByKey().equalTo(Common.currentUser.getPhone())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            //Common.currentUser = item;
                            Token token = postSnapshot.getValue(Token.class);
                            if(Common.currentUser.getEmail().toString() == null)
//                                Common.currentUser.setEmail();
                            {showEmailAddressDialog();

                            }
                            String[] TO = {Common.currentUser.getEmail().toString()};
                            String[] CC = {Common.currentUser.getEmail().toString()};
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);

                            emailIntent.setData(Uri.parse("mailto:"));
                            emailIntent.setType("text/plain");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                            emailIntent.putExtra(Intent.EXTRA_CC, CC);
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your order has been updated");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, "Here are your order details \n"+
                                    "The order for user  \t" +
                                    (Common.currentUser.getName())+
                                    "\nwith email  \t" +
                                    (Common.currentUser.getEmail())+
                                    "\nand phone no \t" +
                                    (Common.currentUser.getPhone())+
                                    "\n \n HAS BEEN PLACED!!  \t" +
//                                    "\n  It will be delivered to address  \t" +
//                                    (Common.currentUser.get)+
                                    "\n\nTHANK YOU FOR SHOPPING WITH US!!");
                            try {
                                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(Cart.this, "There are no email clients installed.", Toast.LENGTH_SHORT);
                            }
                        }     Toast.makeText(Cart.this, "There are no email clients installed.", Toast.LENGTH_SHORT);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });         //                       Toast.makeText(Cart.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();


    }

    private void showEmailAddressDialog() {
        android.app.AlertDialog.Builder alertDailog = new android.app.AlertDialog.Builder(Cart.this);
        alertDailog.setTitle("CHANGE EMAIL ADDRESS");
        alertDailog.setCancelable(false);
        alertDailog.setIcon(R.drawable.ic_email_black_24dp);
        alertDailog.setMessage("One time per session");
        LayoutInflater inflater = LayoutInflater.from(Cart.this);
        View layout_email = inflater.inflate(R.layout.email_address_layout,null);
        final MaterialEditText edtEmail = layout_email.findViewById(R.id.edtEmailAddress);
        alertDailog.setView(layout_email);
        alertDailog.setPositiveButton("UPDATE!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final SpotsDialog dialog1 = new SpotsDialog(Cart.this);
                dialog1.show();
                Map<String, Object> emailUpdate = new HashMap<>();
                emailUpdate.put("EMAIL", edtEmail.getText().toString());
                DatabaseReference user = FirebaseDatabase.getInstance().getReference("User");
                user.child(Common.currentUser.getEmail())
                        .updateChildren(emailUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog1.dismiss();
                        Toast.makeText(Cart.this, "Your EMAIL was updated", Toast.LENGTH_LONG).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog1.dismiss();
                                Toast.makeText(Cart.this, "You have problems updating your email buddy",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });
        alertDailog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDailog.show();
    }

    private void sendordersemailUSER(String localKey, final Request item) {

    }
    private void sendNotificatinOrder(final String order_number) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query data = tokens.orderByChild("serverToken").equalTo(true);//chage to true later
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
                {
                    Token serverToken = postSnapShot.getValue(Token.class);
                    //create raw payload
//                    Notification notification = new Notification("From "+Common.currentUser.getName().toString()+" tap to manage it!!!","You have new Order : "+order_number);
//                    Sender content = new Sender(serverToken.getToken(),notification);
                    Map<String,String> datasend = new HashMap<>();
                    datasend.put("title","You have new Order : "+order_number);
                    datasend.put("message","From "+Common.currentUser.getName().toString()+" tap to manage it!!!");
                    DataMessage dataMessage = new DataMessage(serverToken.getToken(),datasend);

                    mservice.sendNotification(dataMessage)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call,  Response<MyResponse> response) {
                                    //crash fix only run when you see client
                                    if(response.code() == 200){
                                        if(response.body().success ==1)
                                        {
                                            Toast.makeText(Cart.this," Order updated notification sent",Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(Cart.this,"Updated but Failed to send notification",Toast.LENGTH_LONG).show();
                                        }}
                                }

                                @Override
                                public void onFailure( Call<MyResponse> call,  Throwable t) {
                                    Toast.makeText(Cart.this,"notification failure",
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
        cart = new Database(this).getCarts(Common.currentUser.getPhone());
        adapter = new CartAdapter(cart,this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        //calculate price
        int total = 0;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
//        Request orderm=;
//        (Integer.parseInt(order.getQuantity()))
        //add substraction code here

        Locale locale = new Locale("en","BU");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(total)
                .replace("$","")
                .replace("¤","")
                .replace(",",""));//do not add replaceor cart will not work


        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int position) {
        cart.remove(position);//remove from ui
        new Database(this).clearCart(Common.currentUser.getPhone());//remove from firebase
        //update database
        for(Order item:cart)
            new Database(this).addToCart(item);
        //refresh ui
        loadListFood();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)&&
                (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
        {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
    }

    private void displayLocation() {

        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)&&
                (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
        {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation != null)
        {
            Log.d("Location","your location: latitude%:"+mLastLocation.getLatitude()+"longitude:"+mLastLocation.getLongitude());
        }
        else
        {
            Log.d("Location","could not get your location ");

        }

//        mLastLocation = location;
//        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
     mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation();


    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof CartViewHolder)
        {
            String name = ((CartAdapter)recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition()).getProductName();

            final Order deleteItem = ((CartAdapter)recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeitem(deleteIndex);
            new Database(getBaseContext()).removeFromCart(deleteItem.getProductId(),Common.currentUser.getPhone());

            //update total on button press
            int total = 0;
            List<Order> orders = new Database(getBaseContext()).getCarts(Common.currentUser.getPhone());
            for(Order item:orders)
                total+=(Integer.parseInt(item.getPrice()))*(Integer.parseInt(item.getQuantity()));
            Locale locale = new Locale("en","BU");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            txtTotalPrice.setText(fmt.format(total)
                    .replace("$","")
                    .replace("¤","")
                    .replace(",",""));//do not add replaceor cart will not work

            //snackbar with undo
            Snackbar snackbar = Snackbar.make(swipeRefreshLayout,name+"  was removed from cart ",Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.restoreitem(deleteItem,deleteIndex);
                    //add to cart
                    new Database(getBaseContext()).addToCart(deleteItem);
                    //update total on button press
                    int total = 0;
                    List<Order> orders = new Database(getBaseContext()).getCarts(Common.currentUser.getPhone());
                    for(Order item:orders)
                        total+=(Integer.parseInt(item.getPrice()))*(Integer.parseInt(item.getQuantity()));
                    Locale locale = new Locale("en","BU");
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                    txtTotalPrice.setText(fmt.format(total)
                            .replace("$","")
                            .replace("¤","")
                            .replace(",",""));//do not add replaceor cart will not work
                }
            });
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.show();


        }
    }
}