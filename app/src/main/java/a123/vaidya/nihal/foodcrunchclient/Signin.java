package a123.vaidya.nihal.foodcrunchclient;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.CheckBox;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import java.util.Objects;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Model.User;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Signin extends AppCompatActivity {

    private MaterialEditText edtNmae;
    private MaterialEditText edtPhone;
    private MaterialEditText edtPasswd;
    private Button BtnSignin;
    private CheckBox remember_button;

    private FirebaseDatabase database;
    private DatabaseReference table_user;
    //caligraphy font install
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //caligraphy font init
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/restaurant_font.otf")
//                .setFontAttrId(R.attr.fontPath)
//                .build());
        setContentView(R.layout.activity_signin);
        edtNmae= findViewById(R.id.edtName);
        edtPasswd= findViewById(R.id.edtPasswd);
        edtPhone= findViewById(R.id.edtPhone);
        BtnSignin = findViewById(R.id.btnSignin);
        remember_button = findViewById(R.id.remember_button);

        //firebase code
        Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("6ep60jj09lvUcHncYM3yCoIMr", "WXvH93jw1urHD9IzIk6FDRmKW0X5LGZgmMCDo67XFk2uDf2LGJ"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        Paper.init(this);
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        TextView txtForgetPwd = findViewById(R.id.forget_password_txt);
        txtForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showForgetPwdDailog();
            }
        });

        BtnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedToInternet(getBaseContext())) {
                //save user phone and password
                    if(remember_button.isChecked())
                    {
                        Paper.book().write(Common.USER_KEY,edtPhone.getText().toString());
                        Paper.book().write(Common.PWD_KEY,edtPasswd.getText().toString());
                    }

                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //check if user doesnt exist in db
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                //get user info

                                    User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);

                                    user.setPhone(edtPhone.getText().toString());

                                if ((Objects.requireNonNull(user).getPassword().equals(edtPasswd.getText().toString()))
                                    //&&(Common.currentUser.getName().equals(edtNmae.getText().toString())) //for verifying name and password
                                        ) {
                                    DatabaseReference myRef = database.getReference("message");
                                    myRef.setValue("Hello from sign in ");
                                    final SpotsDialog dialog = new SpotsDialog(Signin.this);
                                    dialog.show();
                                    Intent homeIntent = new Intent(Signin.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    dialog.dismiss();
                                    finish();
                                    table_user.removeEventListener(this);

                                } else {
                                    DatabaseReference myRef = database.getReference("message");
                                    myRef.setValue("user doesnt exist check phone");
                                    Toast.makeText(Signin.this, "Sign in FAILED Check your Credentials", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                DatabaseReference myRef = database.getReference("message");
                                myRef.setValue("user doesnt exist from sign in ");
                                Toast.makeText(Signin.this, "User Doesnt exist Please SIGN UP", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else
                {
                    Toast.makeText(Signin.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void showForgetPwdDailog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");
        builder.setCancelable(false);
        builder.setMessage("Please enter your secure code");

        LayoutInflater inflater = this.getLayoutInflater();
        View forget_view = inflater.inflate(R.layout.forgot_passowrd_layout,null);
        builder.setView(forget_view);
        builder.setIcon(R.drawable.ic_security_black_24dp);
        final MaterialEditText edtPhone = forget_view.findViewById(R.id.edtPhone);
        final MaterialEditText edtSecureCode = forget_view.findViewById(R.id.edtSecureCode);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                        if(Objects.requireNonNull(user).getSecureCode().equals(edtSecureCode.getText().toString()))
                        {
                            Toast.makeText(Signin.this,"Your passwrod is "+user.getPassword(),Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(Signin.this,"WRONG SECURE CODE !!!",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }
}
