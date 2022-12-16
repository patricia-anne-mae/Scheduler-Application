package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.division;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class divisionHelper {

    /**
     * Retrieves division data.
     * @param country
     * @return
     */
    public ObservableList<division> pullCountry(String country) {
        try {
            String sql = "SELECT first_level_divisions.Division_ID, countries.Country_ID, first_level_divisions.Division  FROM countries, first_level_divisions WHERE countries.Country = ? and countries.Country_ID = first_level_divisions.Country_ID;";
            Connection connect = JDBC.getConnection();
            ObservableList<division> divisions = FXCollections.observableArrayList();
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, country);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                divisions.add(createDivision(rs));
            }
            return divisions;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Generates a division object.
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private division createDivision(ResultSet resultSet) throws SQLException {
        return new division(
                resultSet.getInt("Division_ID"),
                resultSet.getInt("Country_ID"),
                resultSet.getString("Division"));
    }
}
