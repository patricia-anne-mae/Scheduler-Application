package model;


public class customers {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerZipCode;
    private String customerPhone;
    private String country;
    private String division;
    private int divisionID;
    private int countryID;

    /**
     * Constructor for customer.
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerZipCode
     * @param customerPhone
     * @param country
     * @param division
     * @param divisionID
     * @param countryID
     */
    public customers(int customerID, String customerName, String customerAddress, String customerZipCode,
                     String customerPhone, String country, String division, int divisionID, int countryID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZipCode = customerZipCode;
        this.customerPhone = customerPhone;
        this.country = country;
        this.division = division;
        this.divisionID = divisionID;
        this.countryID = countryID;
    }

    /**
     * Constructor for the customer list
     * @param customerID
     * @param customerName
     */
    public customers(int customerID, String customerName) {
        this.customerID = customerID;
        this.customerName = customerName;
    }

    /**
     * Getter for customer ID
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Getter for customer name.
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Getter for customer address.
     * @return
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Getter for customer zip/postal
     * @return
     */
    public String getCustomerZipCode() {
        return customerZipCode;
    }

    /**
     * Getter for customer phone
     * @return
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Getter for customer country.
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * Getter for customer division.
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * Getter for customer division ID.
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Getter for customer country ID.
     * @return
     */
    public int getCountryID() {
        return countryID;
    }


}