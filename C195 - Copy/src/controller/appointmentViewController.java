package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import helper.contactHelper;
import helper.customerHelper;
import helper.userHelper;
import helper.appointmentHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;

public class appointmentViewController {
    public TextField appIDField;
    public TextField appTitleField;
    public TextField appDescriptionField;
    public TextField appLocationField;
    public TextField appTypeField;
    public DatePicker startDatePicker;
    public ComboBox<LocalTime> startTimeCombo;
    public DatePicker endDatePicker;
    public ComboBox<LocalTime> endTimeCombo;
    public ComboBox appContactCombo;
    public ComboBox customerIDCombo;
    public ComboBox userIDCombo;
    public Button appSaveButton;
    public Button appCancelButton;

    /**
     * This method allows user to modify and add appointments. Leaving a field empty and inputting invalid times
     * will show an alert box. When a user clicks the save button, the input saves and user returns to the main
     * screen.
     *
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void onActionSaveApp(ActionEvent actionEvent) throws IOException, SQLException {

        if (emptyField()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please do not leave fields empty");
            alert.showAndWait();
            return;

        } else if (validateTimeChecker()) {
            return;

        } else {
            try {
                if (appIDField.getText().isEmpty()) {
                    appointmentHelper.createAppointment(
                            appTitleField.getText(),
                            appDescriptionField.getText(),
                            appLocationField.getText(),
                            editContact(),
                            appTypeField.getText(),
                            LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getValue()),
                            LocalDateTime.of(endDatePicker.getValue(), endTimeCombo.getValue()),
                            editCustomer(),
                            (editUser()));

                } else {

                    appointmentHelper.updateAppointments(
                            Integer.parseInt(appIDField.getText()),
                            appTitleField.getText(),
                            appDescriptionField.getText(),
                            appLocationField.getText(),
                            editContact(),
                            appTypeField.getText(),
                            LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getValue()),
                            LocalDateTime.of(endDatePicker.getValue(), endTimeCombo.getValue()),
                            editCustomer(),
                            (editUser()));
                }
                Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Scheduling Application");
                stage.setScene(scene);
                stage.show();
            } catch (Exception exception) {
                System.out.println("Error: " + exception.getMessage());
                exception.printStackTrace();
            }
        }
    }

    /**
     * This will remove any non-integer options from the contact combo box.
     *
     * @return
     */
    public int editContact() {
        String str = String.valueOf(appContactCombo.getValue());
        String contactCBInt = str.replaceAll("\\D+", "");
        return Integer.parseInt(contactCBInt);
    }

    /**
     * This will remove any non-integer options from the customer combo box.
     *
     * @return
     */
    public int editCustomer() {
        String str = String.valueOf(customerIDCombo.getValue());
        String customerCBInt = str.replaceAll("\\D+", "");
        return Integer.parseInt(customerCBInt);
    }

    /**
     * This will remove any non-integer options from the user combo box.
     *
     * @return
     */
    public int editUser() {
        String str = String.valueOf(userIDCombo.getValue());
        String userCBInt = str.replaceAll("\\D+", "");
        return Integer.parseInt(userCBInt);
    }

    /**
     * This will set the appointment ID for the customer overlap.
     *
     * @return
     */
    public int setAppointID() {
        int setAppointID;
        if (appIDField.getText().isEmpty()) {
            setAppointID = 0;
        } else {
            setAppointID = Integer.parseInt(appIDField.getText());
        }
        return setAppointID;
    }


    /**
     * This will ensure that the time inputted is valid. Alert will show if time inputted is not within business
     * hours, if end time is before start time, if start and end time is before the current time.
     *
     * @return
     */
    //check this later
    public boolean validateTimeChecker() {

        LocalDateTime appointmentStartTime = LocalDateTime.of(startDatePicker.getValue(),
                LocalTime.parse(startTimeCombo.getSelectionModel().getSelectedItem().toString()));
        LocalDateTime appointmentEndTime = LocalDateTime.of(endDatePicker.getValue(),
                LocalTime.parse(endTimeCombo.getSelectionModel().getSelectedItem().toString()));


        LocalTime apptStartTime = LocalTime.from(appointmentStartTime);
        LocalTime apptEndTime = LocalTime.from(appointmentEndTime);

        if(appointmentStartTime.equals(appointmentEndTime)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Appointment start and end times cannot be the same");
            alert.showAndWait();
            return true;

        } //code below is for choosing a time that is before the current time, which will provide an error.
        if(startDatePicker.getValue().equals(LocalDate.now()) && apptStartTime.isBefore(LocalTime.now()) ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!!");
            alert.setContentText("Appointment start time cannot be before current time.");
            alert.showAndWait();
            return true;

        }
        if(startDatePicker.getValue().equals(LocalDate.now()) && apptEndTime.isBefore(LocalTime.now())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("The appointment end time cannot be before the current time.");
            alert.showAndWait();
            return true;

        }
        if(startDatePicker.getValue().isBefore(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Appointment start date cannot be before the current date.");
            alert.showAndWait();
            return true;

        }
        if(endDatePicker.getValue().isBefore(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Appointment end date cannot be before the current date.");
            alert.showAndWait();
            return true;

        }
        if(appointmentStartTime.isAfter(appointmentEndTime)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Appointment start time cannot be after end time.");
            alert.showAndWait();
            return true;

        }

        if(startDatePicker.getValue().isBefore(endDatePicker.getValue()) ||
                startDatePicker.getValue().isAfter(endDatePicker.getValue())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Appointment must be between business hours.");
            alert.showAndWait();
            return true;

        }
        if(verifyCustomerOverlap()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Appointment cannot overlap with existing appointment.");
            alert.showAndWait();
            return true;

        }
        return false;
    }


        /**
         * This will ensure that the customers are not overlapping in appointments.
         * @return
         */
        public boolean verifyCustomerOverlap () {
            LocalDateTime startTime = LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getValue());
            LocalDateTime endTime = LocalDateTime.of(endDatePicker.getValue(), endTimeCombo.getValue());

            /*LocalDateTime startTime = LocalDateTime.of(startDatePicker.getValue(),
                    LocalTime.parse(startTimeCombo.getSelectionModel().getSelectedItem().toString()));
            LocalDateTime endTime = LocalDateTime.of(endDatePicker.getValue(),
                    LocalTime.parse(endTimeCombo.getSelectionModel().getSelectedItem().toString()));*/

            int setAppointID = setAppointID();
            ObservableList<appointment> appointmentCustID = appointmentHelper.getCustomerID(editCustomer());
            if (appointmentCustID != null) {
                for (appointment appointments : appointmentCustID) {
                    if (setAppointID == appointments.getAptID()) {
                        return false;
                    }
                    if (appointments.getStartTime().isBefore(endTime) && (startTime.isBefore(appointments.getEndTime()))) {
                        return true;
                    }
                }

            }
            return false;
        }


        /**
         * This method grabs all the data from the database.
         * @param appointments
         */
        public void getAllData (appointment appointments){
            ObservableList<String> contactList = FXCollections.observableArrayList();
            ObservableList<String> customerList = FXCollections.observableArrayList();
            ObservableList<String> userList = FXCollections.observableArrayList();
            ObservableList<LocalTime> startTimeList = FXCollections.observableArrayList();
            ObservableList<LocalTime> endTimeList = FXCollections.observableArrayList();

            try {
                ObservableList<contact> contacts = contactHelper.getAllContactData();
                if (contacts != null) {
                    for (contact contact : contacts) {
                        contactList.add(contact.getID() + " - " + contact.getName());
                    }
                }

                ObservableList<customers> customers = customerHelper.getCustomerTableData();
                if (customers != null) {
                    for (model.customers customer : customers) {
                        customerList.add(customer.getCustomerID() + " - " + customer.getCustomerName());
                    }
                }

                ObservableList<user> users = userHelper.createUserList();
                if (users != null) {
                    for (user user : users) {
                        userList.add(user.getUserID() + " - " + user.getUserName());
                    }
                }
                //this ensures users are only able to schedule appointment during business hours.
                ZonedDateTime estStartHours = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
                LocalTime start = estStartHours.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();
                LocalTime end = start.plusHours(14);

                while (start.isBefore(end)) {
                    startTimeList.add(start);
                    start = start.plusMinutes(5);
                    endTimeList.add(start);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            appContactCombo.setItems(contactList);
            customerIDCombo.setItems(customerList);
            userIDCombo.setItems(userList);
            startTimeCombo.setItems(startTimeList);
            endTimeCombo.setItems(endTimeList);

            if (appointments != null) try {
                appIDField.setText(Integer.toString(appointments.getAptID()));
                appTitleField.setText(appointments.getTitle());
                appDescriptionField.setText(appointments.getDescription());
                appLocationField.setText(appointments.getLocation());
                appContactCombo.setValue(appointments.getContactID() + " - " + appointments.getContactName());
                appTypeField.setText(appointments.getType());
                startDatePicker.setValue(appointments.getStartDate());
                startTimeCombo.setValue(appointments.getStartTime().toLocalTime());
                endDatePicker.setValue(appointments.getEndDate());
                endTimeCombo.setValue(appointments.getEndTime().toLocalTime());
                customerIDCombo.setValue(appointments.getCustomerID());
                userIDCombo.setValue(appointments.getUserID());

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        /**
         * This will check if there are any empty fields.
         * @return
         */
        public boolean emptyField () {
            if (appTitleField.getText().isEmpty()) {
                return true;
            } else if (appDescriptionField.getText().isEmpty()) {
                return true;
            } else if (appLocationField.getText().isEmpty()) {
                return true;
            } else if (appContactCombo.getSelectionModel().isEmpty()) {
                return true;
            } else if (appTypeField.getText().isEmpty()) {
                return true;
            } else if (startDatePicker.getValue() == null) {
                return true;
            } else if (startTimeCombo.getValue() == null) {
                return true;
            } else if (endDatePicker.getValue().toString().isEmpty()) {
                return true;
            } else if (endTimeCombo.getValue().toString().isEmpty()) {
                return true;
            } else if (customerIDCombo.getValue().toString().isEmpty()) {
                return true;
            } else if (userIDCombo.getValue().toString().isEmpty()) {
                return true;
            }
            return false;
        }

        /**
         * This method allows users to click the cancel button, which will delete all the input from the field and
         * return to the main screen. A confirmation box will appear when clicking the cancel button to ensure
         * the user intends to return to the main screen.
         * @param actionEvent
         * @throws IOException
         */
        public void onActionCancelApp (ActionEvent actionEvent) throws IOException {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete all entry. Are you sure you want to return to the main screen?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Scheduling Application");
                stage.setScene(scene);
                stage.show();
            }
        }
    }



