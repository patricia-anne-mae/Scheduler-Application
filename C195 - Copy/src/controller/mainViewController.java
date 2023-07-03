package controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import helper.appointmentHelper;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.appointment;
import model.customers;
import helper.customerHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class mainViewController {
    public TableView<appointment> appointmentTable;
    public TableColumn<appointment, Integer> appIDColumn;
    public TableColumn<appointment, String> appTitleColumn;
    public TableColumn<appointment, String> appDescriptionColumn;
    public TableColumn<appointment, String> appLocationColumn;
    public TableColumn<appointment, String> appContactColumn;
    public TableColumn<appointment, String> appTypeColumn;
    public TableColumn<appointment, Integer> appStartDTColumn;
    public TableColumn<appointment, Integer> appEndDTColumn;
    public TableColumn<appointment, Integer> appCustomerIDColumn;
    public TableColumn<appointment, Integer> appUserIDColumn;
    public RadioButton viewAllRadio;
    public ToggleGroup AppointmentToggle;
    public RadioButton viewWeeksRadio;
    public RadioButton viewMonthsRadio;
    public Button addAppButton;
    public Button modifyAppButton;
    public Button deleteAppButton;
    public TableView<customers> customerTable;
    public TableColumn<customers, Integer> customerIDColumn;
    public TableColumn<customers, String> customerNameColumn;
    public TableColumn<customers, String> customerAddressColumn;
    public TableColumn<customers, Integer> customerZipColumn;
    public TableColumn<customers, String> customerContatColumn;
    public TableColumn<customers, String> customerCountryColumn;
    public TableColumn<customers, String> customerDivColumn;
    public Button addCustomerButton;
    public Button modifyCustomerButton;
    public Button deleteCustomerButton;
    public Button viewReportsButton;
    public Button ExitButton;

    public ObservableList selectedToggle;
    public TextField searchbox;
    /**
     * Observable list for appointment in save method.
     */
    ObservableList<model.appointment> Appointments = FXCollections.observableArrayList();


    /**
     * This method takes the user to the add appointment view, where the user can add a new appointment.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onActionAddApp(ActionEvent actionEvent) throws IOException {
        appointment appointments = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/appointmentView.fxml"));
        Parent scene = loader.load();
        appointmentViewController controller = loader.getController();
        controller.getAllData(appointments);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method will allow the user to go to the appointment view and modify the data. If there is no selected
     * appointment, an alert box will show.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onActionModifyApp(ActionEvent actionEvent) throws IOException {
        appointment selectedAppointment = (appointment) appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/appointmentView.fxml"));
            Parent scene = loader.load();
            appointmentViewController controller = loader.getController();
            controller.getAllData(selectedAppointment);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("No appointment selected, please select an appointment.");
            alert.showAndWait();
        }
    }

    /**
     * This method allows users to delete an appointment. If no appointment is selected, an alert will show.
     * When a user selects an appointment, and clicks delete, a confirmation box will appear asking the user
     * if they are sure they want to delete. When a user confirms, another dialog box will appear confirming
     * the deletion with the ID and type listed.
     *
     * @param actionEvent
     * @throws SQLException
     */
    public void onActionDeleteApp(ActionEvent actionEvent) throws SQLException {
        if (appointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("No appointment selected. Please select an appointment to continue.");
            alert.showAndWait();
        } else { //user confirmation for deleting appointment
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int deletedApptID = appointmentTable.getSelectionModel().getSelectedItem().getAptID();
                String deletedApptType = appointmentTable.getSelectionModel().getSelectedItem().getType();
                appointmentHelper.deleteAppointment(appointmentTable.getSelectionModel().getSelectedItem().getAptID());
                //after user confirms delete, a dialog box will appear stating the ID and type was deleted.
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Warning Dialog");
                alert2.setContentText("Appointment ID " + deletedApptID + " with type of " + deletedApptType + " successfully deleted.");
                alert2.showAndWait();

                Appointments = appointmentHelper.getAllAppointmentData();
                appointmentTable.setItems(Appointments);
                appointmentTable.refresh();
            } else {
                Appointments = appointmentHelper.getAllAppointmentData();
                appointmentTable.setItems(Appointments);
                appointmentTable.refresh();
            }
        }
    }


    /**
     * This method allows users to add a new customer, taking the user to the customer view.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onActionAddCustomer(ActionEvent actionEvent) throws IOException {
        customers customer = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/customerView.fxml"));
        Parent scene = loader.load();
        customerViewController controller = loader.getController();
        controller.getAllCustomerData(customer);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method allows users to modify existing customer and takes the user to the customer view.
     * If no customer is selected, an alert box will show.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onActionModifyCustomer(ActionEvent actionEvent) throws IOException {
        customers selectedCustomer = (customers) customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/customerView.fxml"));
            Parent scene = loader.load();
            customerViewController controller = loader.getController();
            controller.getAllCustomerData(selectedCustomer);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("No customer selected, please select a customer.");
            alert.showAndWait();
        }
    }

    /**
     * This method allows users to delete a customer. A confirmation box will appear to confirm with the user
     * that this is their intended action. If no customer is selected, an alert will show.
     *
     * @param actionEvent
     * @throws SQLException
     */
    public void onActionDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        customers selectedCustomer = (customers) customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this customer?");
            alert.setHeaderText("Delete " + selectedCustomer.getCustomerName() + " and all associated appointments.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                customerHelper.deleteCustomer(selectedCustomer.getCustomerID());
                appointmentHelper.deleteAppointment(selectedCustomer.getCustomerID());
                customerTable.setItems(customerHelper.createCustomerList());
                appointmentTable.setItems(appointmentHelper.getAllAppointmentData());
                customerTable.setItems(customerHelper.getCustomerTableData());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("No customer selected, please select a customer.");
            alert.showAndWait();
        }
    }

    /**
     * This method takes the user to the report view.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void onActionViewReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/reportsView.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * This includes a lambda expression.
     *
     * This method allows users to exit the program.
     *
     * @param actionEvent
     */
    public void onActionExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }

    /**
     * This method changes the table view.
     *
     * @param actionEvent
     */
    public void onActionViewToggle(ActionEvent actionEvent) {
        if (viewAllRadio.isSelected()) {
            selectedToggle = appointmentHelper.getAllAppointmentData();
        } else if (viewWeeksRadio.isSelected()) {
            selectedToggle = appointmentHelper.getAppointmentWeeks();
        } else if (viewMonthsRadio.isSelected()) {
            selectedToggle = appointmentHelper.monthList();
        }
        appointmentTable.setItems(selectedToggle);
    }

    /**
     *This contains lambda expression to populate the table
     *
     * This initializes the table and populates the values into the corresponding columns.
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        customerTable.setItems(customerHelper.getCustomerTableData());
        customerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        customerAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerAddress()));
        customerContatColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerPhone()));
        customerCountryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerDivColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerZipColumn.setCellValueFactory(new PropertyValueFactory<>("customerZipCode"));


        appointmentTable.setItems(appointmentHelper.getAllAppointmentData());
        appIDColumn.setCellValueFactory(new PropertyValueFactory<>("aptID"));
        appTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        appContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartDTColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appEndDTColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        appCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    public void OnActionSearchCustomer(ActionEvent actionEvent) throws SQLException {
        ObservableList<customers> customerSearchReturn = customerHelper.getCustomerTableData();


        FilteredList<customers> filteredList = new FilteredList<>(customerSearchReturn, b -> true);

        searchbox.requestFocus();

        searchbox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Customers -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String search = newValue.toLowerCase();


                if (Customers.getCustomerName().toLowerCase().indexOf(search) != -1) {
                    return true;
                } else if (Customers.getCustomerAddress().toLowerCase().indexOf(search) != -1) {
                    return true;
                } else if (Customers.getDivision().toLowerCase().indexOf(search) != -1) {
                    return true;
                } else if (Customers.getCustomerZipCode().toLowerCase().indexOf(search) != -1) {
                    return true;
                } else if (Customers.getCountry().toLowerCase().indexOf(search) != -1) {
                    return true;
                } else if (Customers.getCustomerPhone().toLowerCase().indexOf(search) != -1) {
                    return true;
                } else return false;
            });
        });

        SortedList<customers> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortedList);
    }

        /*ObservableList<customers> customers = customerHelper.getCustomerTableData();
        customerTable.setItems(customers);

        filtererdCustomers<customers> filteredcustomers = new filteredcustomers<> (customersearch, b-> return)

        searchbox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCustomers(newValue);
        });*/
    }









