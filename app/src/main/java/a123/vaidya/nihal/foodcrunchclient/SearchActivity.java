package a123.vaidya.nihal.foodcrunchclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Database.Database;
import a123.vaidya.nihal.foodcrunchclient.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchclient.Model.Favorites;
import a123.vaidya.nihal.foodcrunchclient.Model.Food;
import a123.vaidya.nihal.foodcrunchclient.Model.Order;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.FoodViewHolder;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchActivity extends AppCompatActivity {
        //for searching items in category
    private FirebaseRecyclerAdapter<Food,FoodViewHolder> searchAdapter;
    private final List<String> suggestList = new ArrayList<>();
    private MaterialSearchBar materialSearchBar;

    private FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    private RecyclerView recycler_menu;
    private RecyclerView.LayoutManager layoutManager;
    private String foodId="";
    private FirebaseDatabase database;
    private DatabaseReference foodList;
    private String categoryId="";

    //favorite cache in search
    private Database localDB;

    //Facebook share
    private CallbackManager callbackManager;
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
        setContentView(R.layout.activity_search);

        //init facebook
        new CallbackManager.Factory();
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        //firebase
        database = FirebaseDatabase.getInstance();
        foodList =database.getReference("Foods");
        //loacal db for search
        localDB = new Database(this);
        recycler_menu = findViewById(R.id.recycler_search);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        //search
        materialSearchBar = findViewById(R.id.searchBar);
        materialSearchBar.setHint("SELECT NAME OF YOUR FOOD");
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

//                    Food item = postDnapshot.getValue(Food.class);
//                    suggestList.add(Objects.requireNonNull(item).getName());
                    try{if (search.toUpperCase().contains(materialSearchBar.getText().toUpperCase()))
                        suggest.add(search);
                    else return;}catch (Exception e){}
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

        //load all foods
        loadAllFoods();
    }

    private void loadAllFoods() {
        Query searchbyname =foodList;
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

                //quick cart button here
                viewHolder.add_to_cart.setOnClickListener(new View.OnClickListener() {
                    int clickcount = 0;
                    @Override
                    public void onClick(View v) {
                        boolean isExist =new Database(getBaseContext()).checkFoodExist(adapter.getRef(position).getKey(), Common.currentUser.getPhone());
                        //clicker code strt
                        clickcount = clickcount + 1;
                        if (model.getQuantity() > clickcount) {
                            double balance = model.getQuantity() - clickcount;
                            Map<String, Object> update_balance = new HashMap<>();
                            update_balance.put("quantity", balance);
                            foodId = adapter.getRef(position).getKey();
                            FirebaseDatabase.getInstance().getReference("Foods")
                                    .child(foodId)
                                    .updateChildren(update_balance)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseDatabase.getInstance().getReference("Foods")
                                                        .child(foodId)
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                Toast.makeText(SearchActivity.this, "INVENTORY UPDATED",
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
                            Toast.makeText(SearchActivity.this, "Item was added to cart", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SearchActivity.this, "INVENTORY EMPTY", Toast.LENGTH_LONG).show();
                        }
                        if (!isExist) {
                            new Database(getBaseContext()).addToCart(new Order(Common.currentUser.getPhone(),
                                    adapter.getRef(position).getKey(),//this gets the random id of food id this took me 2 days to debug lol
                                    model.getName(),
                                    "1",
                                    model.getPrice(),
                                    model.getEmail(),
                                    model.getDiscount(),
                                    model.getImage()

                            ));

                        }//isexist end here
                        else {
                            new Database(getBaseContext()).increaseCart(Common.currentUser.getPhone(), adapter.getRef(position).getKey());

                        }

                    }
                });



                //change fav icon
                if(localDB.isFavorites(adapter.getRef(position).getKey(),Common.currentUser.getPhone()))
                    viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);

                viewHolder.fav_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Favorites favorites = new Favorites();
                        favorites.setFoodId(adapter.getRef(position).getKey());
                        favorites.setFoodName(model.getName());
                        favorites.setFoodDescription(model.getDescription());
                        favorites.setFoodDiscount(model.getDiscount());
                        favorites.setFoodImage(model.getImage());
                        favorites.setFoodMenuId(model.getMenuId());
                        favorites.setUserPhone(Common.currentUser.getPhone());
                        favorites.setFoodPrice(model.getPrice());

                        if(!localDB.isFavorites(adapter.getRef(position).getKey(),Common.currentUser.getPhone()))
                        {
                            localDB.addToFavorites(favorites);
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                            Toast.makeText(SearchActivity.this,"The"+model.getName()+"\n was added to " +
                                    "favorites",Toast.LENGTH_SHORT).show();
//

                        }else
                        {
                            localDB.removeFromFavorites(adapter.getRef(position).getKey(),Common.currentUser.getPhone());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            Toast.makeText(SearchActivity.this,"The"+model.getName()+"\n was removed from " +
                                    " favorites",Toast.LENGTH_SHORT).show();
//
                        }
                    }
                });


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View v, int position, boolean isLongClick) {
                        //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        //this is the third activity
                        Intent foodDetail = new Intent(SearchActivity.this,FoodDetail.class);
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
                        Intent foodDetail = new Intent(SearchActivity.this,FoodDetail.class);
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
        foodList.addListenerForSingleValueEvent(new ValueEventListener() {
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

    @Override
    protected void onStop() {
       if(adapter != null)
           adapter.stopListening();
        if(searchAdapter != null)
            searchAdapter.stopListening();
        super.onStop();
    }
}
