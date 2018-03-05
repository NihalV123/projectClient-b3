package a123.vaidya.nihal.foodcrunchclient.Model;

public class Favorites {
    private String FoodId,FoodName,FoodImage,FoodDescription,FoodPrice,FoodDiscount,
            FoodMenuId,FoodEmail,FoodVideo,FoodRecepixes,FoodrateValue,UserPhone;
    private Double FoodQuantity;

    public Favorites() {
    }

    public Favorites(String foodId, String foodName,String foodPrice, String foodMenuId,String foodImage,
                     String foodDiscount,String foodDescription,
                    // String foodEmail, String foodVideo, String foodRecepixes, String foodrateValue,
                     String userPhone
    //                 Double foodQuantity
    ) {
        FoodId = foodId;
        FoodName = foodName;
        FoodPrice = foodPrice;
        FoodMenuId = foodMenuId;
        FoodImage = foodImage;
        FoodDiscount = foodDiscount;
        FoodDescription = foodDescription;
      //  FoodEmail = foodEmail;
      //  FoodVideo = foodVideo;
       // FoodRecepixes = foodRecepixes;
       // FoodrateValue = foodrateValue;
        UserPhone = userPhone;
        //FoodQuantity = foodQuantity;
    }




    //    public Favorites(String foodId, String foodName, String foodImage, String foodDescription, String foodPrice, String foodDiscount, String foodMenuId, String userPhone) {
//        FoodId = foodId;
//        FoodName = foodName;
//        FoodImage = foodImage;
//        FoodDescription = foodDescription;
//        FoodPrice = foodPrice;
//        FoodDiscount = foodDiscount;
//        FoodMenuId = foodMenuId;
//        UserPhone = userPhone;
//    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(String foodImage) {
        FoodImage = foodImage;
    }

    public String getFoodDescription() {
        return FoodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        FoodDescription = foodDescription;
    }

    public String getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        FoodPrice = foodPrice;
    }

    public String getFoodDiscount() {
        return FoodDiscount;
    }

    public void setFoodDiscount(String foodDiscount) {
        FoodDiscount = foodDiscount;
    }

    public String getFoodMenuId() {
        return FoodMenuId;
    }

    public void setFoodMenuId(String foodMenuId) {
        FoodMenuId = foodMenuId;
    }

    public String getFoodEmail() {
        return FoodEmail;
    }

    public void setFoodEmail(String foodEmail) {
        FoodEmail = foodEmail;
    }

    public String getFoodVideo() {
        return FoodVideo;
    }

    public void setFoodVideo(String foodVideo) {
        FoodVideo = foodVideo;
    }

    public String getFoodRecepixes() {
        return FoodRecepixes;
    }

    public void setFoodRecepixes(String foodRecepixes) {
        FoodRecepixes = foodRecepixes;
    }

    public String getFoodrateValue() {
        return FoodrateValue;
    }

    public void setFoodrateValue(String foodrateValue) {
        FoodrateValue = foodrateValue;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public Double getFoodQuantity() {
        return FoodQuantity;
    }

    public void setFoodQuantity(Double foodQuantity) {
        FoodQuantity = foodQuantity;
    }
}
