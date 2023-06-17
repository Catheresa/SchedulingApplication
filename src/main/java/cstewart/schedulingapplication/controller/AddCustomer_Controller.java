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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/** A class that allows a user to add a customer to the database. */
public class AddCustomer_Controller implements Initializable {
    // Combo boxes for dropdown selections.
    @FXML private ComboBox<Country> cbCountry;
    @FXML private ComboBox<Division> cbStateProvince;

    // Text fields for adding customer details.
    @FXML private TextField txtAddress;
    @FXML private TextField txtName;
    @FXML private TextField txtPhone;
    @FXML private TextField txtPostalCode;

    /** A method that allows user to add a customer to the database.
     @param actionEvent to add customer.
     */
    @FXML
    public void onClickAddCustomer(ActionEvent actionEvent) throws IOException {
        try {
            String tempCustomerName = txtName.getText();
            String tempAddress = txtAddress.getText();
            String tempPostalCode = txtPostalCode.getText();
            String tempPhone = txtPhone.getText();

            if ((cbCountry.getValue() == null) || (cbStateProvince.getValue() == null) || (cbCountry.getValue() == null) ||
                    (txtName.getText().isEmpty()) || (txtAddress.getText().isEmpty()) || (txtPostalCode.getText().isEmpty() ||
                    (txtPhone.getText().isEmpty()))) {

                Alert selectionError = new Alert(Alert.AlertType.ERROR);
                selectionError.setTitle("Selection Error");
                selectionError.setHeaderText("Retry");
                selectionError.setContentText("Please ensure that a country and state/province is selected and all fields are populated.");
                selectionError.showAndWait();

                return;

            } else {
                int tempStateProvince_ID = cbStateProvince.getValue().getDivision_ID();
                DAO.Customer_DAO.addCustomer(tempCustomerName, tempAddress, tempPostalCode, tempPhone, tempStateProvince_ID);

                Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
                Scene customerScene = new Scene(customerScreen);
                Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                customerStage.setScene(customerScene);
                customerStage.show();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
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

    /** A method that allows user to exit screen and go back to the customer screen.
     * @param actionEvent to exit screen and go back to the customer screen. */
    @FXML
    public void onClickExitScreen(ActionEvent actionEvent) throws IOException {
        Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
        Scene customerScene = new Scene(customerScreen);
        Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        customerStage.setScene(customerScene);
        customerStage.show();
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
