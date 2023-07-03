package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.appointment;

import java.sql.*;
import java.time.LocalDateTime;

public class appointmentHelper {

    /**
     * Retrieves data from the database and returns it into the table.
     * @return
     */
    public static ObservableList<appointment> getAllAppointmentData() {
        Connection connect;
        ObservableList<appointment> appointments = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            String sql = "Select appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID;";
            ResultSet results = connect.createStatement().executeQuery(sql);
            while (results.next()) {
                appointment newAppointments = new appointment(
                        results.getInt("Appointment_ID"),
                        results.getString("Title"),
                        results.getString("Description"),
                        results.getString("Location"),
                        results.getString("Contact_Name"),
                        results.getString("Type"),
                        results.getDate("Start").toLocalDate(),
                        results.getTimestamp("Start").toLocalDateTime(),
                        results.getDate("End").toLocalDate(),
                        results.getTimestamp("End").toLocalDateTime(),
                        results.getInt("Customer_ID"),
                        results.getInt("User_ID"),
                        results.getInt("Contact_ID"));
                appointments.add(newAppointments);
            }
            return appointments;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a new appointment to input into the database
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private static appointment newAppointments(ResultSet resultSet) throws SQLException {
        return new appointment(
                resultSet.getInt("Appointment_ID"),
                resultSet.getString("Title"),
                resultSet.getString("Description"),
                resultSet.getString("Location"),
                resultSet.getString("Contact_Name"),
                resultSet.getString("Type"),
                resultSet.getDate("Start").toLocalDate(),
                resultSet.getTimestamp("Start").toLocalDateTime(),
                resultSet.getDate("End").toLocalDate(),
                resultSet.getTimestamp("End").toLocalDateTime(),
                resultSet.getInt("Customer_ID"),
                resultSet.getInt("User_ID"),
                resultSet.getInt("Contact_ID"));
    }

    /**
     * Retrieves data for the current week and inputs it into the table.
     * @return
     */
    public static ObservableList<appointment> getAppointmentWeeks() {
        Connection connect;
        ObservableList<appointment> appointments = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();

            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE week(Start)=week(now());";

            ResultSet resultSet = connect.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                appointment newAppointments = new appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"));
                appointments.add(newAppointments);
            }
            return appointments;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves data from the database for the current month and inputs it into the table.
     * @return
     */
    public static ObservableList<appointment> monthList() {
        Connection connect;
        ObservableList<appointment> appointments = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();

            String sql = " SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE month(Start)=month(now());";

            ResultSet resultSet = connect.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                appointment newAppointments = new appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID"));

                appointments.add(newAppointments);
            }
            return appointments;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }


    /**
     * Searches for the customer ID and returns the information.
     * @param customerID
     * @return
     */
    public static ObservableList<appointment> getCustomerID(int customerID) {
        Connection connect;
        ObservableList<appointment> appointments = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();

            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, contacts.Contact_Name, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID FROM appointments, contacts WHERE appointments.Customer_ID = ?;";

            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                appointments.add(newAppointments(resultSet));
            }
            return appointments;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Allows users to update existing data and save the new data.
     * @param aptID
     * @param title
     * @param description
     * @param location
     * @param contactName
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @return
     * @throws SQLException
     */
    public static boolean updateAppointments(int aptID, String title, String description, String location, int contactName, String type,
                                             LocalDateTime start, LocalDateTime end, int customerID, int userID) throws SQLException {
        Connection connect;
        try {
            connect = JDBC.getConnection();

            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?;";

            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, location);
            statement.setInt(4, contactName);
            statement.setString(5, type);
            statement.setTimestamp(6, Timestamp.valueOf(start));
            statement.setTimestamp(7, Timestamp.valueOf(end));
            statement.setInt(8, customerID);
            statement.setInt(9, userID);
            statement.setInt(10, aptID);
            statement.execute();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Creates a new appointment.
     * @param title
     * @param description
     * @param location
     * @param contactName
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @return
     * @throws SQLException
     */
    public static boolean createAppointment(String title, String description, String location, int contactName, String type,
                                            LocalDateTime start, LocalDateTime end, int customerID, int userID) throws SQLException {
        Connection connect;
        try {
            connect = JDBC.getConnection();

            String sql = "INSERT INTO appointments(Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, location);
            statement.setInt(4, contactName);
            statement.setString(5, type);
            statement.setTimestamp(6, Timestamp.valueOf(start));
            statement.setTimestamp(7, Timestamp.valueOf(end));
            statement.setInt(8, customerID);
            statement.setInt(9, userID);
            statement.execute();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Allows users to delete existing appointment from the database.
     * @param aptID
     * @return
     * @throws SQLException
     */
    public static boolean deleteAppointment(int aptID) throws SQLException {
        Connection connect;
        try {
            connect = JDBC.getConnection();

            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?;";

            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, aptID);
            statement.execute();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves data for the contact schedule report.
     * @return
     */
    public static String getReportContact() {
        try {

            StringBuilder reportAppointmentEachContact = new StringBuilder("Contact ID | Appointment ID | Customer ID | Title | Type | Description | Start | End\n");
            String sql = "SELECT Contact_ID, Appointment_ID, Customer_ID, Title, Type, Description, Start, End FROM appointments ORDER BY Contact_ID ";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                int appointmentID = rs.getInt("Appointment_ID");
                int customerID = rs.getInt("Customer_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

                reportAppointmentEachContact.append("\n" + contactID + "\t" + appointmentID + "\t" + customerID + "\t" + title + "\t" + type + "\t" + description + "\t" + start + "\t" + end + "\n");
            }
            return reportAppointmentEachContact.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Try again";
        }
    }

    /**
     * Retrieves data from the database for the report type and month.
     * @return
     */
    public static String getReportTypeMonth() {
        try {
            StringBuilder reportAppointmentPerTypeMonth = new StringBuilder("Month   |    Type   |    Total \n");
            reportAppointmentPerTypeMonth.append("\n");
            String sql = "SELECT MONTHNAME(start) as Month, Type, COUNT(*)  as Amount FROM appointments GROUP BY MONTH(start), type";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String month = rs.getString("Month");
                String type = rs.getString("Type");
                String amount = rs.getString("Amount");

                reportAppointmentPerTypeMonth.append("\n" + month + "\t " + type + "\t" + amount + "\n");
            }
            return reportAppointmentPerTypeMonth.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Try again";
        }
    }

    /**
     * Retrieves data from the database for the report type and location.
     * @return
     */
    public static String reportAppointmentTypeLocation() {
        try {
            StringBuilder reportAppointmentPerTypeLocation = new StringBuilder("Location     |     Type    |    Total \n");
            reportAppointmentPerTypeLocation.append("\n");

            String sql = "SELECT Location, Type, COUNT(*)  as Amount FROM appointments GROUP BY Location, type";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String amount = rs.getString("Amount");
                reportAppointmentPerTypeLocation.append("\n" + location + "\t" + type + "\t" + amount + "\n");
            }
            return reportAppointmentPerTypeLocation.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Try again";
        }
    }
}
