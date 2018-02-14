package a123.vaidya.nihal.foodcrunchclient;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Model.User;

public class Signin extends AppCompatActivity {

    MaterialEditText edtNmae,edtPhone,edtPasswd;
    Button BtnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        edtNmae= findViewById(R.id.edtName);
        edtPasswd= findViewById(R.id.edtPasswd);
        edtPhone= findViewById(R.id.edtPhone);
        BtnSignin = findViewById(R.id.btnSignin);

        //on with the firebase

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        BtnSignin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user doesnt exist in db
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            //get user info
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);

                            // user.setPhone(edtPhone.getText().toString());

                            if ((user.getPassword().equals(edtPasswd.getText().toString()))
                                //&&(user.getName().equals(edtNmae.getText().toString())) for verifying name and password
                                    ){
                                DatabaseReference myRef = database.getReference("message");
                                myRef.setValue("Hello from sign in ");

                                Intent homeIntent = new Intent(Signin.this,Home.class);
                                 Common.currentUser = user;
                                startActivity(homeIntent);

                            } else {
                                DatabaseReference myRef = database.getReference("message");
                                myRef.setValue("user doesnt exist check phone");
                                Toast.makeText(Signin.this, "Sign in FAILED Check your Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            DatabaseReference myRef = database.getReference("message");
                            myRef.setValue("user doesnt exist from sign in ");
                            Toast.makeText(Signin.this, "User Doesnt exist Please SIGN UP", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
