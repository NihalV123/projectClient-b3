package a123.vaidya.nihal.foodcrunchclient;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Database.Database;
import a123.vaidya.nihal.foodcrunchclient.Helper.RecyclerItemTouchHelper;
import a123.vaidya.nihal.foodcrunchclient.Interface.RecyclerItemTouchHelperListener;
import a123.vaidya.nihal.foodcrunchclient.Model.Favorites;
import a123.vaidya.nihal.foodcrunchclient.Model.Order;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.CartViewHolder;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.FavoritesAdapter;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.FavoritesViewHolder;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ShowFavorites extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    private RecyclerView recycler_menu;
    private RecyclerView.LayoutManager layoutManager;

    FavoritesAdapter adapter;
    RelativeLayout rootlayout;
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
        setContentView(R.layout.activity_show_favorites);
        recycler_menu = findViewById(R.id.recycler_favorite);
        rootlayout = (RelativeLayout) findViewById(R.id.rootLayoutfav);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        //       swipe to delete view init
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new
                RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler_menu);

        loadFavorites();
    }

    private void loadFavorites() {
    adapter = new FavoritesAdapter(this,new Database(this).getAllFavorites(Common.currentUser.getPhone()));
    recycler_menu.setAdapter(adapter);


    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof FavoritesViewHolder)
        {
            String name = ((FavoritesAdapter)recycler_menu.getAdapter()).getItem(position).getFoodName();
            final Favorites deleteItem = ((FavoritesAdapter)recycler_menu.getAdapter()).getItem(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();
            adapter.removeitem(viewHolder.getAdapterPosition());
            //update db
            new Database(getBaseContext()).removeFromFavorites(deleteItem.getFoodId(), Common.currentUser.getPhone());
            //snackbar with undo
            Snackbar snackbar = Snackbar.make(rootlayout,name+"  was removed from cart ",Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.restoreitem(deleteItem,deleteIndex);
                    //add to cart
                    new Database(getBaseContext()).addToFavorites(deleteItem);

                }
            });
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.show();

        }
    }
}
