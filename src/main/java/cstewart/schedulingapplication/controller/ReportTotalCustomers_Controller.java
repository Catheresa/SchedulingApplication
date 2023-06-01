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

public class ReportTotalCustomers_Controller implements Initializable {
    @FXML public Label totalReportTotalCustomers;
    @FXML public ComboBox<String> monthReportTotalCustomers;
    @FXML public ComboBox<String> typeReportTotalCustomers;

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

    @FXML
    public void onClickExitScreenReportTotalCustomers(ActionEvent actionEvent) throws IOException {
        Parent reportOptionsScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportOptions.fxml"));
        Scene reportOptionsScene = new Scene(reportOptionsScreen);
        Stage reportOptionsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportOptionsStage.setScene(reportOptionsScene);
        reportOptionsStage.show();
    }

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
