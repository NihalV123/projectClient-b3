package a123.vaidya.nihal.foodcrunchclient.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import a123.vaidya.nihal.foodcrunchclient.Model.Request;
import a123.vaidya.nihal.foodcrunchclient.Model.User;
import a123.vaidya.nihal.foodcrunchclient.Remote.APIService;
import a123.vaidya.nihal.foodcrunchclient.Remote.FCMRetrofitClient;
import a123.vaidya.nihal.foodcrunchclient.Remote.RetrofitClient;
import a123.vaidya.nihal.foodcrunchclient.Remote.iGeoCoordinates;

/**
 * Created by nnnn on 26/12/2017.
 */

public class Common {
    public static User currentUser;
    public static Request currentRequest;
    public static final String PHONE_TEXT = "userPhone";
    private static final String BASE_URL = "https://maps.googleapis.com";

    private static final String fcmUrl = "https://fcm.googleapis.com";

    public static APIService getFCMService()
    {
        //return RetrofitClient.getClient(BASE_URL).create(APIService.class);
        return FCMRetrofitClient.getClient(fcmUrl).create(APIService.class);
    }
    //extra start here
    public static iGeoCoordinates getGeoCodeService()
    {
        return RetrofitClient.getClient(BASE_URL).create(iGeoCoordinates.class);
    }

    //end here
    public static String convertCodeToStatus(String code)
    {
        switch (code) {
            case "0":
                return "Placed";
            case "1":
                return "Your food is on the way ";
            default:
                return "Shipped!!";
        }
    }

    public static final String UPDATE = "Update";
    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";
    public static final String DELETE = "Delete";
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info != null)
            {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED)
                        return true;

                }
            }
        }

        return false;
    }
}
