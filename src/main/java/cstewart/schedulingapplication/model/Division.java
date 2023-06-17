package cstewart.schedulingapplication.model;

/** A class for divisions. */
public class Division {
    // ENCAPSULATED FIELDS
    private int Division_ID;
    private String Division;
    private int country_ID;

    // CONSTRUCTOR
    public Division(int division_ID, String division, int country_ID) {
        this.Division_ID = division_ID;
        this.Division = division;
        this.country_ID = country_ID;
    }

    // GETTERS AND SETTERS
    public Division(int division_ID) {
        Division_ID = division_ID;
    }
    public Division(String division) {
        Division = division;
    }

    public int getDivision_ID() {
        return Division_ID;
    }
    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }

    public String getDivision() {
        return Division;
    }
    public void setDivision(String division) {
        Division = division;
    }

    public int getCountry_ID() {
        return country_ID;
    }
    public void setCountry_ID(int country_ID) {
        this.country_ID = country_ID;
    }

    /** A method to override the superclass. */
    @Override
    public String toString(){
        return "(" + Division_ID + ") " + Division;
    }
}
