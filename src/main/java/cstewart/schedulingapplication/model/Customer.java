package cstewart.schedulingapplication.model;

public class Customer {
    // Encapsulation: Encapsulated fields
    private int customer_ID;
    private String customer_Name;
    private String address;
    private String postal_Code;
    private String phone;
    private String create_Date;
    private String created_By;
    private String last_Update;
    private String last_Updated_By;
    private int division_ID;
    private int country_ID;
    private String country;
    private String division;

    public Customer(int customer_ID, String customer_Name, String address, String postal_Code, String phone,
                    String create_Date, String created_By, String last_Update, String last_Updated_By, int division_ID) {
        this.customer_ID = customer_ID;
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.create_Date = create_Date;
        this.created_By = created_By;
        this.last_Update = last_Update;
        this.last_Updated_By = last_Updated_By;
        this.division_ID = division_ID;
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
    public Customer(int customer_ID, String customer_Name, String address, String postal_Code, String phone) {
        this.customer_ID = customer_ID;
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;

    }

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

    public String getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(String create_Date) {
        this.create_Date = create_Date;
    }

    public String getCreated_By() {
        return created_By;
    }

    public void setCreated_By(String created_By) {
        this.created_By = created_By;
    }

    public String getLast_Update() {
        return last_Update;
    }

    public void setLast_Update(String last_Update) {
        this.last_Update = last_Update;
    }

    public String getLast_Updated_By() {
        return last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        this.last_Updated_By = last_Updated_By;
    }

    public int getDivision_ID() {
        return division_ID;
    }

    public void setDivision_ID(int division_ID) {
        this.division_ID = division_ID;
    }

    @Override
    public String toString(){
        return (Integer.toString(customer_ID) + " " + customer_Name + " " + address + " " + postal_Code + " " + phone);
    }
}