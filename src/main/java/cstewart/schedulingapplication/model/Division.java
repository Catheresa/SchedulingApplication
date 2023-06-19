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
    /** @return the division_ID. */
    public int getDivision_ID() {
        return Division_ID;
    }
    /** @param division_ID the division_ID to set. */
    public void setDivision_ID(int division_ID) {
        Division_ID = division_ID;
    }
    /** @return the Division. */
    public String getDivision() {
        return Division;
    }
    /** @param division the division to set. */
    public void setDivision(String division) {
        Division = division;
    }
    /** @return the country_ID. */
    public int getCountry_ID() {
        return country_ID;
    }
    /** @param country_ID the country_ID to set. */
    public void setCountry_ID(int country_ID) {
        this.country_ID = country_ID;
    }

    /** A method to override the superclass. */
    @Override
    public String toString(){
        return "(" + Division_ID + ") " + Division;
    }
}
