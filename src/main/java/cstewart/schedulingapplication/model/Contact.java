package cstewart.schedulingapplication.model;

/** A class for contacts. */
public class Contact {
    // ENCAPSULATED FIELDS
    private int Contact_ID;
    private String Contact_Name;
    private String Email;

    // CONSTRUCTOR
    public Contact(int contact_ID, String contact_Name, String email) {
        Contact_ID = contact_ID;
        Contact_Name = contact_Name;
        Email = email;
    }

    // GETTERS AND SETTERS
    /** @return the Contact_ID. */
    public int getContact_ID() {
        return Contact_ID;
    }
    /** @param contact_ID the contact_ID to set. */
    public void setContact_ID(int contact_ID) {
        Contact_ID = contact_ID;
    }
    /** @return the Contact_Name. */
    public String getContact_Name() {
        return Contact_Name;
    }
    /** @param contact_Name the contact_Name to set. */
    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }
    /** @return the Email. */
    public String getEmail() {
        return Email;
    }
    /** @param email the email to set. */
    public void setEmail(String email) {
        Email = email;
    }

    /** A method to override the superclass. */
    @Override
    public String toString(){
        return "(" + Contact_ID + ") " + Contact_Name;
    }
}
