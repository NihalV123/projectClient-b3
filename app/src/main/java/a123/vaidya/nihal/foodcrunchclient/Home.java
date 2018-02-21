package a123.vaidya.nihal.foodcrunchclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchclient.Model.Category;
//import a123.vaidya.nihal.foodcrunchclient.Service.ListenOrder;
import a123.vaidya.nihal.foodcrunchclient.Model.Token;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.MenuViewHolder;
import io.paperdb.Paper;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;
    RecyclerView recycler_menu;
    MaterialEditText edtHomeAddress,edtPassword;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //firebase
        Paper.init(this);
        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this,Cart.class);
                startActivity(cartIntent);

                //testing fab with bottom toast
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getName());

        //load da menu
        recycler_menu = findViewById(R.id.recycler_menu);
        //final MaterialEditText edtHomeAddress = (MaterialEditText) home_address_layout.findViewById(R.id.edtHomeAddress);
        //edtPassword = findViewById(R.id.edtPasswd);
        recycler_menu.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        if (Common.isConnectedToInternet(this)) {

            loadMenu();
        }
        else
        {
            Toast.makeText(this,"Please check your internet connection",Toast.LENGTH_LONG).show();
            return;
        }
        //registeration of notification service
        updateToken(FirebaseInstanceId.getInstance().getToken());
//        Intent service = new Intent(Home.this, ListenOrder.class);
//        startService(service);
    }

    private void updateToken(String token) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Tokens");
        Token data = new Token(token,false);//false as this reads frm client
        try {
            tokens.child(Common.currentUser.getPhone()).setValue(data);
            Toast.makeText(Home.this,"You logged with staff account",Toast.LENGTH_LONG).show();
            //tokens.child(Common.currentUser.getIsStaff());
        }
        catch(Exception e){
            Toast.makeText(Home.this,"Welcome !!!",Toast.LENGTH_LONG).show();
        }
    }

    private void loadMenu() {

        adapter = new FirebaseRecyclerAdapter<Category,
                MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View v, int position, boolean isLongClick) {
                        //category is key
                        Intent foodList = new Intent (Home.this,FoodList.class);
                        foodList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodList);

                        //Toast.makeText(Home.this,""+clickItem.getName(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == R.id.refresh)
        {
            loadMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            Toast.makeText(Home.this,"You are already in main menu",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent (Home.this,Cart.class);
            startActivity(cartIntent);
        } else if (id == R.id.nav_orders) {
            Intent orderIntent = new Intent (Home.this,OrderStatus.class);
            startActivity(orderIntent);
        } else if (id == R.id.nav_logout) {
            //delete remmbered user details
            Toast.makeText(Home.this,"Logging out",Toast.LENGTH_LONG).show();
            Paper.book().destroy();
            Intent signIn = new Intent (Home.this,Signin.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }
        else if (id == R.id.nav_favorites) {
            //delete remmbered user details
            Paper.book().destroy();
            Intent signIn = new Intent (Home.this,Signin.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }
        else if (id == R.id.nav_homeaddress) {
            showHomeAddressDialog();
        }
        else if (id == R.id.nav_emailaddress) {
            //delete remmbered user details
            Paper.book().destroy();
            Intent signIn = new Intent (Home.this,Signin.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }
        else if (id == R.id.nav_password) {
            //delete remmbered user details
            Paper.book().destroy();
            Intent signIn = new Intent (Home.this,Signin.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }
        else if (id == R.id.settings) {
            //delete remmbered user details
            Paper.book().destroy();
            Intent signIn = new Intent (Home.this,Signin.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showHomeAddressDialog() {
        AlertDialog.Builder alertDailog = new AlertDialog.Builder(Home.this);
        alertDailog.setTitle("CHANGE HOME ADDRESS");
        alertDailog.setMessage("Please fill all fields");


        LayoutInflater inflater = LayoutInflater.from(this);
        View layout_home = inflater.inflate(R.layout.home_address_layout,null);



    }
}
