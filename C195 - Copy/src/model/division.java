package model;

public class division {
    private final int divisionID;
    private final int countryID;
    private String division;

    /**
     * Constructor for division.
     * @param divisionID
     * @param countryID
     * @param division
     */
    public division(int divisionID, int countryID, String division) {
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.division = division;
    }

    /**
     * Getter for division ID.
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Getter for country ID.
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     *Getter for division.
     * @return
     */
    public String getDivision() {
        return division;
    }
}