package a123.vaidya.nihal.foodcrunchclient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.util.HashMap;
import java.util.Map;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Interface.ItemClickListener;
import a123.vaidya.nihal.foodcrunchclient.Model.Category;
import a123.vaidya.nihal.foodcrunchclient.Model.Token;
import a123.vaidya.nihal.foodcrunchclient.ViewHolder.MenuViewHolder;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;
    MaterialEditText edtHomeAddress,edtPassword;
    //caligraphy font install
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //caligraphy font init
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/restaurant_font.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MENU");
        setSupportActionBar(toolbar);

        //firebase
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipelayout1);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
        android.R.color.holo_green_dark,
        android.R.color.holo_orange_dark,
        android.R.color.holo_red_dark,
        android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Common.isConnectedToInternet(getBaseContext())) {

                    loadMenu();
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (Common.isConnectedToInternet(getBaseContext())) {

                    loadMenu();
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("6ep60jj09lvUcHncYM3yCoIMr", "WXvH93jw1urHD9IzIk6FDRmKW0X5LGZgmMCDo67XFk2uDf2LGJ"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        Paper.init(this);
        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SpotsDialog dialog = new SpotsDialog(Home.this);
                dialog.show();
                Intent cartIntent = new Intent(Home.this,Cart.class);
                startActivity(cartIntent);
                dialog.dismiss();

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

        //recycler menu customisation
        //layoutManager =new LinearLayoutManager(this);
        //recycler_menu.setLayoutManager(layoutManager);
        recycler_menu.setLayoutManager(new GridLayoutManager(this,1));

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
    }
    private void updateToken(String token) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Tokens");
        Token data = new Token(token,false);//false as this reads frm client
        try {
            tokens.child(Common.currentUser.getPhone()).setValue(data);
            Toast.makeText(Home.this,"Welcome ",Toast.LENGTH_LONG).show();
            //tokens.child(Common.currentUser.getIsStaff());
        }
        catch(Exception e){
            Toast.makeText(Home.this,"Welcome !!!",Toast.LENGTH_LONG).show();
        }
    }

    private void loadMenu() {
        //new firebase code
        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(category, Category.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
            @Override
            public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_item,parent,false);
                return  new MenuViewHolder(itemView);
            }
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder viewHolder, int position, @NonNull Category model) {
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View v, int position, boolean isLongClick) {
                        //category is key
                        final SpotsDialog dialog = new SpotsDialog(Home.this);
                        dialog.show();
                        Intent foodList = new Intent (Home.this,FoodList.class);
                        foodList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodList);
                        dialog.dismiss();
                        //Toast.makeText(Home.this,""+clickItem.getName(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        adapter.startListening();
        recycler_menu.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
//            @Override
//            public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View itemview = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.menu_item,parent,false);
//                return new MenuViewHolder(itemview);
//            }
        };
    @Override
    protected void onStart() {
        super.onStart();
        loadMenu();
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recycler_menu.setAdapter(adapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadMenu();
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recycler_menu.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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
            final SpotsDialog dialog = new SpotsDialog(Home.this);
            Intent cartIntent = new Intent (Home.this,Cart.class);
            dialog.dismiss();
            startActivity(cartIntent);
        } else if (id == R.id.nav_orders) {
            final SpotsDialog dialog = new SpotsDialog(Home.this);
            Intent orderIntent = new Intent (Home.this,OrderStatus.class);
            startActivity(orderIntent);
            dialog.dismiss();
        } else if (id == R.id.nav_logout) {
            //delete remmbered user details
            final SpotsDialog dialog = new SpotsDialog(Home.this);
            Toast.makeText(Home.this,"Logging out",Toast.LENGTH_LONG).show();
            Paper.book().destroy();
            Intent signIn = new Intent (Home.this,Signin.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
            dialog.dismiss();
        }
        else if (id == R.id.nav_favorites) {
            final SpotsDialog dialog = new SpotsDialog(Home.this);
            Intent orderIntent = new Intent (Home.this,OrderStatus.class);
            startActivity(orderIntent);
            dialog.dismiss();
        }
        else if (id == R.id.nav_homeaddress) {
            showHomeAddressDialog();
        }
        else if (id == R.id.nav_emailaddress) {
            showEmailAddressDialog();
        }
        else if (id == R.id.nav_password) {
            showChangePasswordDialog();
        }
        else if (id == R.id.settings) {
            final SpotsDialog dialog = new SpotsDialog(Home.this);
            dialog.show();
            Intent signIn = new Intent (Home.this,Signin.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
            dialog.dismiss();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showEmailAddressDialog() {
        android.app.AlertDialog.Builder alertDailog = new android.app.AlertDialog.Builder(Home.this);
        alertDailog.setTitle("CHANGE EMAIL ADDRESS");
        alertDailog.setMessage("One time per session");
        LayoutInflater inflater = LayoutInflater.from(this);
        View layout_email = inflater.inflate(R.layout.email_address_layout,null);
        MaterialEditText edtEmail = (MaterialEditText)layout_email.findViewById(R.id.edtEmailAddress);
        alertDailog.setView(layout_email);
        alertDailog.setPositiveButton("UPDATE!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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

    private void showChangePasswordDialog() {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
    alertDialog.setTitle("CHANGE PASSWORD");
    alertDialog.setMessage("One time per session");

        LayoutInflater inflater = LayoutInflater.from(this);
        View layout_pwd = inflater.inflate(R.layout.change_password_layout,null);
        final MaterialEditText edtPassword = (MaterialEditText)layout_pwd.findViewById(R.id.edtPassword);
        final MaterialEditText edtNewPassword = (MaterialEditText)layout_pwd.findViewById(R.id.edtNewPassword);
        final MaterialEditText edtRepeatPassword = (MaterialEditText)layout_pwd.findViewById(R.id.edtRepeatNewPassword);

        alertDialog.setView(layout_pwd);
        alertDialog.setPositiveButton("UPDATE!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final SpotsDialog dialog1 = new SpotsDialog(Home.this);
                dialog1.show();
                    //check old password
                if(edtPassword.getText().toString().equals(Common.currentUser.getPassword()))
                {
                    //check new password
                    if((edtNewPassword.getText().toString().equals(edtRepeatPassword.getText().toString()))
                            &&(! edtNewPassword.getText().toString().isEmpty())
                            )
                    {
                        if(! edtNewPassword.getText().toString().equals(edtPassword.getText().toString()))
                        {
                            Map <String, Object> passwordUpdate = new HashMap<>();
                            passwordUpdate.put("Password", edtNewPassword.getText().toString());
                            DatabaseReference user = FirebaseDatabase.getInstance().getReference("User");
                            user.child(Common.currentUser.getPhone())
                                    .updateChildren(passwordUpdate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            dialog1.dismiss();
                                            Toast.makeText(Home.this, "Your password was updated", Toast.LENGTH_LONG).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog1.dismiss();
                                            Toast.makeText(Home.this, "You have a problem buddy", Toast.LENGTH_LONG).show();

                                        }
                                    })
                            ;
                        }
                        else
                        {
                            dialog1.dismiss();
                            Toast.makeText(Home.this,"New password is same as the old one",Toast.LENGTH_LONG).show();
                        }
                    }else
                    {
                        dialog1.dismiss();
                        Toast.makeText(Home.this,"Password doesnt match!\n Please try again",Toast.LENGTH_LONG).show();

                    }
                }else
                {
                    dialog1.dismiss();
                    Toast.makeText(Home.this,"Wrong Password!!\n Please try Again",Toast.LENGTH_LONG).show();
                }
            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void showHomeAddressDialog() {
        AlertDialog.Builder alertDailog = new AlertDialog.Builder(Home.this);
        alertDailog.setTitle("CHANGE HOME ADDRESS");
        alertDailog.setMessage("One time per session");
        LayoutInflater inflater = LayoutInflater.from(this);
        View layout_home = inflater.inflate(R.layout.home_address_layout,null);
        MaterialEditText edtAddress = (MaterialEditText)layout_home.findViewById(R.id.edtHomeAddress);
        alertDailog.setView(layout_home);
        alertDailog.setPositiveButton("UPDATE!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
}
