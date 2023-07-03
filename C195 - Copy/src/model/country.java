package model;

public class country {
    private final int country_ID;
    private String country;

    /**
     * Constructor for country.
     * @param country_ID
     * @param country
     */
    public country(int country_ID, String country) {
        this.country_ID = country_ID;
        this.country = country;
    }

    /**
     * Getter for country
     * @return
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * Setter for country.
     * @param name
     */
    public void setCountry(String name) {
        this.country = name;
    }

    /**
     * Getter for country ID.
     * @return
     */
    public int getCountryID() {
        return this.country_ID;
    }
}