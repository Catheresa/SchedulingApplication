package cstewart.schedulingapplication.model;

import java.time.LocalDateTime;

public class Appointment {
    // Encapsulation: Encapsulated fields
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private String create_Date;
    private String create_By;
    private String last_Update;
    private String last_Updated_By;
    private int customer_ID;
    private int user_ID;
    private int contact_ID;
    private String contact_Name;

    public Appointment(int appointment_ID, LocalDateTime start) {
        this.appointment_ID = appointment_ID;
        this.start = start;
    }

    public Appointment(int appointment_ID, String title, String description, String location, String contact_Name, String type, LocalDateTime start,
                       LocalDateTime end, int customer_ID, int user_ID) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact_Name = contact_Name;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_ID = customer_ID;
        this.user_ID = user_ID;

    }

    public Appointment(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,
                       String create_Date, String create_By, String last_Update, String last_Updated_By, int customer_ID, int user_ID, int contact_ID) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.create_Date = create_Date;
        this.create_By = create_By;
        this.last_Update = last_Update;
        this.last_Updated_By = last_Updated_By;
        this.customer_ID = customer_ID;
        this.user_ID = user_ID;
        this.contact_ID = contact_ID;

    }

    public Appointment(int appointment_ID, String title, String description, String location, String type, LocalDateTime start,
                       LocalDateTime end, String create_Date, String create_By, String last_Update, String last_Updated_By,
                       int customer_ID, int user_ID, int contact_ID, String contact_Name) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.create_Date = create_Date;
        this.create_By = create_By;
        this.last_Update = last_Update;
        this.last_Updated_By = last_Updated_By;
        this.customer_ID = customer_ID;
        this.user_ID = user_ID;
        this.contact_ID = contact_ID;
        this.contact_Name = contact_Name;
    }

    public int getAppointment_ID() {
        return appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        this.appointment_ID = appointment_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(String create_Date) {
        this.create_Date = create_Date;
    }

    public String getCreate_By() {
        return create_By;
    }

    public void setCreate_By(String create_By) {
        this.create_By = create_By;
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

    public int getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public int getContact_ID() {
        return contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        this.contact_ID = contact_ID;
    }

    public String getContact_Name() {
        return contact_Name;
    }

    public void setContact_Name(String contact_Name) {
        this.contact_Name = contact_Name;
    }

    @Override
    public String toString(){
        return (Integer.toString(appointment_ID) + " " + title + " " + description + " " + location + " " + contact_Name
                + " " + type + " " + customer_ID + " " + user_ID);
    }
}
