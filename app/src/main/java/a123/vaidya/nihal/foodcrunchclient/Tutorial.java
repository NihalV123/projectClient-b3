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
        addFragment(new Step.Builder().setTitle("CHAT BOT")
                .setContent("ALTHOUGH IN DEVELOPMENT YOU CAN TALK TO THE CHAT BOT")
                      .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_android_black_24dp) // int top drawable
                .setSummary("TALK TO THE CHAT BOT FOR FUNNY RESPONSES")
                .build());
        addFragment(new Step.Builder().setTitle("CHANGE NAME/PASSWORD/EMAIL/HOME ADDRESS OPTIONS")
                .setContent("LETS YOU CHANGE NAME/PASSWORD/EMAIL/HOME ADDRESS LOG OFF FOR EFFECT")
                       .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_view_list_black_24dp) // int top drawable
                .setSummary("YOU CAN SEE YOUR NAME AND EMAIL CHANGED IN NAVIGATION DRAWER\n YOU CAN CHOOSE HOME ADDRESS" +
                        " FOR DELIVERING YOUR PRODUCT \nTOCHANGE PASSWORD YOU NEED YOUR UNIQUE SECURE CODE")
                .build());
        addFragment(new Step.Builder().setTitle("RATING BUTTON")
                .setContent("YOU CAN GIVE RATING AND PROVIDE FEEDBACK FOR THE FOOD WITH THIS BUTTON")
                     .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_star_black_24dp) // int top drawable
                .setSummary("YOU CAN PROVIDE FEEDBACK AS MANY TIMES AS YOU WANT AND PREVIOUS FEEDBACK CAN BE VIEWED IN SHOW REVIEWS BUTTON")
                .build());
        addFragment(new Step.Builder().setTitle("MY CART OPTION")
                .setContent("YOU CAN CHANGE QUANTITY DELETE ONE OR ALL ITEMS IN CART")
                     .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_shopping_basket_black_24dp) // int top drawable
                .setSummary("YOU NEED TO CLCIK PLACE OREDER TYPE OR CHOOSE ADDRESS AND PAYMENT METHOD AND SELECT WAY TO BE INFORMED FOR SUCCESSFUL ORDER")
                .build());
        addFragment(new Step.Builder().setTitle("GROCERY LIST OPTION")
                .setContent("YOU CAN TEMPORARILY WRITE DOWN SHOPPING LIST USING THIS OPTION")
                   .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_bookmark_black_24dp) // int top drawable
                .setSummary("YOU CAN ADD NEW ITEM USING + BUTTON ON THE TOP LEFT AND CLEAR ALL ITEMS USING " +
                        "CLEAR BUTTON \nTHIS LIST IS TEMPORARY AND IS NOT STORED ANYWHERE AND WILL BE DELETED WHEN YOU EXIT THAT SCREEN")
                .build());
        addFragment(new Step.Builder().setTitle("SETTINGS OPTION")
                .setContent("YOU CAN RECIEVE NOTIFICATIONS ABOUT SPECIAL FOOD OFFERS IF YOU SUBSCRIBE USING THIS OPTION")
                        .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_settings_applications_black_24dp) // int top drawable
                .setSummary("WE WILL INFORM YOU ABOUT DISCOUNTS FESIVALRELATED PRODUCTS ETC")
                .build());
        addFragment(new Step.Builder().setTitle("REMEMBER ME BUTTON")
                .setContent("YOU CAN RE LOGIN IF THE APP IS CLOSED USING THIS BUTTON")
                      .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_info_black_24dp) // int top drawable
                .setSummary("YOU NEED TO REMAIN LOGGED IN BEFORE THE APP IS CLOSED AND BUTTON MUST BE TICKED FOR IT TO WORK PROPERLY")
                .build());
        addFragment(new Step.Builder().setTitle("FORGOT PASSOWRD")
                .setContent("YOU CAN RECOVER YOUR PASSWORD WITH YOUR SECURE CODE AND PHONE NUMBER")
                        .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_security_black_24dp) // int top drawable
                .setSummary("PASSOWRD IS MAPPED TO YOUR PHONE ONLY")
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


