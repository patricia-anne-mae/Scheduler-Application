package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class userHelper {

    public static user currentUser = null;
    public static int userID = 0;

    /**
     * Retrieves data for user from the database.
     * @return
     */
    public static ObservableList<user> createUserList() {
        Connection connect;
        ObservableList<user> users = FXCollections.observableArrayList();
        try {
            connect = JDBC.getConnection();
            String SQL = "SELECT User_ID, User_Name FROM users;";
            ResultSet rs = connect.createStatement().executeQuery(SQL);
            while (rs.next()) {
                user newUser = new user(
                        rs.getInt("User_ID"),
                        rs.getString("User_Name"));
                users.add(newUser);
            }
            return users;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Validates login input.
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static boolean validateLogin(String username, String password) throws SQLException {
        try {
            String sql = "SELECT * FROM users WHERE User_Name='" + username + "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (password.matches(rs.getString("Password"))) {
                    userID = rs.getInt("User_ID");
                    user user = new user(rs.getInt("User_ID"), rs.getString("User_Name"));
                    //int curruntUserID = rs.getInt("User_ID");
                    currentUser = user;
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    /**
     * Validates login input.
     * @param username
     * @param password
     * @return
     */
    public static int validateUser(String username, String password)
    {
        try
        {
            String sqlQuery = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password +"'";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getString("User_Name").equals(username))
            {
                if (rs.getString("Password").equals(password))
                {
                    return rs.getInt("User_ID");

                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }


}

