package a123.vaidya.nihal.foodcrunchclient;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Database.Database;
import a123.vaidya.nihal.foodcrunchclient.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchclient.Model.Food;
import a123.vaidya.nihal.foodcrunchclient.Model.Order;
import a123.vaidya.nihal.foodcrunchclient.Model.Rating;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.FoodViewHolder;
import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FoodList extends AppCompatActivity {

    private RecyclerView recycler_menu;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase database;
    private DatabaseReference foodList;
    private String categoryId="";

    private FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    //for searching firebase
    private FirebaseRecyclerAdapter<Food,FoodViewHolder> searchAdapter;
    private final List<String> suggestList = new ArrayList<>();
    private MaterialSearchBar materialSearchBar;
    private Database localDB;
    private SwipeRefreshLayout rootLayout;
    private ImageView fav_image;
    private ImageView like;
    private String foodId="";
    private ImageView share;
    private RatingBar ratingbar;
    private ImageView add_to_cart;
    private CounterFab fab;
    //Facebook share
    private CallbackManager callbackManager;
    private TextView textView;
    private ShareDialog shareDialog;


    //create bitmap from picaso
    private final Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();
            if(ShareDialog.canShow(SharePhotoContent.class))
            {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
            shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

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
        setContentView(R.layout.activity_food_list);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SpotsDialog dialog = new SpotsDialog(FoodList.this);
                dialog.show();
                Intent cartIntent = new Intent(FoodList.this,Cart.class);
                startActivity(cartIntent);
                dialog.dismiss();

            }
        });
        fab.setCount(new Database(this).getCountCart());
        //firebase code
        callbackManager = new CallbackManager.Factory().create();
        shareDialog = new ShareDialog(this);
        Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("6ep60jj09lvUcHncYM3yCoIMr", "WXvH93jw1urHD9IzIk6FDRmKW0X5LGZgmMCDo67XFk2uDf2LGJ"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        database = FirebaseDatabase.getInstance();
        foodList =database.getReference("Foods");
        localDB = new Database(this);
        rootLayout = findViewById(R.id.rootLayout);
        rootLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark);
        rootLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getIntent()!=null)
                    categoryId = getIntent().getStringExtra("CategoryId");
                if (!categoryId.isEmpty())
                {
                    if (Common.isConnectedToInternet(getBaseContext()))
                        loadListFood(categoryId);
                    else
                        Toast.makeText(FoodList.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
                }
            }
        });

        rootLayout.post(new Runnable() {
            @Override
            public void run() {
                //get intent and search from here
                if (getIntent()!=null)
                    categoryId = getIntent().getStringExtra("CategoryId");
                if (!categoryId.isEmpty())
                {
                    if (Common.isConnectedToInternet(getBaseContext()))
                        loadListFood(categoryId);
                    else
                    {Toast.makeText(FoodList.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
                    return;}
                }
                //search
                materialSearchBar = findViewById(R.id.searchBar);
                textView = findViewById(R.id.textView3);
                textView = findViewById(R.id.textView2);
                materialSearchBar.setHint("Enter the name of your food");
//        materialSearchBar.setSpeechMode(false);
                loadSuggest();
                 materialSearchBar.setLastSuggestions(suggestList);
                materialSearchBar.setCardViewElevation(10);
                materialSearchBar.addTextChangeListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        List<String> suggest = new ArrayList<>();
                        for(String search:suggestList)
                        {
                            if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                                suggest.add(search);
                        }
                        materialSearchBar.setLastSuggestions(suggest);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                    @Override
                    public void onSearchStateChanged(boolean enabled) {
                        //disable and enable for ui effect
                        if(!enabled)
                            recycler_menu.setAdapter(adapter);
                    }

                    @Override
                    public void onSearchConfirmed(CharSequence text) {
                        startSearch(text);
                    }

                    @Override
                    public void onButtonClicked(int buttonCode) {

                    }
                });


            }
        });

        recycler_menu = findViewById(R.id.recycler_food);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        fav_image = findViewById(R.id.fav);
//        share = findViewById(R.id.share);
        ratingbar = findViewById(R.id.ratingbarrr);
//        like = findViewById(R.id.like);
        add_to_cart= findViewById(R.id.add_to_crat);

        //get intent
        if (getIntent()!=null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty())
        {
            if (Common.isConnectedToInternet(getBaseContext()))
            loadListFood(categoryId);
            else
                Toast.makeText(FoodList.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
            return;
        }


    }

    private void startSearch(CharSequence text) {
        //query search by name
        Query searchbyname =foodList.orderByChild("name").equalTo(text.toString());
        //options
        FirebaseRecyclerOptions<Food> foodoptions = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(searchbyname,Food.class)
                .build();

        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(foodoptions) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder viewHolder, int position, @NonNull Food model) {
                viewHolder.food_name.setText(model.getName());
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View v, int position, boolean isLongClick) {
                        //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        //this is the third activity
                        Intent foodDetail = new Intent(FoodList.this,FoodDetail.class);
                        //send to new activity
                        foodDetail.putExtra("FoodId",searchAdapter.getRef(position).getKey());
                        startActivity(foodDetail);

                    }
                });
            }

            @Override
            public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.food_item,parent,false);
                return new FoodViewHolder(itemView);
            }
        };
        searchAdapter.startListening();
        recycler_menu.setAdapter(searchAdapter);


    }

    private void loadSuggest() {
        foodList.orderByChild("menuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postDnapshot:dataSnapshot.getChildren())
                        {
                            Food item = postDnapshot.getValue(Food.class);
                            suggestList.add(Objects.requireNonNull(item).getName());
                        }
                        materialSearchBar.setLastSuggestions(suggestList);
//                        {
//                            Rating item = postSnapshot.getValue(Rating.class);
//                            sum+=Integer.parseInt(Objects.requireNonNull(item).getRateValue());
//                            count++;
//                        }
//                        if(count != 0) {
//                            float average = sum / count;
//                            ratingBar.setRating(average);
//                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    //select * from foods where menuid=&
    private void loadListFood(String categoryId) {
        Query searchbyname =foodList.orderByChild("menuId").equalTo(categoryId);
        //options
        FirebaseRecyclerOptions<Food> foodoptions = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(searchbyname,Food.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(foodoptions) {
            @Override
            protected void onBindViewHolder(@NonNull final FoodViewHolder viewHolder, final int position, @NonNull final Food model) {
                viewHolder.food_name.setText(model.getName());
                viewHolder.food_price.setText(String.format("INR :  %s",model.getPrice()));
                //viewHolder.ratingbar.setRating(Float.parseFloat(.getRateValue()));
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);

                //add to cart
                viewHolder.add_to_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int clickcount=0;       //count no of times button was pressed
                        clickcount=clickcount+1;
                        new Database(getBaseContext()).addToCart(new Order(adapter.getRef(position).getKey(), model.getName(),"1",
                                model.getPrice(), model.getDiscount(),model.getImage(),model.getEmail()
                        ));

                        if(model.getQuantity() > clickcount )
                        {
                            double balance = model.getQuantity() - clickcount;
                            //set to database
                            Map<String ,Object> update_balance = new HashMap<>();
                            update_balance.put("quantity",balance);
                            foodId = model.getName();
//                            foodId = model
                            //get instance and put
                            FirebaseDatabase.getInstance().getReference("Foods")
//                            foodId =adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
                                    .child(foodId)
                                    //.equalTo() //if doesnt work try get curent category id
                                    .updateChildren(update_balance)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                //get instance and update
                                                FirebaseDatabase.getInstance().getReference("Foods")
                                                        .child(foodId)
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                                        food_price.setText(currentFood.getPrice());
                                                                Toast.makeText(FoodList.this, "INVENTORY UPDATED",
                                                                        Toast.LENGTH_LONG).show();
                                                               Food model = dataSnapshot.getValue(Food.class);

                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });
                                            }
                                        }
                                    });
                            Toast.makeText(FoodList.this,"Item was added to cart",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(FoodList.this, "INVENTORY EMPTY", Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(FoodList.this, "ITEM CAN BE QUICKLY ADDED ONLY ONCE \n TO ADD MORE TIMES CLICK ON THE  ITEM", Toast.LENGTH_LONG).show();
                        v.setOnClickListener(null);
                    }
                });
                //change fav icon
                if(localDB.isFavorites(adapter.getRef(position).getKey(),Common.currentUser.getPhone()))
                    viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);

//                viewHolder.share.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Picasso.with(getApplicationContext())
//                                .load(model.getImage())
//                                .into(target);
//                        Toast.makeText(FoodList.this,"Getting Ready \nShare To FACEBOOK \n Unless " +
//                                "the sdk got updated \nOne food once per session",Toast.LENGTH_SHORT).show();
//                    }
//                });
//                viewHolder.like.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(FoodList.this,"Comming soon!",Toast.LENGTH_SHORT).show();
//                    }
//                });



                viewHolder.fav_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!localDB.isFavorites(adapter.getRef(position).getKey(),Common.currentUser.getPhone()))
                        {
                            localDB.addToFavorites(adapter.getRef(position).getKey(),Common.currentUser.getPhone());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                            Toast.makeText(FoodList.this,"The"+model.getName()+"\n was added to " +
                                    "favorites",Toast.LENGTH_SHORT).show();
                            Snackbar.make(rootLayout,"The"+model.getName()+" \nwas added to" +
                                    " favorites",Snackbar.LENGTH_LONG).show();

                        }else
                        {
                            localDB.removeFromFavorites(adapter.getRef(position).getKey(),Common.currentUser.getPhone());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            Toast.makeText(FoodList.this,"The"+model.getName()+"\n was removed from " +
                                    " favorites",Toast.LENGTH_SHORT).show();
                            Snackbar.make(rootLayout,"The"+model.getName()+" \nwas removed from" +
                                    " favorites",Snackbar.LENGTH_LONG).show();

                        }
                    }
                });


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View v, int position, boolean isLongClick) {
                        //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        //this is the third activity
                        Intent foodDetail = new Intent(FoodList.this,FoodDetail.class);
                        //send to new activity
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);


                    }
                });


            }

            @Override
            public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.food_item,parent,false);
                return new FoodViewHolder(itemView);
            }
        };
        adapter.startListening();

        //set adapter
        recycler_menu.setAdapter(adapter);

        rootLayout.setRefreshing(false);
    }
    @Override
    protected void onStart() {
        super.onStart();
        loadListFood(categoryId);
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recycler_menu.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListFood(categoryId);
        if (adapter!= null){
            adapter.startListening();}
        Objects.requireNonNull(adapter).notifyDataSetChanged();
        recycler_menu.setAdapter(adapter);

    }
}
