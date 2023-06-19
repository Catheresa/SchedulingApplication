package cstewart.schedulingapplication.model;

import java.time.LocalDateTime;
/**
 * Class Appointment.java*/
/** A class for appointments.
 @author Catheresa Stewart Student ID: 009490256 */
public class Appointment {
    // ENCAPSULATED FIELDS
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customer_ID;
    private int user_ID;
    private int contact_ID;
    private String contact_Name;

    // CONSTRUCTORS (OVERLOADED)
    public Appointment(int appointment_ID, LocalDateTime start) {
        this.appointment_ID = appointment_ID;
        this.title = " ";
        this.description = " ";
        this.location = " ";
        this.type = " ";
        this.start = start;
        this.end = LocalDateTime.now();
        this.customer_ID = 1;
        this.user_ID = 1;
        this.contact_ID = 1;
        this.contact_Name = " ";
    }

    public Appointment(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,
                       int customer_ID, int user_ID, int contact_ID) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_ID = customer_ID;
        this.user_ID = user_ID;
        this.contact_ID = contact_ID;
        this.contact_Name = " ";
    }

    public Appointment(int appointment_ID, String title, String description, String location, String type, LocalDateTime start,
                       LocalDateTime end, int customer_ID, int user_ID, int contact_ID, String contact_Name) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_ID = customer_ID;
        this.user_ID = user_ID;
        this.contact_ID = contact_ID;
        this.contact_Name = contact_Name;
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
        this.customer_ID = customer_ID;
        this.user_ID = user_ID;
        this.contact_ID = contact_ID;
        this.contact_Name = contact_Name;
    }

    // GETTERS AND SETTERS
    /** @return the appointment_ID. */
    public int getAppointment_ID() {
        return appointment_ID;
    }
    /** @param appointment_ID the appointment_ID to set. */
    public void setAppointment_ID(int appointment_ID) {
        this.appointment_ID = appointment_ID;
    }
    /** @return the title. */
    public String getTitle() {
        return title;
    }
    /** @param title the title to set. */
    public void setTitle(String title) {
        this.title = title;
    }
    /** @return the description. */
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    /** @return the location. */
    public String getLocation() {
        return location;
    }
    /** @param location the location to set. */
    public void setLocation(String location) {
        this.location = location;
    }
    /** @return the type. */
    public String getType() {
        return type;
    }
    /** @param type the type to set. */
    public void setType(String type) {
        this.type = type;
    }
    /** @return the start date and time. */
    public LocalDateTime getStart() {
        return start;
    }
    /** @param start the start to set. */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    /** @return the end date and time. */
    public LocalDateTime getEnd() {
        return end;
    }
    /** @param end the end to set. */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    /** @return the customer_ID. */
    public int getCustomer_ID() {
        return customer_ID;
    }
    /** @param customer_ID the customer_ID to set. */
    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }
    /** @return the user_ID. */
    public int getUser_ID() {
        return user_ID;
    }
    /** @param user_ID the user_ID to set. */
    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }
    /** @return the Contact_ID. */
    public int getContact_ID() {
        return contact_ID;
    }
    /** @param contact_ID the contact_ID to set. */
    public void setContact_ID(int contact_ID) {
        this.contact_ID = contact_ID;
    }
    /** @return the contact_Name. */
    public String getContact_Name() {
        return contact_Name;
    }
    /** @param contact_Name the contact_Name to set. */
    public void setContact_Name(String contact_Name) {
        this.contact_Name = contact_Name;
    }

    /** A method to override the superclass. */
    @Override
    public String toString(){
        return (Integer.toString(appointment_ID) + " " + title + " " + description + " " + location + " " + contact_Name
                + " " + type + " " + customer_ID + " " + user_ID);
    }
}
