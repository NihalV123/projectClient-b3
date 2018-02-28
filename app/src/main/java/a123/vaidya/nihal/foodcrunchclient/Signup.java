package a123.vaidya.nihal.foodcrunchclient;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Model.User;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Signup extends AppCompatActivity {

    private MaterialEditText edtNmae;
    private MaterialEditText edtPhone;
    private MaterialEditText edtPasswd;
    private MaterialEditText edtSecureCode;
    MaterialEditText edtEmail;
    MaterialEditText edtHomeAddress;
    private Button BtnSignup;

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
        setContentView(R.layout.activity_signup);
        edtEmail= findViewById(R.id.edtEmail);
        edtNmae= findViewById(R.id.edtName);
        edtHomeAddress= findViewById(R.id.edtHomeAddress);
        edtPasswd= findViewById(R.id.edtPasswd1);
        edtPhone= findViewById(R.id.edtPhone);
        Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("6ep60jj09lvUcHncYM3yCoIMr", "WXvH93jw1urHD9IzIk6FDRmKW0X5LGZgmMCDo67XFk2uDf2LGJ"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        BtnSignup = findViewById(R.id.btnSignup);
        edtSecureCode=findViewById(R.id.edtSecureCode);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("User");


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        BtnSignup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (Common.isConnectedToInternet(getBaseContext())) {
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //check if user doesnt exist in db

                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                DatabaseReference myRef = database.getReference("message");

                                myRef.setValue("Hello from sign up");

                                Toast.makeText(Signup.this, "Phone Number already registered!",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Signup.this, "Select the way you want to be notified",
                                        Toast.LENGTH_SHORT).show();
                                User user = new User(edtNmae.getText().toString(), edtPasswd.getText().toString(),
                                        edtSecureCode.getText().toString(),
                                        edtHomeAddress.getText().toString(),
                                        edtEmail.getText().toString()

                                        );
                                table_user.child(edtPhone.getText().toString()).setValue(user);
                                DatabaseReference myRef = database.getReference("message");

                                myRef.setValue("everythink ok");

                                String[] CC = {user.getEmail().toString()};
                                String[] TO = {user.getEmail().toString()};
                                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                                emailIntent.setData(Uri.parse("mailto:"));
                                emailIntent.setType("text/plain");
                                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "You have created an account with FoodCrunch the anytime shopping app");
                                emailIntent.putExtra(Intent.EXTRA_TEXT, "Here are your account details \n"+
                                        "The new account is created for the user \t" +
                                        (user.getName().toString())+
                                        "\n with email \t" +
                                        (user.getEmail().toString())+
                                        "\nand has been linked to your phone number \n" +
                                        "\n \n Your password is  \t" +
                                        (user.getPassword().toString())+
                                        "\n \n and your secure code is \t" +
                                        (user.getSecureCode().toString())+
                                        "\n\nPlease write down your secure code it will be used to recover your password");
                                try {
                                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                                } catch (android.content.ActivityNotFoundException ex) {
                                }

                                Toast.makeText(Signup.this, "SIGN UP successfull Please log in!!", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else
                {
                    Toast.makeText(Signup.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
                }
            }
        });

        //firebase debug
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

    }

}

