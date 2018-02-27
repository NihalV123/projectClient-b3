package a123.vaidya.nihal.foodcrunchclient.Model;


public class Food {
    private String Name,Image,Description,Price,
    Discount,MenuId,
    Email;

    public Food() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public Food(String name, String image, String description, String price,
                //String email,
                String discount, String menuId) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        //Email = email;
        Discount = discount;
        MenuId = menuId;

    }


}
