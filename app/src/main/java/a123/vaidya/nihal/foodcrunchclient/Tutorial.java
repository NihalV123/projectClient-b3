package a123.vaidya.nihal.foodcrunchclient;

import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class Tutorial extends TutorialActivity {
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        setIndicatorSelected(R.drawable.ic_adjust_black_24dp); // Indicator drawable when selected
//        setIndicator(R.drawable.ic_crop_square_black_24dp); // Indicator drawable
        addFragment(new Step.Builder().setTitle("SEARCH BUTTON")
                .setContent("YOU CAN SEARCH ALL FOOD USING SEARCH")
                .setBackgroundColor(R.color.colorPrimary) // int background color
                .setDrawable(R.drawable.ic_search_black_24dp) // int top drawable
                .setSummary("YOU CAN TYPE TO GET SUGGESTIONS OR CLICK ON RECENT SUGGESTIONS AND " +
                        "PRESS ENTER THIS IS ALSO AVAILABLE FOR EACH FOOD CATEGORY AND PRE POPULATED WITH TOP SEARCHES")
                .build());
        addFragment(new Step.Builder().setTitle("REFRESH BUTTON")
                .setContent("YOU CAN REFRESH LAYOUT USING REFRESH BUTTON")
                .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_refresh_black_24dp) // int top drawable
                .setSummary("YOU CAN REFRESH WHEN ADDING OR REMOVING CATEGORIES OR WHEN IMAGE IS NOT BEING ETCHED FROM SERVER ")
                .build());
        addFragment(new Step.Builder().setTitle("GO TO CART BUTTON")
                .setContent("YOU CAN QUICKLY GO TO CART USING THIS BUTTON")
                .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_shopping_cart_black_24dp) // int top drawable
                .setSummary("THIS BUTTON SHOWS NUMBER OF ITEMS IN CART IN SMALL AND IS ALSO AVAILABLE IN CATEGORIES AT TOP LEFT")
                .build());
        addFragment(new Step.Builder().setTitle("BANNER")
                .setContent("QUICKLY SELECT FROM TOP SELLING FOOD ITEMS FROM TODAY")
                .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_theaters_black_24dp) // int top drawable
                .setSummary("TAP TO SEE DETAILS OF ITEMS")
                .build());
        addFragment(new Step.Builder().setTitle("QUICK ADD TO CART BUTTON")
                .setContent("YOU CAN QUICKLY ADD THE DESIRED ITEM TO CART WITHOUT LOOKING AT ITS DETAILS")
                        .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_add_shopping_cart_black_24dp) // int top drawable
                .setSummary("ITEM QUANTITY IN CART WILL INCREASE THE NUMBER OF TIMES YOU CLICK IF YOU GO IN THE" +
                        " DETAILS THE SELECTED QUANTITY WILL OVERRIDE YOUR PREVIOUS QUANTITY OF THAT ITEM")
                .build());
        addFragment(new Step.Builder().setTitle("FAVORITE BUTTON")
                .setContent("YOU CAN QUICKLY ADD AND REMOVE ANY ITEM FROM FAVORITE LIST")
                     .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_favorite_black_24dp) // int top drawable
                .setSummary("ONCE YOU ADD TO FAVORITES ICON WILL CHANGE TO WHITE AND ITEMS WILL BE STORED IN FAVORITE LIST FOR QUICK BUY")
                .build());
        addFragment(new Step.Builder().setTitle("SHARE TO FACEBOOK BUTTON")
                .setContent("OPENS DAILOG TO SHARE FOOD TO FACEBOOK")
                      .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_share_black_24dp) // int top drawable
                .setSummary("SHARE DAILOG IS COMPLETELY ISOLATED WE DO NOT STORE YOUR INFO")
                .build());
        addFragment(new Step.Builder().setTitle("FOOD DETAILS SCREEN")
                .setContent("LETS YOU CHHOSE ITEMS GET DETAILS AND RECEPIES ABOUT IT ALSO RATE AND VIEW WHAT OTHER PEOPLE SAID")
                       .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_add_shopping_cart_black_24dp) // int top drawable
                .setSummary("CART BUTTON ADDS SELECTED QUANTITY TO CART QUANTITY CAN BE CHANGED IN CART TOO ")
                .build());
        addFragment(new Step.Builder().setTitle("RATING BUTTON")
                .setContent("YOU CAN GIVE RATING AND PROVIDE FEEDBACK FOR THE FOOD WITH THIS BUTTON")
                     .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_security_black_24dp) // int top drawable
                .setSummary("YOU CAN PROVIDE FEEDBACK AS MANY TIMES AS YOU WANT AND PREVIOUS FEEDBACK CAN BE VIEWED IN SHOW REVIEWS BUTTON")
                .build());
        addFragment(new Step.Builder().setTitle("CART SCREEN")
                .setContent("YOU CAN CHANGE QUANTITY DELETE ONE OR ALL ITEMS IN CART")
                     .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_child_care_black_24dp) // int top drawable
                .setSummary("YOU NEED TO CLCIK PLACE OREDERENTER NEW NAME EXCEEDING MINIMUM LENGTH AND CLICK UPDATE FOR SUCCESSFULLY UPDATING NAME")
                .build());
        addFragment(new Step.Builder().setTitle("TO DO LIST OPTION")
                .setContent("YOU CAN TEMPORARILY WRITE DOWN A LIST OF THINGS TO BE DONE USING THIS OPTION")
                   .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_bookmark_black_24dp) // int top drawable
                .setSummary("YOU CAN ADD NEW ITEM USING + BUTTON ON THE TOP LEFT AND CLEAR ALL ITEMS USING " +
                        "CLEAR BUTTON \nTHIS LIST IS TEMPORARY AND IS NOT STORED ANYWHERE AND WILL BE DELETED WHEN YOU EXIT THAT SCREEN")
                .build());
        addFragment(new Step.Builder().setTitle("SEND NEWS OPTION")
                .setContent("YOU CAN SEND NOTIFICATIONS TO ALL SUBSCRIBERS ABOUT SPECIAL FOOD OFFERS USING THIS OPTION")
                        .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_notifications_active_black_24dp) // int top drawable
                .setSummary("YOU NEED TO ADD TITLE AND MESSAGE FOR NOTIFICATION AND CLICK SEND")
                .build());
        addFragment(new Step.Builder().setTitle("RESEND LOGIN OPTION")
                .setContent("IN CASE YOU WANT TO CHECK YOUR CREDENTIALS YOU CAN SEND THEM TO YOUR EMAIL USING THIS OPTION")
                      .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_supervisor_account_black_24dp) // int top drawable
                .setSummary("YOU NEED TO CHOOSE THE APP YOU NEED TO RECIEVE LOGIN DETAILS DEFAULT IS GMAIL BUT YOU CAN USE WHATSAPP AND OTHERS TOO YOU NEED TO FIND YOUR ACCOUNT AND HIT SEND")
                .build());
        addFragment(new Step.Builder().setTitle("SIGN OUT OPTION")
                .setContent("YOU CAN SIGN OUT FROM THIS OPTION ")
                        .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_exit_to_app_black_24dp) // int top drawable
                .setSummary("IF YOU ARE DONE PURCHASING FOOD OR NEED TO LOG IN AS DIFFERENT USER YOU CAN USE THIS OPTION YOUR CART STILL REMAINS WHEN YOU SIGN OUT ON SAME PHONE")
                .build());
        addFragment(new Step.Builder().setTitle("ABOUT OPTION")
                .setContent("YOU CAN VIEW DETAILS ABOUT THE CREATOR OF THIS APP WITH THIS OPTION")
                      .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_info_black_24dp) // int top drawable
                .setSummary("YOU CAN ALSO FIND LINKS FOR OTHER PLATFORMS I USE :)")
                .build());


    }

//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//        switch (v.getId())
//        {
//            case R.id.prev:
//                break;
//
//            case R.id.next:
//                break;
//        }
//    }
}


