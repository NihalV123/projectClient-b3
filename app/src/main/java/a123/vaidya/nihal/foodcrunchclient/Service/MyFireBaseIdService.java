package a123.vaidya.nihal.foodcrunchclient.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import a123.vaidya.nihal.foodcrunchclient.Common.Common;
import a123.vaidya.nihal.foodcrunchclient.Model.Token;

public class MyFireBaseIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String tokenRefreshed = FirebaseInstanceId.getInstance().getToken();
        if(Common.currentUser != null)
        updateTokenToFirebase(tokenRefreshed);
    }

    private void updateTokenToFirebase(String tokenRefreshed) {
        if(Common.currentUser != null){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Tokens");
        Token token = new Token(tokenRefreshed,false); //false as this reads frm client
        tokens.child(Common.currentUser.getPhone()).setValue(token);


    }}

}
