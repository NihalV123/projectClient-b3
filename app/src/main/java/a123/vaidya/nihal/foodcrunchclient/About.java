package a123.vaidya.nihal.foodcrunchclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Element  addsElement = new Element();
        addsElement.setTitle("DETAILS  ");
        View about = new AboutPage(this)
                .isRTL(false)
                //.setImage(R.drawable.box)
                .setDescription("THIS IS  A DUMMY PROJECT \nCREATED BY NIHAL VAIDYA TYCS\n ROLL NO 518")
                .addItem(new Element().setTitle("Verion 0.8"))
                .addItem(addsElement)
                .addGroup("Connect with me")
                .addEmail("nhlvcam@gmail.com")
                .addWebsite("http://www.google.com")
                .addFacebook("Nihal Vaidya ")
                .addTwitter("nhlvcam")
                .addYoutube("UCRZ7xBsehvpfquJtWpvSY1w")
                .addPlayStore("com.ideashower.readitlater.pro")
                .addInstagram("nihal7210\n")
                .addGitHub("netra001")
                .addItem(createCopyRight())
                .create();
        setContentView(about);

    }

    private Element createCopyRight() {
        Element copyright = new Element();
        final String cpoyrightstring = String.format("Copyright free since %d by Nihal Vaidya", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(cpoyrightstring);
     copyright.setIconDrawable(R.drawable.ic_child_care_black_24dp);
     copyright.setGravity(Gravity.CENTER);
     copyright.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Toast.makeText(About.this,cpoyrightstring,Toast.LENGTH_LONG).show();
         }
     });
     return copyright;
    }
}
