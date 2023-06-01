package cstewart.schedulingapplication.controller;

import DAO.Geographical_DAO;
import cstewart.schedulingapplication.model.Country;
import cstewart.schedulingapplication.model.Customer;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateCustomer_Controller implements Initializable {
    @FXML public TextField txtCustomerID;
    @FXML public ComboBox<Country> cbCountry;
    @FXML public TextField txtName;
    @FXML public TextField txtAddress;
    @FXML public ComboBox<Division> cbStateProvince;
    @FXML public TextField txtPostalCode;
    @FXML public TextField txtPhone;
    //Buttons to update data or move to a new screen
    @FXML public Button btnAppointment;
    @FXML public Button btnUpdateCustomer;
    @FXML public Button btnExitScreen;

    Customer identifiedCustomer;
    Country identifiedCountry;
//    int identifiedCountry;

    @FXML
    public void sendCustomer(Customer customer) throws SQLException {
        identifiedCustomer = customer;
//        identifiedCountry = DAO.Customer_DAO.identifyCustomerCountry(identifiedCustomer.getCustomer_ID());
//        cbStateProvince.setItems(DAO.Geographical_DAO.getAllDivisions());
        Country tempCountry = Geographical_DAO.getCountryByDivision(customer.getDivision_ID());
        cbCountry.setValue(tempCountry);
        cbStateProvince.setItems(Geographical_DAO.getAllDivisionsByCountry(tempCountry.getCountry_ID()));
        Division div = Geographical_DAO.getDivision(customer.getDivision_ID());
        cbStateProvince.setValue(div);
        txtCustomerID.setText(String.valueOf(identifiedCustomer.getCustomer_ID()));
        txtName.setText(identifiedCustomer.getCustomer_Name());
        txtAddress.setText(String.valueOf(identifiedCustomer.getAddress()));
        txtPostalCode.setText(String.valueOf(identifiedCustomer.getPostal_Code()));
        txtPhone.setText(String.valueOf(identifiedCustomer.getPhone()));

    }

    @FXML
    public void onClickExitScreen(ActionEvent actionEvent) throws IOException {
        Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
        Scene customerScene = new Scene(customerScreen);
        Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        customerStage.setScene(customerScene);
        customerStage.show();
    }
    @FXML
    public void onClickAppointment(ActionEvent actionEvent) throws IOException {
        Parent appointmentScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
        Scene appointmentScene = new Scene(appointmentScreen);
        Stage appointmentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appointmentStage.setScene(appointmentScene);
        appointmentStage.show();
    }
    @FXML
    public void onSelectCountry(ActionEvent actionEvent) {
        int country_ID = cbCountry.getValue().getCountry_ID();

        try {
            cbStateProvince.setItems(Geographical_DAO.getAllDivisionsByCountry(country_ID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onSelectStateProvince(ActionEvent actionEvent) {
    }
    @FXML
    public void onClickUpdateCustomer(ActionEvent actionEvent) throws IOException {
        String tempContactName = txtName.getText();
        String tempAddress = txtAddress.getText();
        String tempPostalCode = txtPostalCode.getText();
        String tempPhone = txtPhone.getText();
        Timestamp tempCreate_Date = new Timestamp(System.currentTimeMillis());;
        String tempCreated_By = "script";
        Timestamp tempLastUpdate = new Timestamp(System.currentTimeMillis());;
        String tempLastUpdated_By = "script";
        int tempDivisionID = cbCountry.getValue().getCountry_ID();
        int tempID = Integer.parseInt(txtCustomerID.getText());

        try {
            DAO.Customer_DAO.updateCustomer(tempContactName,tempAddress, tempPostalCode, tempPhone,tempCreate_Date,tempCreated_By, tempLastUpdate,
                    tempLastUpdated_By,tempDivisionID, tempID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Parent optionScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
            Scene optionScene = new Scene(optionScreen);
            Stage optionStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            optionStage.setScene(optionScene);
            optionStage.show();
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
