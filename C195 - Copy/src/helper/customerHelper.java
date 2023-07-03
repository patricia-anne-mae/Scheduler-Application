package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.customers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class customerHelper {

    /**
     *Retrieves customer information from the database.
     * @return
     * @throws SQLException
     */
    public static ObservableList<customers> getCustomerTableData() throws SQLException {
        Connection c;
        ObservableList<customers> customers = FXCollections.observableArrayList();
        try {
            c = JDBC.getConnection();
            String SQL = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, countries.Country, first_level_divisions.Division, customers.Division_ID, countries.Country_ID FROM customers INNER JOIN first_level_divisions ON first_level_divisions.Division_ID = customers.Division_ID INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID;";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            while (rs.next()) {
                customers newCustomer = new customers(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getString("Country"),
                        rs.getString("Division"),
                        rs.getInt("Division_ID"),
                        rs.getInt("Country_ID"));

                customers.add(newCustomer);
            }
            return customers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Allows users to create a new customer.
     * @return
     */
    public static ObservableList<customers> createCustomerList() {
        Connection connect;
        ObservableList<customers> customers = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            String SQL = "SELECT Customer_ID, Customer_Name FROM customers;";
            ResultSet resultSet = connect.createStatement().executeQuery(SQL);
            while (resultSet.next()) {
                model.customers newCustomer = new customers(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"));
                customers.add(newCustomer);
            }
            return customers;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * This allows data to be modified and saved into the database with the new information.
     * @param CustomerID
     * @param CustomerName
     * @param CustomerAddress
     * @param CustomerPostalCode
     * @param CustomerPhone
     * @param DivisionID
     * @return
     * @throws SQLException
     */
    public static boolean updateCustomer(int CustomerID, String CustomerName, String CustomerAddress, String CustomerPostalCode, String CustomerPhone, String DivisionID) throws SQLException {
        Connection connect;
        try {
            connect = JDBC.getConnection();
            String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, CustomerName);
            statement.setString(2, CustomerAddress);
            statement.setString(3, CustomerPostalCode);
            statement.setString(4, CustomerPhone);
            statement.setInt(5, Integer.parseInt(DivisionID));
            statement.setInt(6, CustomerID);
            statement.execute();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * This creates a new customer.
     * @param CustomerName
     * @param CustomerAddress
     * @param CustomerPostalCode
     * @param CustomerPhone
     * @param DivisionID
     * @return
     * @throws SQLException
     */
    public static boolean createCustomer(String CustomerName, String CustomerAddress, String CustomerPostalCode, String CustomerPhone, String DivisionID) throws SQLException {
        Connection connect;
        try {
            connect = JDBC.getConnection();
            String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, CustomerName);
            statement.setString(2, CustomerAddress);
            statement.setString(3, CustomerPostalCode);
            statement.setString(4, CustomerPhone);
            statement.setInt(5, Integer.parseInt(DivisionID));
            statement.execute();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * This deletes a customer from the database.
     * @param custID
     * @return
     * @throws SQLException
     */
    public static boolean deleteCustomer(int custID) throws SQLException {
        Connection connect;
        try {
            connect = JDBC.getConnection();
            String sql = "DELETE FROM customers WHERE Customer_ID = ?;";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, custID);
            statement.execute();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

}