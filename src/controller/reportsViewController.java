package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import helper.appointmentHelper;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class reportsViewController {
    public TextArea reportTextArea;
    public RadioButton appByMonthType;
    public RadioButton contactSchedule;
    public RadioButton appByLocation;
    public Button generateButton;
    public Button resetButton;
    public Button returnToMain;
    public ToggleGroup reportsToggle;

    public void onActionViewAppMonth(ActionEvent actionEvent) {
    }

    public void onActionViewContact(ActionEvent actionEvent) {
    }

    public void onActionViewAppLoc(ActionEvent actionEvent) {
    }

    /**
     * This method generates the reports depending on what the user selects.
     * @param actionEvent
     */
    public void onActionGenerate(ActionEvent actionEvent) {
        if (appByMonthType.isSelected()) {
            reportTextArea.setText(appointmentHelper.getReportTypeMonth());
        }
        if (contactSchedule.isSelected()) {
            reportTextArea.setText(appointmentHelper.getReportContact());
        }
        if (appByLocation.isSelected()) {
            reportTextArea.setText(appointmentHelper.reportAppointmentTypeLocation());
        }
    }

    /**
     * This method allows the user to clear the text area.
     * @param actionEvent
     */
    public void onActionReset(ActionEvent actionEvent) {
        reportTextArea.clear();
    }

    /**
     * This method allows the user to return to the main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onActionReturnToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }
}
