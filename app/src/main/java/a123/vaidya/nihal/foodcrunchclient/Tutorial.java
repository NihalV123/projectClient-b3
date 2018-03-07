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
                .setSummary("YOU CAN TYPE TO GET SUGGESTIONS OR CLICK ON RECENT SUGGESTIONS AND PRESS ENTER")
                .build());
        addFragment(new Step.Builder().setTitle("REFRESH BUTTON")
                .setContent("YOU CAN REFRESH LAYOUT USING REFRESH BUTTON")
                .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_refresh_black_24dp) // int top drawable
                .setSummary("YOU CAN REFRESH WHEN ADDING OR REMOVING CATEGORIES OR WHEN IMAGE IS NOT BEING ETCHED FROM SERVER ")
                .build());
        addFragment(new Step.Builder().setTitle("ADD CATEGORY BUTTON")
                .setContent("YOU CAN ADD NEW CATEGORY USING GREEN FLOATING ACTION BUTTON")
                .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_playlist_add_black_24dp) // int top drawable
                .setSummary("---YOU NEED TO PROVIDE NAME AND IMAGE AND UPLOAD TO DATABASE TO SUCCESSFULLY ADD OR UPDATE CATEGORY\n---YOU " +
                        "CAN ALSO UPDATE OR REMOVE CATEGORY USING THE UPDATE OR REMOVE BUTTONS")
                .build());
        addFragment(new Step.Builder().setTitle("ADD FOOD BUTTON")
                .setContent("WHEN YOU ENTER CATEGORY YOU CAN ADD NEW FOOD ITEM USING RED FLOATING ACTION BUTTON")
                .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_playlist_add_black_24dp) // int top drawable
                .setSummary("---YOU NEED NAME DESCRIPTION PRICE DISCOUNT RECEPIE QUANTITY AND IMAGE AND UPLOAD TO DATABASE TO SUCCESSFULLY ADD OR UPDATE NEW FOOD ITEM\n---YOU " +
                        "CAN ALSO REMOVE FOOD ITEM USING REMOVE BUTTON")
                .build());
        addFragment(new Step.Builder().setTitle("ALL ORDERS MENU PART 1")
                .setContent("YOU CAN VIEW AND UPDATE AND REMOVE ALL ORDERS FROM ALL USERS USING ALL ORDERS")
                        .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_access_time_black_24dp) // int top drawable
                .setSummary("---YOU CAN USE EDIT BUTTON TO CHANGE DELIVERY STATE OF THE ORDER FROM JUST PLACED TO ON THE WAY TO " +
                        "SHIPPED WHICH WILL TRIGGER NOTIFICATION AND EMAIL CLIENT CHOOSER TO SEND EMAIL")
                .build());
        addFragment(new Step.Builder().setTitle("ALL ORDERS MENU PART 2")
                .setContent("ONCE SHIPPED YOU CAN REMOVE ORDER BY USING REMOVE BUTTON ")
                     .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_access_time_black_24dp) // int top drawable
                .setSummary("---YOU CAN ALSO VIEW THE DETAILS OF ORDER AND ALL ITEMS IN THE ORDER USING DETAILS BUTTON \n ---WHILE DELIVERY" +
                        " YOU CAN USE DIRECTIONS BUTTON TO GET A ROUTE FROM YOUR CURRENT LOCATION TO THE ADDRESS OF THE CUSTOMER")
                .build());
        addFragment(new Step.Builder().setTitle("BANNER MENU")
                .setContent("VIEW UPDATE AND REMOVE ITEMS IN BANNER ON THE CLIENT SIDE")
                      .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_theaters_black_24dp) // int top drawable
                .setSummary("---YOU NEED NAME AND IMAGE WHICH USER CAN ONLY SEE ON THE BANNER AND A FOOD ID PRESENT IN DATABASE " +
                        "TO LINK THE SPECIAL FOOD ITEM TO THE DATABASE AND CLICK CREATE TO SUCCESSFULLY ADD OR UPDATE NEW BANNER ITEM\n---YOU CAN REMOVE BANNER ITEMS USING REMOVE BUTTON")
                .build());
        addFragment(new Step.Builder().setTitle("UPDATE EMAIL OPTION")
                .setContent("YOU CAN CHANGE YOUR EMAIL USING THIS OPTION")
                       .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_email_black_24dp) // int top drawable
                .setSummary("YOU NEED TO PROVIDE A CORRECT EMAIL AND CLICK UPDATE FOR SUCCESSFULLY UPDATING EMAIL")
                .build());
        addFragment(new Step.Builder().setTitle("CHANGE PASSWORD OPTION")
                .setContent("YOU CAN CHANGE YOUR PASSWORD USING THIS OPTION")
                     .setBackgroundColor(R.color.colorPrimary)  // int background color
                .setDrawable(R.drawable.ic_security_black_24dp) // int top drawable
                .setSummary("YOU NEED TO ENTER CURRENT PASSWORD AND ENTER NEW PASSWORD TWICE AND CLICK UPDATE FOR SUCCESSFULLY UPDATING PASSWORD")
                .build());
        addFragment(new Step.Builder().setTitle("CHANGE NAME OPTION")
                .setContent("YOU CAN CHANGE THE NAME SEEN ON HEADER USING THIS OPTION")
                     .setBackgroundColor(R.color.about_instagram_color)  // int background color
                .setDrawable(R.drawable.ic_child_care_black_24dp) // int top drawable
                .setSummary("YOU NEED TO ENTER NEW NAME EXCEEDING MINIMUM LENGTH AND CLICK UPDATE FOR SUCCESSFULLY UPDATING NAME")
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


