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
    public int getUser_ID() {
        return User_ID;
    }
    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }
    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    /** A method to override the superclass. */
    @Override
    public String toString(){
        return (Integer.toString(User_ID) + " " + User_Name);
    }
}
