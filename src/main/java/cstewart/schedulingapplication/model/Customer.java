package cstewart.schedulingapplication.model;

/** A class for customers. */
public class Customer {
    // ENCAPSULATED FIELDS
    private int customer_ID;
    private String customer_Name;
    private String address;
    private String postal_Code;
    private String phone;
    private int division_ID;
    private int country_ID;
    private String country;
    private String division;

    // CONSTRUCTORS (OVERLOADED)
    public Customer(int customer_ID, String customer_Name, String address, String postal_Code, String phone) {
        this.customer_ID = customer_ID;
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.division_ID = 1;
        this.country_ID = 1;
        this.country = " ";
        this.division = " ";
    }
    public Customer(int customer_ID, String customer_Name, String address, String postal_Code, String phone, int division_ID) {
        this.customer_ID = customer_ID;
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.division_ID = division_ID;
        this.country_ID = 1;
        this.country = " ";
        this.division = " ";
    }
    public Customer(int customer_ID, String customer_Name, String address, String postal_Code, String phone, String country, int division_ID) {
        this.customer_ID = customer_ID;
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.division_ID = division_ID;
        this.country_ID = 1;
        this.country = country;
        this.division = " ";
    }

    public Customer(int customer_ID, String customer_Name, int country_ID, String country,
                    String address, int division_ID,  String division, String postal_Code, String phone) {
        this.customer_ID = customer_ID;
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.division_ID = division_ID;
        this.country_ID = country_ID;
        this.country = country;
        this.division = division;
    }


    // GETTERS AND SETTERS
    public int getCountry_ID() {
        return country_ID;
    }
    public void setCountry_ID(int country_ID) {
        this.country_ID = country_ID;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getDivision() {
        return division;
    }
    public void setDivision(String division) {
        this.division = division;
    }

    public int getCustomer_ID() {
        return customer_ID;
    }
    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    public String getCustomer_Name() {
        return customer_Name;
    }
    public void setCustomer_Name(String customer_Name) {
        this.customer_Name = customer_Name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal_Code() {
        return postal_Code;
    }
    public void setPostal_Code(String postal_Code) {
        this.postal_Code = postal_Code;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivision_ID() {
        return division_ID;
    }
    public void setDivision_ID(int division_ID) {
        this.division_ID = division_ID;
    }

    /** A method to override the superclass. */
    @Override
    public String toString(){
        return (Integer.toString(customer_ID) + " " + customer_Name + " " + address + " " + postal_Code + " " + phone);
    }
}