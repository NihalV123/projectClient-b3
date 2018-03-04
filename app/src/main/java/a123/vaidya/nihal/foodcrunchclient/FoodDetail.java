
package a123.vaidya.nihal.foodcrunchclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Database.Database;
import a123.vaidya.nihal.foodcrunchclient.Model.Food;
import a123.vaidya.nihal.foodcrunchclient.Model.Order;
import a123.vaidya.nihal.foodcrunchclient.Model.Rating;
import a123.vaidya.nihal.foodcrunchclient.Model.User;
import info.hoang8f.widget.FButton;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FoodDetail extends AppCompatActivity implements RatingDialogListener{

    private TextView food_name;
    private TextView food_price;
    private TextView food_description;
    private TextView food_video;
    private TextView food_recepie;
    private ImageView food_image;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton btnRating;
    private CounterFab btnCart;
    private ElegantNumberButton numberButton;
    private RatingBar ratingBar;

    //caligraphy font install
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    private String foodId="";
    String categoryId ="";
    private FirebaseDatabase database;
    private DatabaseReference foods;
    FButton btnSohowComment;
    private DatabaseReference ratingTbl;

    private Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //caligraphy font init
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/restaurant_font.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_food_detail);

        btnSohowComment = (FButton)findViewById(R.id.bthshowcomments);
        btnSohowComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodDetail.this,ShowComment.class);
                intent.putExtra(Common.INTENT_FOOD_ID,foodId);
                        startActivity(intent);
            }
        });

        //Firebase code
        Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("6ep60jj09lvUcHncYM3yCoIMr", "WXvH93jw1urHD9IzIk6FDRmKW0X5LGZgmMCDo67XFk2uDf2LGJ"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");
        ratingTbl = database.getReference("Rating");

        //actual view
        numberButton = findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btnCart);
        btnRating = findViewById(R.id.btnRating);
        ratingBar = findViewById(R.id.ratingbar);

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();

            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Database(getBaseContext()).addToCart(new Order(foodId, currentFood.getName(), numberButton.getNumber(),
                        currentFood.getPrice(), currentFood.getDiscount(),currentFood.getImage(),currentFood.getEmail()
                ));
                //delete if probems
                //inventory rough code not useful
                if(currentFood.getQuantity() > Integer.valueOf(numberButton.getNumber()) )
                {
                    double balance = currentFood.getQuantity() - Integer.valueOf(numberButton.getNumber());
                    //set to database
                    Map<String ,Object> update_balance = new HashMap<>();
                    update_balance.put("quantity",balance);

                    //get instance and put
                    FirebaseDatabase.getInstance().getReference("Foods")
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
                Toast.makeText(FoodDetail.this, "INVENTORY UPDATED", Toast.LENGTH_LONG).show();
                                                        currentFood = dataSnapshot.getValue(Food.class);

                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                    }
                                }
                            });
                    Toast.makeText(FoodDetail.this,"Item was added to cart",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(FoodDetail.this, "INVENTORY EMPTY", Toast.LENGTH_LONG).show();
                }


            }
        });
//        currentFood.getQuantity()-1.0;
        btnCart.setCount(new Database(this).getCountCart());
        food_description = findViewById(R.id.food_description);
        food_price = findViewById(R.id.food_price);
        food_video = findViewById(R.id.food_video);
        food_recepie = findViewById(R.id.food_recepie);
        food_name = findViewById(R.id.food_name);
        food_image= findViewById(R.id.img_food);
        collapsingToolbarLayout= findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandededAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);


        if (getIntent()!= null)
            foodId=getIntent().getStringExtra("FoodId");
        if(!foodId.isEmpty())
        {
            if (Common.isConnectedToInternet(getBaseContext())){
                getDetailFood(foodId);
                getRatingFood(foodId);}
            else
                Toast.makeText(FoodDetail.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
        }
    }

    private void getRatingFood(String foodId) {
        Query foodRating = ratingTbl.orderByChild("foodId").equalTo(foodId);
        foodRating .addValueEventListener(new ValueEventListener() {
            int count = 0,sum=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Rating item = postSnapshot.getValue(Rating.class);
                    sum+=Integer.parseInt(Objects.requireNonNull(item).getRateValue());
                    count++;
                }
                if(count != 0) {
                    float average = sum / count;
                    ratingBar.setRating(average);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very bad","Needs Improvement","OK","Very Good","Above Expectations"))
                .setDefaultRating(1)
                .setTitle("Please Rate Our Food!")
                .setDescription("Select a star and give review")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Provide Feedback Here")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(FoodDetail.this)
                .show();

    }



    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName ());
                food_description.setText(currentFood.getDescription());
                food_video.setText(currentFood.getVideo ());
                food_recepie.setText(currentFood.getRecepixes());


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPositiveButtonClicked(int value, @NotNull String comments) {
        //upload rating to firebase
        final Rating rating = new Rating(Common.currentUser.getName(),
                foodId,String.valueOf(value),comments);
        ratingTbl.push()
                .setValue(rating)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(FoodDetail.this,"Thank you for your feedback!!",Toast.LENGTH_LONG).show();
                    }
                });


//old code user can rate only once
//            ratingTbl.child(Common.currentUser.getPhone()).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if(dataSnapshot.child(Common.currentUser.getPhone()).exists())
//                    {
//                        //remove old and update
//                        ratingTbl.child(Common.currentUser.getPhone()).removeValue();
//                        ratingTbl.child(Common.currentUser.getPhone()).setValue(rating);
//
//                    }else
//                    {
//                        ratingTbl.child(Common.currentUser.getPhone()).setValue(rating);
//                    }
//
//
//                }
//
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//
//
//            });
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }
}
