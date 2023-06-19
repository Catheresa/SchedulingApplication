package cstewart.schedulingapplication.model;

/** A class for users. */
public class User {
    // ENCAPSULATED FIELDS
    private int User_ID;
    private String User_Name;

    // CONSTRUCTOR
    public User(int user_ID, String user_Name) {
        User_ID = user_ID;
        User_Name = user_Name;
    }

    // GETTERS AND SETTERS
    /** @return the user_ID. */
    public int getUser_ID() {
        return User_ID;
    }
    /** @param user_ID the user_ID to set. */
    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    /** @return the user_Name. */
    public String getUser_Name() {
        return User_Name;
    }
    /** @param user_Name the user_Name to set. */
    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    /** A method to override the superclass. */
    @Override
    public String toString(){
        return (Integer.toString(User_ID) + " " + User_Name);
    }
}
