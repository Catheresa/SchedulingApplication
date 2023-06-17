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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/** A class that allows a user to update and existing customer. */
public class UpdateCustomer_Controller implements Initializable {
    // Combo boxes for dropdown selections.
    @FXML public ComboBox<Country> cbCountry;
    @FXML public ComboBox<Division> cbStateProvince;

    // Text fields for updating customer details.
    @FXML public TextField txtAddress;
    @FXML public TextField txtCustomerID;
    @FXML public TextField txtName;
    @FXML public TextField txtPhone;
    @FXML public TextField txtPostalCode;

    Customer identifiedCustomer;

    /** A method utilized to send selected customer data to the "UpdateCustomer" screen.
     @param customer send selected customer to the update customer screen.
     */
    @FXML
    public void sendCustomer(Customer customer) throws SQLException {
        identifiedCustomer = customer;

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

    /** A method utilized to return to the customer screen without making changes.
     @param actionEvent return to the customer screen.
     */
    @FXML
    public void onClickExitScreen(ActionEvent actionEvent) throws IOException {
        Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
        Scene customerScene = new Scene(customerScreen);
        Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        customerStage.setScene(customerScene);
        customerStage.show();
    }

    /** A method utilized to load the "State/Prov" combo box based on the country selected.
     @param actionEvent provide corresponding states or province based on the country selected.
     */
    @FXML
    public void onSelectCountry(ActionEvent actionEvent) {
        int country_ID = cbCountry.getValue().getCountry_ID();

        try {
            cbStateProvince.setItems(Geographical_DAO.getAllDivisionsByCountry(country_ID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** A method utilized to update customer data in the SQL database.
     @param actionEvent update customer data in the SQL database.
     */
    @FXML
    public void onClickUpdateCustomer(ActionEvent actionEvent) throws IOException {
        String tempContactName = txtName.getText();
        String tempAddress = txtAddress.getText();
        String tempPostalCode = txtPostalCode.getText();
        String tempPhone = txtPhone.getText();
        int tempStateProvince = cbStateProvince.getValue().getDivision_ID();
        int tempID = Integer.parseInt(txtCustomerID.getText());

        try {
            DAO.Customer_DAO.updateCustomer(tempContactName,tempAddress, tempPostalCode, tempPhone,tempStateProvince, tempID);

            Parent optionScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
            Scene optionScene = new Scene(optionScreen);
            Stage optionStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            optionStage.setScene(optionScene);
            optionStage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** A method to override the superclass. */
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
