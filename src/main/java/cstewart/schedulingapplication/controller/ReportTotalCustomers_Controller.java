package cstewart.schedulingapplication.controller;

import DAO.Appointment_DAO;
import DAO.DateTime_DAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

/** A class that allows a user to view the number of customers by the type and month. */
public class ReportTotalCustomers_Controller implements Initializable {
    // Combo boxes for dropdown selections.
    @FXML public ComboBox<String> monthReportTotalCustomers;
    @FXML public ComboBox<String> typeReportTotalCustomers;

    // Label
    @FXML public Label totalReportTotalCustomers;

    /** A method that navigates the counts the number of customers once type and month have been selected by the user.
     @param actionEvent returns a customer count by type and month.
     */
    @FXML
    public void onSelectMonthReportTotalCustomers(ActionEvent actionEvent) throws ParseException {
        try {
            int tempMonth = DateTime_DAO.convertMonthStringToInteger(monthReportTotalCustomers.getSelectionModel().getSelectedItem());
            System.out.println("Month convert = " + tempMonth);
            String tempType= typeReportTotalCustomers.getSelectionModel().getSelectedItem();
            totalReportTotalCustomers.setText(String.valueOf(Appointment_DAO.countAppointmentByTypeAndMonth(tempType, tempMonth)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//TODO: According to The Little Things, this should look differently.  Is the way that I have it going to pass?  Or do I need to adjust this?
    /** A method that navigates the user to the report options screen when "Exit Screen" is selected.
     @param actionEvent navigates to the report options screen.
     */
    @FXML
    public void onClickExitScreenReportTotalCustomers(ActionEvent actionEvent) throws IOException {
        Parent reportOptionsScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportOptions.fxml"));
        Scene reportOptionsScene = new Scene(reportOptionsScreen);
        Stage reportOptionsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportOptionsStage.setScene(reportOptionsScene);
        reportOptionsStage.show();
    }

    /** A method to override the superclass. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<String> allMonths = DateTime_DAO.getMonths();
            monthReportTotalCustomers.setItems(allMonths);
            monthReportTotalCustomers.setVisibleRowCount(5);
            monthReportTotalCustomers.setPromptText("Select Month");

            ObservableList<String> allTypes = Appointment_DAO.getTypes();
            typeReportTotalCustomers.setItems(allTypes);
            typeReportTotalCustomers.setVisibleRowCount(5);
            typeReportTotalCustomers.setPromptText("Select Type");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
