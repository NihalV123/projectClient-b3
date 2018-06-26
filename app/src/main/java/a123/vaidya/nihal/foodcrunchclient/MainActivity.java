package a123.vaidya.nihal.foodcrunchclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
import android.content.SharedPreferences;
=======
>>>>>>> old/master
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Bundle;
<<<<<<< HEAD
import android.preference.PreferenceManager;
=======
>>>>>>> old/master
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Model.User;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private MaterialEditText edtNmae;
    private MaterialEditText edtPhone;
    private MaterialEditText edtPasswd;
    private Button BtnSignin;
    private Button BtnSignIn;
    private Button BtnSignUp;
    private TwitterLoginButton twitterLoginButton;
    private TextView txtSlogan;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
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
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("6ep60jj09lvUcHncYM3yCoIMr",
                        "WXvH93jw1urHD9IzIk6FDRmKW0X5LGZgmMCDo67XFk2uDf2LGJ"))
                .debug(true)
                .build();
        Twitter.initialize(config);

        BtnSignIn= findViewById(R.id.btnSignin);
        BtnSignUp= findViewById(R.id.btnSignup);

        twitterLoginButton = findViewById(R.id.twitter_login_button);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                Toast.makeText(MainActivity.this,"You are registered with twitter",Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(MainActivity.this,"Please install twitter or try normal sign in",Toast.LENGTH_LONG).show();
            }
        });

            printKeyHash();
<<<<<<< HEAD



        //app intro
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart = getPref.getBoolean("firstStart",true);
                if(isFirstStart)
                {
                    startActivity(new Intent(MainActivity.this,MyIntro.class));
                    SharedPreferences.Editor e= getPref.edit();
                    e.putBoolean("firstStart",false);
                    e.apply();
                }

            }
        });

        thread.start();


=======
>>>>>>> old/master
        txtSlogan= findViewById(R.id.txtslogan);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtSlogan.setTypeface(face);

        //cache user details
        Paper.init(this);

        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final SpotsDialog dialog = new SpotsDialog(MainActivity.this);
                dialog.show();
                Intent Signup= new Intent(MainActivity.this,Signup.class);
                startActivity(Signup);
                dialog.dismiss();
            }
        });
        BtnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final SpotsDialog dialog = new SpotsDialog(MainActivity.this);
                dialog.show();
                Intent Signin= new Intent(MainActivity.this,Signin.class);
                startActivity(Signin);
                dialog.dismiss();
            }
        });
        //check user details
        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if(user != null && pwd != null)
        {
            if(!user.isEmpty() && !pwd.isEmpty())
                login(user,pwd);
        }
    }
//important for social logins search key in logcat for sha1
    private void printKeyHash() {
    try{
        PackageInfo info =getPackageManager().getPackageInfo("a123.vaidya.nihal.foodcrunchclient",
                PackageManager.GET_SIGNATURES);

        for(Signature signature:info.signatures)
        {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());
            Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
        }
    } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
        e.printStackTrace();
    }
    }

    private void twitterlogin(TwitterSession session) {
            String username = session.getUserName();
        Intent Signin_twitter= new Intent(MainActivity.this,Signin.class);
        startActivity(Signin_twitter);
    }

    private void login(final String phone, final String pwd) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        edtNmae= findViewById(R.id.edtName);
        edtPasswd= findViewById(R.id.edtPasswd);
        edtPhone= findViewById(R.id.edtPhone);
        BtnSignin = findViewById(R.id.btnSignin);

        if (Common.isConnectedToInternet(getBaseContext())) {
            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //check if user doesnt exist in db
                    if (dataSnapshot.child(phone).exists()) {
                        //get user info
                        User user = dataSnapshot.child(phone).getValue(User.class);

                         Objects.requireNonNull(user).setPhone(phone);

                        if ((user.getPassword().equals(pwd))) {
                            DatabaseReference myRef = database.getReference("message");
                            myRef.setValue("Hello from sign in ");
                            final SpotsDialog dialog = new SpotsDialog(MainActivity.this);
                            dialog.show();
                            Intent homeIntent = new Intent(MainActivity.this, Home.class);
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            dialog.dismiss();

                        } else {
                            DatabaseReference myRef = database.getReference("message");
                            myRef.setValue("user doesnt exist check phone");
                            Toast.makeText(MainActivity.this, "Sign in FAILED Check your Credentials", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        DatabaseReference myRef = database.getReference("message");
                        myRef.setValue("user doesnt exist from sign in ");
                        Toast.makeText(MainActivity.this, "User Doesnt exist Please SIGN UP", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else
        {
            Toast.makeText(MainActivity.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Twitter.initialize(this);
        final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;
        TwitterAuthClient authClient = new TwitterAuthClient();
        authClient.requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                // Do something with the result, which provides the email address
                twitterlogin(session);
                Toast.makeText(MainActivity.this,"You are registered with twitter",Toast.LENGTH_LONG).show();

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(MainActivity.this,"Please install twitter or try normal sign in",Toast.LENGTH_LONG).show();

            }
        });
        Toast.makeText(MainActivity.this,"You are registered w twitter",Toast.LENGTH_LONG).show();
        // Pass the activity result to the login button.
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
