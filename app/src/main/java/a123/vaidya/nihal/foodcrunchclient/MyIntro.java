package a123.vaidya.nihal.foodcrunchclient;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

public class MyIntro extends AppIntro2{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntro2Fragment.newInstance("FOOD CRUNCH","SEE WHATS POPULAR"
                ,R.drawable.ic_whatshot_big,
                Color.parseColor("#ff774f")));

        addSlide(AppIntro2Fragment.newInstance("FOOD CRUNCH","CUSTOMISE YOUR CART"
                ,R.drawable.ic_add_shopping_cart_big,
                Color.parseColor("#e38c50")));

        addSlide(AppIntro2Fragment.newInstance("FOOD CRUNCH","SEE DETAILS OF YOUR ORDER"
                ,R.drawable.ic_room_service_big,
                Color.parseColor("#ff774f")));

        addSlide(AppIntro2Fragment.newInstance("FOOD CRUNCH","NEW USER IS CREDITED RS 100 FOR THE LAUNCH MONTH"
                ,R.drawable.ic_monetization_on_big,
                Color.parseColor("#e38c50")));

            showStatusBar(false);
          //  setBarColor(Color.parseColor("#333639"));

    }

    @Override
    public void onDonePressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        Toast.makeText(MyIntro.this,"You cannot skip from here HEHE ;) ,",Toast.LENGTH_LONG).show();
    }

}
