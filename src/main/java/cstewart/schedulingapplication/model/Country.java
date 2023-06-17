package cstewart.schedulingapplication.model;

/** A class for countries. */
public class Country {
    // ENCAPSULATED FIELDS
    private int Country_ID;
    private String Country;

    // CONSTRUCTOR
    public Country(int country_ID, String country) {
        Country_ID = country_ID;
        Country = country;
    }

    // GETTERS AND SETTERS
    public Country(String country) {
        Country = country;
    }
    public Country(int country_ID) {
        Country_ID = country_ID;
    }

    public int getCountry_ID() {
        return Country_ID;
    }
    public void setCountry_ID(int country_ID) {
        Country_ID = country_ID;
    }

    public String getCountry() {
        return Country;
    }
    public void setCountry(String country) {
        Country = country;
    }

    /** A method to override the superclass. */
    @Override
    public String toString(){
        return (Integer.toString(Country_ID) + " " + Country);
    }
}
