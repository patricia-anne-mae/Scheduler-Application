package controller;

import helper.divisionHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import helper.customerHelper;
import model.country;
import model.customers;
import helper.countryHelper;
import model.division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


public class customerViewController implements Initializable {
    public TextField customerIDField;
    public TextField customerNameField;
    public TextField customerAddressField;
    public TextField customerContactField;
    public TextField customerZipField;
    public ComboBox<String> customerCountryCombo;
    public ComboBox<String> customerDivisionCombo;
    public Button saveCustomerButton;
    public Button cancelCustomerButton;

    /**
     * Observable list from the country helper.
     */
    private ObservableList countriesList = FXCollections.observableArrayList();

    /**
     * This method allows users save inputting data. If a user leaves empty fields, an alert box will show.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void onActionSaveCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        if (emptyField()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please fill out all fields");
            alert.showAndWait();
            return;
        } else if (customerIDField.getText().isEmpty()) {
            customerHelper.createCustomer(
                    customerNameField.getText(),
                    customerAddressField.getText(),
                    customerZipField.getText(),
                    customerContactField.getText(),
                    editDivison());
        } else try {
            customerHelper.updateCustomer(
                    Integer.parseInt(customerIDField.getText()),
                    customerNameField.getText(),
                    customerAddressField.getText(),
                    customerZipField.getText(),
                    customerContactField.getText(),
                    editDivison());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method allows users to click on the cancel button. If the user clicks cancel, all input will be
     * deleted and user will return to main screen.. A confirmation box will appear for the user to confirm
     * this is their intended action.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionCancelCustomer(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete all entry. Are you sure you want to return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainView.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Scheduling Application");
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * This will check the customer's information are filled and no empty fields are left.
     * @return
     */
    public boolean emptyField() {
        if (customerNameField.getText().isEmpty()) {
            return true;
        } else if (customerAddressField.getText().isEmpty()) {
            return true;
        } else if (customerZipField.getText().isEmpty()) {
            return true;
        } else if (customerContactField.getText().isEmpty()) {
            return true;
        } else if (customerCountryCombo.getValue().toString().isEmpty()) {
            return true;
        } else if (editDivison().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * This will ensure there are no non-integer options in the division combo box.
     * @return
     */
    public String editDivison() {
        String div = (String) customerDivisionCombo.getValue();
        return div.replaceAll("\\D+", "");
    }

    /**
     * This will get all the data for the customer.
     * @param customer
     */
    public void getAllCustomerData(customers customer) {
        try {
            ObservableList<country> countries = countryHelper.getCountries();
            if (countries != null) {
                for (country country : countries) {
                    countriesList.add(country.getCountry());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        customerCountryCombo.setItems(countriesList);

        if (customer != null) try {

            customerIDField.setText(Integer.toString(customer.getCustomerID()));
            customerAddressField.setText((customer.getCustomerAddress()));
            customerNameField.setText(customer.getCustomerName());
            customerZipField.setText((customer.getCustomerZipCode()));
            customerContactField.setText((customer.getCustomerPhone()));
            customerCountryCombo.setValue(customer.getCountryID() + " - " + customer.getCountry());
            customerDivisionCombo.setValue(customer.getDivisionID() + " - " + customer.getDivision());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This will grab all the data from the countries and input it into the combo box.
     * @param actionEvent
     */
    public void onActionSelectCountry(ActionEvent actionEvent) {
        ObservableList<String> divs = FXCollections.observableArrayList();
        try {
            ObservableList<division> divisions = new divisionHelper().pullCountry((String) customerCountryCombo.getSelectionModel().getSelectedItem());
            if (divisions != null) {
                for (division division : divisions) {
                    divs.add(division.getDivisionID() + " - " + division.getDivision());
                }
            }
            customerDivisionCombo.setItems(divs);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
