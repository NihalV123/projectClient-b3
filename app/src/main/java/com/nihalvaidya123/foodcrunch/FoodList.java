package com.nihalvaidya123.foodcrunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nihalvaidya123.foodcrunch.Interface.ItemClickListener;
import com.nihalvaidya123.foodcrunch.Model.Food;
import com.nihalvaidya123.foodcrunch.ViewHolder.FoodViewHolder;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId="";

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //firebase code
        database = FirebaseDatabase.getInstance();
        foodList =database.getReference("Foods");

        recycler_menu = findViewById(R.id.recycler_food);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        //get intent
        if (getIntent()!=null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty()&&categoryId != null)
        {
            loadListFood(categoryId);
        }

    }
//select * from foods where menuid=&
    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,FoodViewHolder.class,foodList.orderByChild("MenuId")
                .equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);

                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void ocClick(View v, int position, boolean isLongClick) {
                        //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        //this is the third activity
                        Intent foodDetail = new Intent(FoodList.this,FoodDetail.class);
                        //send to new activity
                                foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                                startActivity(foodDetail);


                    }
                });

            }
        };

        //set adapter
        recycler_menu.setAdapter(adapter);


    }
}
