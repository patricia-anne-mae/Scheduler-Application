package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.contact;

import java.sql.Connection;
import java.sql.ResultSet;

public class contactHelper {
    /**
     * Retrieves contact from the database.
     * @return
     */
    public static ObservableList<contact> getAllContactData() {
        Connection connect;
        ObservableList<contact> contacts = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            String sql = "SELECT * FROM contacts;";
            ResultSet resultSet = connect.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                contact newContact = new contact(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email"));
                contacts.add(newContact);
            }
            return contacts;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }



}

