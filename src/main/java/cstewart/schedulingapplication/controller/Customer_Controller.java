package cstewart.schedulingapplication.controller;

import DAO.Appointment_DAO;
import DAO.Customer_DAO;
import cstewart.schedulingapplication.model.Country;
import cstewart.schedulingapplication.model.Customer;
import cstewart.schedulingapplication.model.Division;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer_Controller implements Initializable {
    //The name or ID search fields:
    @FXML private TextField searchCustomer;

    //The customer table and columns:
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerIDCol;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableColumn<Country, Integer> countryIDCol;
    @FXML private TableColumn<Country, String> countryCol;
    @FXML private TableColumn<Customer, String> customerAddressCol;
    @FXML private TableColumn<Customer, String> customerPostalCodeCol;
    @FXML private TableColumn<Division, Integer> divisionIDCol;
    @FXML private TableColumn<Division, String> stateCol;
    @FXML private TableColumn<Customer, String> customerPhoneCol;

    Customer selectedCustomer;

    /** A method that allows a user to input a customer ID or partial customer name into a text field to identify a customer.
     * *
     * @param actionEvent to search for a specific customer. */
    @FXML
    public void onInputSearchCustomer(ActionEvent actionEvent) throws SQLException {
        String partialName = searchCustomer.getText();
        ObservableList<Customer> searchCustomerName = DAO.Customer_DAO.lookupCustomer(partialName);
        customerTable.setItems(searchCustomerName);

        if(searchCustomerName.size() == 0){
            try{
                int tempID = Integer.parseInt(partialName);
                Customer customer = DAO.Customer_DAO.lookupCustomer(tempID);
                if(customer != null){
                    searchCustomerName.add(customer);
                }else{throw new Exception();}

            }catch (Exception currentError){
                Alert loginError = new Alert(Alert.AlertType.ERROR);
                loginError.setTitle("Search Error");
                loginError.setHeaderText("Search Error " + currentError);
                loginError.setContentText("Try again. No match was found.  Press 'Enter' to reload.");
                loginError.show();
            }
        }
        searchCustomer.setText("");
    }

    /** A method that loads the add customer screen when the user clicks on "Add" button.
     * *
     * @param actionEvent go to add customer screen. */
    @FXML
    public void onClickAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent addCustomerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/addCustomer.fxml"));
        Scene addCustomerScene = new Scene(addCustomerScreen);
        Stage addCustomerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addCustomerStage.setScene(addCustomerScene);
        addCustomerStage.show();
    }

    /** A method that loads the update customer screen when the user selects a customer and clicks on the "Add" button.
     * *
     * @param actionEvent go to update customer screen. */
    @FXML
    public void onClickUpdateCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            if (customerTable.getSelectionModel().getSelectedItem() != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/cstewart/schedulingapplication/updateCustomer.fxml"));
                Parent updateParent = loader.load();
                Scene updateCustomerScene = new Scene(updateParent);
                UpdateCustomer_Controller UCController = loader.getController();
                UCController.sendCustomer(customerTable.getSelectionModel().getSelectedItem());
                Stage updateCustomerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                updateCustomerStage.setScene(updateCustomerScene);
                updateCustomerStage.show();
            } else {
                Alert updateCustomerError = new Alert(Alert.AlertType.ERROR);
                updateCustomerError.setTitle("Update Customer Error");
                updateCustomerError.setContentText("Please select a customer to update.");
                updateCustomerError.show();
            }
        }catch (SQLException sqlException){
            Logger.getLogger(Customer_Controller.class.getName()).log(Level.SEVERE, null, sqlException);
        }
    }
    /** A method that deletes a customer and their appointments from the database.
     * *
     * @param actionEvent deletes customer appointment first and then the selected customer from database. */
    @FXML
    public void onClickDeleteCustomer(ActionEvent actionEvent) throws IOException {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        int selectedCustomerID = selectedCustomer.getCustomer_ID();
        int referenceCustomerID = selectedCustomerID;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Customer Deletion");
        alert.setHeaderText("Are you sure you wish to delete the selected customer?");
        alert.setContentText("This Customer ID: " + referenceCustomerID +" and all related appointments will be deleted.");
        Optional<ButtonType> result1 = alert.showAndWait();

        try {
            if (result1.isPresent() && result1.get() == ButtonType.OK) {
                Appointment_DAO.deleteFromDBByAppointmentID(selectedCustomerID);
                Customer_DAO.deleteCustomerFromDB(selectedCustomerID);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Alert customerDeleted = new Alert(Alert.AlertType.INFORMATION);
            customerDeleted.setTitle("Customer Deleted");
            customerDeleted.setHeaderText("Deletion");
            customerDeleted.setContentText("The selected Customer ID: " + referenceCustomerID + " has been deleted");
            customerDeleted.showAndWait();

            Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
            Scene customerScene = new Scene(customerScreen);
            Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            customerStage.setScene(customerScene);
            customerStage.show();
        }
    }

    /** A method that helps load data from the SQL Database into the customer table.*/
    @FXML
    private void loadCustomerTable(){
        try{
            customerTable.setItems(DAO.Customer_DAO.getAllCustomersDivisionsCountries());
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
            countryIDCol.setCellValueFactory(new PropertyValueFactory<>("Country_ID"));
            countryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
            divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
            stateCol.setCellValueFactory(new PropertyValueFactory<>("Division"));
            customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
            customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            // Sort table by "Customer ID" column.
            customerTable.getSortOrder().add(customerIDCol);
            customerTable.sort();

        }catch (SQLException sqlException){
            Logger.getLogger(Customer_Controller.class.getName()).log(Level.SEVERE, null, sqlException);
        }
    }

    public void onClickAppointments(ActionEvent actionEvent) throws IOException {
        Parent appointmentScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
        Scene appointmentScene = new Scene(appointmentScreen);
        Stage appointmentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appointmentStage.setScene(appointmentScene);
        appointmentStage.show();
    }

    public void onClickReports(ActionEvent actionEvent) throws IOException {
        Parent reportOptionsScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportOptions.fxml"));
        Scene reportOptionsScene = new Scene(reportOptionsScreen);
        Stage reportOptionsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportOptionsStage.setScene(reportOptionsScene);
        reportOptionsStage.show();
    }

    /** A method that exits the program when the user clicks on the "Exit Screen" button.
     * *
     * @param actionEvent exits the program. */
    @FXML
    public void onClickExitScreenCustomer(ActionEvent actionEvent) throws IOException {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustomerTable();
    }

}