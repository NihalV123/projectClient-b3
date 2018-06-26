package com.nihalvaidya123.foodcrunch;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import android.content.Intent;
>>>>>>> old1/master
=======
import android.content.Intent;
>>>>>>> old2/master
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import com.nihalvaidya123.foodcrunch.Common.Common;
>>>>>>> old1/master
=======
import com.nihalvaidya123.foodcrunch.Common.Common;
>>>>>>> old2/master
import com.nihalvaidya123.foodcrunch.Model.User;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    MaterialEditText edtNmae,edtPhone,edtPasswd;
    Button BtnSignup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtNmae= findViewById(R.id.edtName);
        edtPasswd= findViewById(R.id.edtPasswd);
        edtPhone= findViewById(R.id.edtPhone);
        BtnSignup = findViewById(R.id.btnSignup);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("User");


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        BtnSignup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user doesnt exist in db

                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            DatabaseReference myRef = database.getReference("message");

                            myRef.setValue("Hello from sign up");

                            Toast.makeText(Signup.this, "Phone Number already registered!", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            User user = new User(edtNmae.getText().toString(),edtPasswd.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            DatabaseReference myRef = database.getReference("message");

<<<<<<< HEAD
<<<<<<< HEAD
                            myRef.setValue("everythink ok");
                            Toast.makeText(Signup.this, "SIGN UP successfull welcome to the crew!", Toast.LENGTH_SHORT).show();
                            finish();
=======
=======
>>>>>>> old2/master


                            myRef.setValue("everythink ok");
                            Toast.makeText(Signup.this, "SIGN UP successfull welcome to the crew!", Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(Signup.this,Home.class);
                            Common.currentUser = user;
                            startActivity(homeIntent);
                           // finish();
<<<<<<< HEAD
>>>>>>> old1/master
=======
>>>>>>> old2/master
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        //firebase debug
        mAuth =FirebaseAuth.getInstance();


<<<<<<< HEAD
<<<<<<< HEAD

    }
//private void registerUser(){
//
//}
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btnSignup:
//                registerUser();
//                break;
//            case R.id.btnSignin:
//
//                break;
//        }
//    }
=======
    }
>>>>>>> old1/master
=======
    }
>>>>>>> old2/master
}

