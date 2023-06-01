package cstewart.schedulingapplication.controller;

import DAO.Geographical_DAO;
import cstewart.schedulingapplication.model.Country;
import cstewart.schedulingapplication.model.Division;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCustomer_Controller implements Initializable {
    @FXML public TextField txtCustomerID;
    @FXML public ComboBox<Country> cbCountry;
    @FXML public TextField txtName;
    @FXML public TextField txtAddress;
    @FXML public ComboBox<Division> cbStateProvince;
    @FXML public TextField txtPostalCode;
    @FXML public TextField txtPhone;
    @FXML public Button btnAddCustomer;
    @FXML public Button btnExitScreen;

    /** A method that allows user to exit screen and go back to the customer screen.
     * *
     * @param actionEvent to exit screen and go back to the customer screen. */
    @FXML
    public void onClickExitScreen(ActionEvent actionEvent) throws IOException {
        Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
        Scene customerScene = new Scene(customerScreen);
        Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        customerStage.setScene(customerScene);
        customerStage.show();
    }

    /** A method that allows user to go to the appointment screen.
     * *
     * @param actionEvent to go to the appointment screen. */
    @FXML
    public void onClickAppointment(ActionEvent actionEvent) throws IOException {
        Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
        Scene customerScene = new Scene(customerScreen);
        Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        customerStage.setScene(customerScene);
        customerStage.show();
    }

    @FXML
    public void onClickAddCustomer(ActionEvent actionEvent) throws IOException {
        try {
            ZoneId myZoneId = ZoneId.systemDefault();
            ZoneId utcZoneId = ZoneId.of("UTC");
            String tempCustomerName = txtName.getText();
            String tempAddress = txtAddress.getText();
            String tempPostalCode = txtPostalCode.getText();
            String tempPhone = txtPhone.getText();
            Timestamp tempCreatedDate = new Timestamp(System.currentTimeMillis());
            String tempCreated_By = "script";
            Timestamp tempUpdatedDate = new Timestamp(System.currentTimeMillis());
            String tempLast_Updated_By = "script";
            int tempDivision_ID = cbCountry.getValue().getCountry_ID();

            // FIX ME!  TIME CONVERSION ISSUES
            DAO.Customer_DAO.addCustomer(tempCustomerName,tempAddress, tempPostalCode,tempPhone, tempCreatedDate,tempCreated_By,tempUpdatedDate,tempLast_Updated_By,tempDivision_ID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally{
            Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
            Scene customerScene = new Scene(customerScreen);
            Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            customerStage.setScene(customerScene);
            customerStage.show();
        }
    }

    public void onSelectCBCountry(ActionEvent actionEvent) {
        int country_ID = cbCountry.getValue().getCountry_ID();

        try {
            cbStateProvince.setItems(Geographical_DAO.getAllDivisionsByCountry(country_ID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            ObservableList<Country> allCountries = DAO.Geographical_DAO.getAllCountries();
            cbCountry.setItems(allCountries);
            cbCountry.setVisibleRowCount(3);
            cbCountry.setPromptText("Select Country");

            cbStateProvince.setVisibleRowCount(3);
            cbStateProvince.setPromptText("Select State or Province");

        }catch (SQLException e){
            Logger.getLogger(AddCustomer_Controller.class.getName()).log(Level.SEVERE, null, e);

        }
    }

}
