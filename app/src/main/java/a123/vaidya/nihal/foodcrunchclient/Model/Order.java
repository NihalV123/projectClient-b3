package a123.vaidya.nihal.foodcrunchclient.Model;

/**
 * Created by nnnn on 27/12/2017.
 */

public class Order {
<<<<<<< HEAD
    private String UserPhone;
=======
    private int ID;
>>>>>>> old/master
    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Price;
<<<<<<< HEAD
    private String Email;
    private String Discount;
    private String Image;

    public Order() {
    }

    public Order(String userPhone, String productId, String productName, String quantity, String price, String email,String discount, String image) {
        UserPhone = userPhone;
=======
    private String Discount;
    private String Image;
    private String Email;
    public Order() {
    }

    public Order(int ID, String productId, String productName, String quantity, String price, String discount,String image,String email) {
        this.ID = ID;
>>>>>>> old/master
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
<<<<<<< HEAD
        Email = email;
        Discount = discount;
        Image = image;

    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

=======
        Discount = discount;
        Image = image;
        Email= email;
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Order(String productId, String productName, String quantity, String price ,
                 String discount,String image,String email
    ) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
        Image = image;
        Email = email;

    }



>>>>>>> old/master
    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

<<<<<<< HEAD
    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
=======
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
>>>>>>> old/master
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

<<<<<<< HEAD
    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
=======
    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

>>>>>>> old/master
}
