package a123.vaidya.nihal.foodcrunchclient.Model;


public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String IsStaff;
    private String secureCode;
    //private String Homeaddress;
    //private String Email;

    public User (){
    }

    public User (String name, String password, String secureCode
                // ,String homeaddress , String email
    ) {
        Name = name;
        Password = password;
        IsStaff="false";
        this.secureCode = secureCode;
       // Homeaddress = homeaddress;
        //Email = email;
    }

//
//    public String getHomeaddress() {
//        return Homeaddress;
//    }
//
//    public void setHomeaddress(String homeaddress) {
//        Homeaddress = homeaddress;
//    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getSecureCode() {
        return secureCode;
    }
//
//    public String getEmail() {
//        return Email;
//    }
//
//    public void setEmail(String email) {
//        Email = email;
//    }

    public void setSecureCode(String secureCode) {
        this.secureCode = secureCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword () {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
