package com.nihalvaidya123.foodcrunch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    Button BtnSignIn,BtnSignUp,btnTest1,btnTest2,btnTest3,
            btnTest4,btnTest5,btnTest6,btnTest7,btnTest8;
    TextView txtSlogan;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnSignIn=(Button)findViewById(R.id.btnSignin);
        BtnSignUp=(Button)findViewById(R.id.btnSignup);

        btnTest1=(Button)findViewById(R.id.btntest1);
        btnTest2=(Button)findViewById(R.id.btntest2);
        btnTest3=(Button)findViewById(R.id.btntest3);
        btnTest4=(Button)findViewById(R.id.btntest4);
        btnTest5=(Button)findViewById(R.id.btntest5);
        btnTest6=(Button)findViewById(R.id.btntest6);
        btnTest7=(Button)findViewById(R.id.btntest7);
        btnTest8=(Button)findViewById(R.id.btntest8);

        txtSlogan=(TextView)findViewById(R.id.txtslogan);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtSlogan.setTypeface(face);

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
        btnTest4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Signin= new Intent(MainActivity.this,Signin.class);
                startActivity(Signin);
            }
        });
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
                Intent Signin= new Intent(MainActivity.this,Signin.class);
                startActivity(Signin);
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
