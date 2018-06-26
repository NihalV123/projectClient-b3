package com.nihalvaidya123.foodcrunch;

import android.app.ProgressDialog;
import android.content.Intent;
<<<<<<< HEAD
<<<<<<< HEAD
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
=======
=======
>>>>>>> old2/master
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
<<<<<<< HEAD
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
>>>>>>> old1/master
=======
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
>>>>>>> old2/master
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import android.widget.Toast;
>>>>>>> old1/master
=======
import android.widget.Toast;
>>>>>>> old2/master

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> old2/master
import com.nihalvaidya123.foodcrunch.Model.User;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
<<<<<<< HEAD
>>>>>>> old1/master
=======
>>>>>>> old2/master


public class MainActivity extends AppCompatActivity {

<<<<<<< HEAD
<<<<<<< HEAD
    Button BtnSignIn,BtnSignUp,btnTest1,btnTest2,btnTest3,
            btnTest4,btnTest5,btnTest6,btnTest7,btnTest8;
=======
    TwitterLoginButton loginButton;
    Button BtnSignIn,BtnSignUp,btnTest1,btnTest2,btnTest3,
            btnTest5,btnTest6,btnTest7,btnTest8;
>>>>>>> old1/master
=======
    TwitterLoginButton loginButton;
    Button BtnSignIn,BtnSignUp,btnTest1,btnTest2,btnTest3,
            btnTest5,btnTest6,btnTest7,btnTest8;
>>>>>>> old2/master
    TextView txtSlogan;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
<<<<<<< HEAD
=======
        Twitter.initialize(this);
        MultiDex.install(this);
>>>>>>> old1/master
=======
        Twitter.initialize(this);
>>>>>>> old2/master
        setContentView(R.layout.activity_main);

        BtnSignIn= findViewById(R.id.btnSignin);
        BtnSignUp= findViewById(R.id.btnSignup);

        btnTest1= findViewById(R.id.btntest1);
        btnTest2= findViewById(R.id.btntest2);
        btnTest3= findViewById(R.id.btntest3);
<<<<<<< HEAD
<<<<<<< HEAD
        btnTest4= findViewById(R.id.btntest4);
=======
//        String edtNmae;
//        edtNmae= findViewById(R.id.edtName);

>>>>>>> old1/master
=======
//        String edtNmae;
//        edtNmae= findViewById(R.id.edtName);
//
>>>>>>> old2/master
        btnTest5= findViewById(R.id.btntest5);
        btnTest6= findViewById(R.id.btntest6);
        btnTest7= findViewById(R.id.btntest7);
        btnTest8= findViewById(R.id.btntest8);

        txtSlogan= findViewById(R.id.txtslogan);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtSlogan.setTypeface(face);

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> old2/master
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                login(session);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(MainActivity.this,"Twitter login failed",Toast.LENGTH_SHORT).show();
            }
        });

<<<<<<< HEAD
>>>>>>> old1/master
=======
>>>>>>> old2/master
        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Signup= new Intent(MainActivity.this,Signup.class);
                startActivity(Signup);
            }
        });
        BtnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Signin= new Intent(MainActivity.this,Signin.class);
                startActivity(Signin);
            }
        });
        btnTest1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Signup= new Intent(MainActivity.this,Signup.class);
                startActivity(Signup);
            }
        });
        btnTest2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Signin= new Intent(MainActivity.this,Signin.class);
                startActivity(Signin);
            }
        });
        btnTest3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Signup= new Intent(MainActivity.this,Signup.class);
                startActivity(Signup);
            }
        });
<<<<<<< HEAD
<<<<<<< HEAD
        btnTest4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Signin= new Intent(MainActivity.this,Signin.class);
                startActivity(Signin);
            }
        });
=======

>>>>>>> old1/master
=======

>>>>>>> old2/master
        btnTest5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Signup= new Intent(MainActivity.this,Signup.class);
                startActivity(Signup);
            }
        });
        btnTest6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Signin= new Intent(MainActivity.this,Signin.class);
                startActivity(Signin);
            }
        });
        btnTest7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Signup= new Intent(MainActivity.this,Signup.class);
                startActivity(Signup);
            }
        });
        btnTest8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
<<<<<<< HEAD
<<<<<<< HEAD
                Intent Signin= new Intent(MainActivity.this,Signin.class);
=======
                Intent Signin= new Intent(MainActivity.this,qrMainActivity.class);
>>>>>>> old1/master
=======
                Intent Signin= new Intent(MainActivity.this,qrMainActivity.class);
>>>>>>> old2/master
                startActivity(Signin);
            }
        });
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> old2/master
//
    //linked in hash generator do not delete
//    public void generateHashkey(){
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    PACKAGE,
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//
//                ((TextView) findViewById(R.id.package_name)).setText(info.packageName);
//                ((TextView) findViewById(R.id.hash_key)).setText(Base64.encodeToString(md.digest(),
//                        Base64.NO_WRAP));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.d(TAG, e.getMessage(), e);
//        } catch (NoSuchAlgorithmException e) {
//            Log.d(TAG, e.getMessage(), e);
//        }
//    }

    private void login(TwitterSession session) {
        String username = session.getUserName();
        Intent Signin= new Intent(MainActivity.this,Signup.class);
//        Signin.putExtra("txtFullName",username);
//          User user = new User(edtNmae.getText().toString(),edtPasswd.getText().toString());
//        table_user.child(edtPhone.getText().toString()).setValue(user);

        startActivity(Signin);
    }
<<<<<<< HEAD
>>>>>>> old1/master
=======
>>>>>>> old2/master

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
<<<<<<< HEAD
<<<<<<< HEAD
}
=======
=======
>>>>>>> old2/master

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }



}


<<<<<<< HEAD
>>>>>>> old1/master
=======
>>>>>>> old2/master
