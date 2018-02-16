package a123.vaidya.nihal.foodcrunchclient.Common;

import a123.vaidya.nihal.foodcrunchclient.Model.User;

/**
 * Created by nnnn on 26/12/2017.
 */

public class Common {
    public static User currentUser;
    public static String convertCodeToStatus(String code)
    {
        if (code.equals("0"))
            return "Placed";
        else if (code.equals("1"))
            return "Your food is on the way ";
        else
            return "Shipped!!";
    }
}
