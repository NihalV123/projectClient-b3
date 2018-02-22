package a123.vaidya.nihal.foodcrunchclient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Model.User;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    MaterialEditText edtNmae,edtPhone,edtPasswd;
    Button BtnSignin;
    Button BtnSignIn,BtnSignUp,btnTest1,btnTest2,btnTest3,
            btnTest4,btnTest5,btnTest6,btnTest7,btnTest8;
    TextView txtSlogan;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnSignIn= findViewById(R.id.btnSignin);
        BtnSignUp= findViewById(R.id.btnSignup);

//        btnTest1= findViewById(R.id.btntest1);
//        btnTest2= findViewById(R.id.btntest2);
//        btnTest3= findViewById(R.id.btntest3);
//        btnTest4= findViewById(R.id.btntest4);


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

                         user.setPhone(phone);

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
            return;
        }


    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
