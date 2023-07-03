package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class appointment {
    private int aptID;
    private String title;
    private String description;
    private String location;
    private String contactName;
    private String type;
    private LocalDate startDate;
    private LocalDateTime startTime;
    private LocalDate endDate;
    private LocalDateTime endTime;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Appointment constructor.
     * @param aptID
     * @param title
     * @param description
     * @param location
     * @param contactName
     * @param type
     * @param startDate
     * @param startTime
     * @param endDate
     * @param endTime
     * @param customerID
     * @param userID
     * @param contactID
     */
    public appointment(int aptID, String title, String description, String location, String contactName,
                       String type, LocalDate startDate, LocalDateTime startTime, LocalDate endDate,
                       LocalDateTime endTime, int customerID, int userID, int contactID) {
        this.aptID = aptID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactName = contactName;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Appointment constructor used for the alerts.
     * @param aptID
     * @param startDate
     * @param startTime
     */
    public appointment(int aptID, LocalDate startDate, LocalDateTime startTime) {
        this.aptID = aptID;
        this.startDate = startDate;
        this.startTime = startTime;
    }

    public appointment(int appointmentID, String appointmentTitle, String appointmentDescription,
                       String appointmentLocation, String appointmentTitle1, LocalDateTime start,
                       LocalDateTime end, int customerID, int userID, int customerID1) {
    }

    /**
     * constructor used for validate checker.
      * @param aptID
     * @param apptStartTime
     * @param apptEndTime
     * @param title
     * @param type
     * @param description
     * @param customerID
     */
    public appointment(int aptID, LocalTime apptStartTime, LocalTime apptEndTime,
                        String title, String type, String description, int customerID) {

        LocalTime.from(apptStartTime);
        LocalTime.from(apptEndTime);

        this.aptID = aptID;
        this.title = title;
        this.type = type;
        this.description = description;
        this.customerID = customerID;

    }

    /**
     * Getter for appointment ID
     * @return
     */
    public int getAptID() {
        return aptID;
    }

    /**
     * Getter for appointment title.
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for appointment description.
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for appointment location.
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter for appointment contact name.
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Getter for appointment type.
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for appointment start date.
     * @return
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Getter for appointment start time.
     * @return
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Getter for appointment end date.
     * @return
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Getter for appointment end time.
     * @return
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Getter for appointment customer ID.
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Getter for appointment User ID.
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Getter for appointment Contact ID.
     * @return
     */
    public int getContactID() {
        return contactID;
    }

}
