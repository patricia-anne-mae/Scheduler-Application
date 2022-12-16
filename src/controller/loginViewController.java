package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import helper.userHelper;
import javafx.stage.Stage;
import model.appointment;
import helper.appointmentHelper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class loginViewController implements Initializable {
    public TextField usernameField;
    public TextField passwordField;
    public Button loginButton;
    public Label locationLabel;
    public Label locationZone;

    private final ResourceBundle myBundle = ResourceBundle.getBundle("language/login", Locale.getDefault());
    public Label usernameLogin;
    public Label passwordLogin;
    public Label logIn;

    private ResourceBundle resourceBundle;



    /**
     * This method allows users to login using valid user and password. Upon logging in, if there are appointments
     * within the next 15 minutes, an alert will show stating the time. If there are no upcoming appointment,
     * an alert will show there are no upcoming appointments. This method also includes the login activity tracker,
     * when a user logs in, each user that successfully and unsuccessfully logs in will be tracked on the
     * login_activity.txt.
     *
     * The 15 minute appointment alert is a lambda expression.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onActionLogin(ActionEvent actionEvent) throws SQLException, IOException {
        try {

            ObservableList<appointment> getAllAppointments = appointmentHelper.getAllAppointmentData();
            LocalDateTime currentTimeMinus15Min = LocalDateTime.now().minusMinutes(15);
            LocalDateTime currentTimePlus15Min = LocalDateTime.now().plusMinutes(15);
            LocalDateTime startTime;
            int getAppointmentID = 0;
            LocalDateTime displayTime = null;
            boolean appointmentWithin15Min = false;

            ResourceBundle rb = ResourceBundle.getBundle("language/login", Locale.getDefault());

            String usernameInput = usernameField.getText();
            String passwordInput = passwordField.getText();
            int userId = userHelper.validateUser(usernameInput, passwordInput);

            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            PrintWriter outputFile = new PrintWriter(fileWriter);

            if (userId > 0) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Scheduling Application");
                stage.setScene(scene);
                stage.show();


                outputFile.print("user: " + usernameInput + " successfully logged in at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");


                for (appointment appointment : getAllAppointments) {
                    startTime = appointment.getStartTime();
                    if ((startTime.isAfter(currentTimeMinus15Min) || startTime.isEqual(currentTimeMinus15Min)) && (startTime.isBefore(currentTimePlus15Min) || (startTime.isEqual(currentTimePlus15Min)))) {
                        getAppointmentID = appointment.getAptID();
                        displayTime = startTime;
                        appointmentWithin15Min = true;
                    }
                }
                if (appointmentWithin15Min != false) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment within 15 minutes: " + getAppointmentID + " and appointment start time of: " + displayTime);
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("There is an upcoming appointment within 15 minutes");
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("no upcoming appointments");
                }
            } else if (userId < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("Error"));
                alert.setContentText(rb.getString("Incorrect"));
                alert.show();


                outputFile.print("user: " + usernameInput + " failed login attempt at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");

            }
            outputFile.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    /**
     * This initializes the login language.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        try {

            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);

            ZoneId zone = ZoneId.systemDefault();

            locationZone.setText(String.valueOf(zone));

            rb = ResourceBundle.getBundle("language/login", Locale.getDefault());
            logIn.setText(rb.getString("Login"));
            usernameLogin.setText(rb.getString("username"));
            passwordLogin.setText(rb.getString("password"));
            loginButton.setText(rb.getString("Login"));
            locationLabel.setText(rb.getString("Location"));

        } catch (MissingResourceException e) {
            System.out.println("Resource file missing: " + e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}






