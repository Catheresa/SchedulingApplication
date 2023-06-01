package cstewart.schedulingapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportOptions_Controller {

    @FXML
    public void onClickContactReportOptions(ActionEvent actionEvent) throws IOException {
        Parent reportContactScheduleScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportContactSchedule.fxml"));
        Scene reportContactScheduleScene = new Scene(reportContactScheduleScreen);
        Stage reportContactScheduleStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportContactScheduleStage.setScene(reportContactScheduleScene);
        reportContactScheduleStage.show();
    }
    @FXML
    public void onClickCustomerByDivisionReportOptions(ActionEvent actionEvent) throws IOException {
        Parent reportLocationScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportCustomersByDivision.fxml"));
        Scene reportLocationScene = new Scene(reportLocationScreen);
        Stage reportLocationStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportLocationStage.setScene(reportLocationScene);
        reportLocationStage.show();
    }
    @FXML
    public void onClickTypeReportOptions(ActionEvent actionEvent) throws IOException {
        Parent reportTotalCustomersScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportTotalCustomers.fxml"));
        Scene reportTotalCustomersScene = new Scene(reportTotalCustomersScreen);
        Stage reportTotalCustomersStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportTotalCustomersStage.setScene(reportTotalCustomersScene);
        reportTotalCustomersStage.show();
    }
    @FXML
    public void onClickExitScreenReportOptions(ActionEvent actionEvent) throws IOException {
        Parent appointmentScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
        Scene appointmentScene = new Scene(appointmentScreen);
        Stage appointmentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appointmentStage.setScene(appointmentScene);
        appointmentStage.show();
    }
}
