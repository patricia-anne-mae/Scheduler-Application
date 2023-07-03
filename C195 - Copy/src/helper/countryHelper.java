package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.country;

import java.sql.Connection;
import java.sql.ResultSet;

public class countryHelper {

    /**
     * Generates a list of country.
     * @return
     */
    public static ObservableList<country> getCountries() {
        Connection connect;
        ObservableList<country> countries = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            String SQL = "SELECT * FROM countries";
            ResultSet resultSet = connect.createStatement().executeQuery(SQL);
            while (resultSet.next()) {
                country newCountry = new country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country"));
                countries.add(newCountry);
            }
            return countries;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
