package a123.vaidya.nihal.foodcrunchclient.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Database.Database;
import a123.vaidya.nihal.foodcrunchclient.FoodDetail;
import a123.vaidya.nihal.foodcrunchclient.FoodList;
import a123.vaidya.nihal.foodcrunchclient.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchclient.Model.Favorites;
import a123.vaidya.nihal.foodcrunchclient.Model.Food;
import a123.vaidya.nihal.foodcrunchclient.Model.Order;
import a123.vaidya.nihal.foodcrunchclient.R;

public class FavoritesAdapter  extends RecyclerView.Adapter<FavoritesViewHolder>{

    public String foodId="";
    private ShareDialog shareDialog;
    private Context context;
    private CallbackManager callbackManager;
    private TextView textView;
    private List<Favorites> favoritesList;

    public FavoritesAdapter(Context context, List<Favorites> favoritesList) {
        this.context = context;
        this.favoritesList = favoritesList;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context)
                .inflate(R.layout.favorite_item,parent,false);
        new CallbackManager.Factory();
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog((Activity) context);
        return new FavoritesViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder viewHolder, final int position) {
        viewHolder.food_name.setText(favoritesList.get(position).getFoodName());
        viewHolder.food_price.setText(String.format("INR :  %s",favoritesList.get(position).getFoodPrice().toString()));
        //viewHolder.ratingbar.setRating(Float.parseFloat(.getRateValue()));
        Picasso.with(context).load(favoritesList.get(position).getFoodImage())
                .into(viewHolder.food_image);
        final Double item = 100.0;
        //quick cart button here


        viewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(context,"SHARE SUCCESS ",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(context,"SHARE CANCEL ",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(context,"SOMETHING IS NOT RIGHT ",Toast.LENGTH_SHORT).show();

                    }
                });

//                Picasso.with(getApplicationContext())
//                        .load(model.getImage())
//                        .into(target);
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("This is useful")
                        .setContentUrl(Uri.parse("https://youtube.com"))
                        .build();
                if(ShareDialog.canShow(ShareLinkContent.class))
                {
                    shareDialog.show(linkContent);
                }


                Toast.makeText(context,"Getting Ready ",Toast.LENGTH_SHORT).show();
            }
        });





        viewHolder.add_to_cart.setOnClickListener(new View.OnClickListener() {
            int clickcount = 0;
            @Override
            public void onClick(View v) {
                boolean isExist =new Database(context).checkFoodExist(favoritesList.get(position).getFoodId(),
                        Common.currentUser.getPhone());
                favoritesList.get(position).setFoodQuantity(item);
                //clicker code strt
                clickcount = clickcount - 1;
                if ((favoritesList.get(position).getFoodQuantity()+50) > clickcount) {
                    double balance =item + clickcount;
                    Map<String, Object> update_balance = new HashMap<>();
                    update_balance.put("quantity", balance);
                    foodId = favoritesList.get(position).getFoodName();
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
                                                        Toast.makeText(context, "INVENTORY UPDATED",
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
                    Toast.makeText(context, "Item was added to cart", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "INVENTORY EMPTY", Toast.LENGTH_LONG).show();
                }



                if (!isExist) {
                    new Database(context).addToCart(new Order(Common.currentUser.getPhone(),
                            favoritesList.get(position).getFoodId(),//this gets the random id of food id this took me 2 days to debug lol
                            favoritesList.get(position).getFoodName(),
                            "1",
                            favoritesList.get(position).getFoodPrice(),
                            favoritesList.get(position).getFoodDiscount(),
                            favoritesList.get(position).getFoodImage(),
                            favoritesList.get(position).getFoodEmail()
                    ));
                   Toast.makeText(context,"ITEM  "+favoritesList.get(position).getFoodName().toString()+" WAS ADDED TO CART ",Toast.LENGTH_SHORT).show();
                }//isexist end here
                else {
                    new Database(context).increaseCart(Common.currentUser.getPhone(), favoritesList.get(position).getFoodId());

                }

            }
        });

        final Favorites local = favoritesList.get(position);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                //this is the third activity
                Intent foodDetail = new Intent(context,FoodDetail.class);
                //send to new activity
                foodDetail.putExtra("FoodId",favoritesList.get(position).getFoodId());
                context.startActivity(foodDetail);


            }
        });

    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public void removeitem(int position)
    {
        favoritesList.remove(position);
        notifyItemRemoved(position);

    }

    public void restoreitem(Favorites item,int position)
    {
        favoritesList.add(position,item);
        notifyItemInserted(position);
    }

    public Favorites getItem(int position)
    {
        return favoritesList.get(position);
    }


//    public Context getApplicationContext() {
//        return applicationContext;
//    }
}
