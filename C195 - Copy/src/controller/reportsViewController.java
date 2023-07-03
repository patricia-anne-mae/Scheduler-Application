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

    //reference to reports object
    private reports currentReport;

    /**
     * Creates instances
     * @param actionEvent
     */
    public void onActionViewAppMonth(ActionEvent actionEvent) {
        currentReport = new ReportByMonthType(); //create an instance of ReportByMonthType
    }

    public void onActionViewContact(ActionEvent actionEvent) {
        currentReport = new ReportByContact(); //create an instance of ReportByContact
    }

    public void onActionViewAppLoc(ActionEvent actionEvent) {
        currentReport = new ReportByLocation(); //create an instance of ReportByLocation
    }

    /**
     * This method generates the reports depending on what the user selects.
     * @param actionEvent
     */
    public void onActionGenerate(ActionEvent actionEvent) {
        if (currentReport != null) {
            reportTextArea.setText(currentReport.generateReport());
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