package a123.vaidya.nihal.foodcrunchclient.Model;


public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String IsStaff;
    private String secureCode;
    private String HomeAddress;
    private String Email;
    private Double balance;
    private String Extra;

    public User (){
    }

    public User (String name, String password, String secureCode,String homeAddress,String email,

                 String extra
    ) {
        Name = name;
        Password = password;
        IsStaff="false";
        this.secureCode = secureCode;
        HomeAddress = homeAddress;
        Email = email;

        Extra = extra;
    }

    public String getHomeAddress() {
        return HomeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        HomeAddress = homeAddress;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getSecureCode() {
        return secureCode;
    }

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


    public String getExtra() {
        return Extra;
    }

    public void setExtra(String extra) {
        Extra = extra;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}