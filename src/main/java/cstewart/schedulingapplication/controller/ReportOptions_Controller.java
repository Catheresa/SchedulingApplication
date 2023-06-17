package cstewart.schedulingapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/** A class that allows a user to view report options and navigate to the desired report. */
public class ReportOptions_Controller {

    /** A method that navigates the user to the report that provides the schedule for the selected contact.
     @param actionEvent navigates to the reportContactSchedule screen.
     */
    @FXML
    public void onClickContactReportOptions(ActionEvent actionEvent) throws IOException {
        Parent reportContactScheduleScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportContactSchedule.fxml"));
        Scene reportContactScheduleScene = new Scene(reportContactScheduleScreen);
        Stage reportContactScheduleStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportContactScheduleStage.setScene(reportContactScheduleScene);
        reportContactScheduleStage.show();
    }

    /** A method that navigates the user to the report that provides the customer list by selected state/division.
     @param actionEvent navigates to the reportCustomersByDivision screen.
     */
    @FXML
    public void onClickCustomerByDivisionReportOptions(ActionEvent actionEvent) throws IOException {
        Parent reportLocationScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportCustomersByDivision.fxml"));
        Scene reportLocationScene = new Scene(reportLocationScreen);
        Stage reportLocationStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportLocationStage.setScene(reportLocationScene);
        reportLocationStage.show();
    }

    /** A method that navigates the user to the report that provides the total number of customers by type and month.
     @param actionEvent navigates to the reportTotalCustomers screen.
     */
    @FXML
    public void onClickTypeReportOptions(ActionEvent actionEvent) throws IOException {
        Parent reportTotalCustomersScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportTotalCustomers.fxml"));
        Scene reportTotalCustomersScene = new Scene(reportTotalCustomersScreen);
        Stage reportTotalCustomersStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportTotalCustomersStage.setScene(reportTotalCustomersScene);
        reportTotalCustomersStage.show();
    }
    /** A method that exits the screen.
     @param actionEvent navigates to the customers screen.
     */
    @FXML
    public void onClickExitScreenReportOptions(ActionEvent actionEvent) throws IOException {
        Parent appointmentScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
        Scene appointmentScene = new Scene(appointmentScreen);
        Stage appointmentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appointmentStage.setScene(appointmentScene);
        appointmentStage.show();
    }
}
