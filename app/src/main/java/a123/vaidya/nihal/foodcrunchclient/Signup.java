package a123.vaidya.nihal.foodcrunchclient;
import android.content.Context;
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
    //MaterialEditText edtEmail;
    private Button BtnSignup;

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
        setContentView(R.layout.activity_signup);

        edtNmae= findViewById(R.id.edtName);
        edtPasswd= findViewById(R.id.edtPasswd);
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

                                Toast.makeText(Signup.this, "Phone Number already registered!", Toast.LENGTH_SHORT).show();

                            } else {
                                User user = new User(edtNmae.getText().toString(), edtPasswd.getText().toString(),
                                       // edtNmae.getText().toString(),
                                        edtSecureCode.getText().toString()
                                        );
                                table_user.child(edtPhone.getText().toString()).setValue(user);
                                DatabaseReference myRef = database.getReference("message");

                                myRef.setValue("everythink ok");
                                Toast.makeText(Signup.this, "SIGN UP successfull welcome to the crew!", Toast.LENGTH_SHORT).show();
                                finish();
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

