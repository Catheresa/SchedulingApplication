package cstewart.schedulingapplication.controller;

import DAO.Customer_DAO;
import DAO.Geographical_DAO;
import cstewart.schedulingapplication.model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/** A class that allows a user to view a report of customers by a selected division. */
public class ReportCustomersByDivision_Controller implements Initializable {
    // Combo boxes for dropdown selections.
    @FXML private ComboBox<String> divisionCB;

    //The customer table and columns:
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customer_IDCol;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableColumn<Customer, String> addressCol;
    @FXML private TableColumn<Customer, String> postalCodeCol;
    @FXML private TableColumn<Customer, String> phoneCol;

    /** A method that filters a list of customers by the selected state/division.
     @param actionEvent filters customer list by state/division.
     */
    @FXML
    public void onSelectDivision(ActionEvent actionEvent) {
        try {
            String selectedDivision = divisionCB.getSelectionModel().getSelectedItem();
            ObservableList<Customer> searchCustomers = Customer_DAO.getCustomerByDivision(selectedDivision);
            customerTable.setItems(searchCustomers);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** A method that reloads a list of all customers.
     @param actionEvent reloads customer list.
     */
    @FXML
    public void onClickRefresh(ActionEvent actionEvent) throws IOException {
        Parent reportLocationScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportCustomersByDivision.fxml"));
        Scene reportLocationScene = new Scene(reportLocationScreen);
        Stage reportLocationStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportLocationStage.setScene(reportLocationScene);
        reportLocationStage.show();
    }

    /** A method that exits the screen.
     @param actionEvent reloads the report options screen.
     */
    @FXML
    public void onClickExitScreen(ActionEvent actionEvent) throws IOException {
        Parent reportOptionsScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportOptions.fxml"));
        Scene reportOptionsScene = new Scene(reportOptionsScreen);
        Stage reportOptionsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportOptionsStage.setScene(reportOptionsScene);
        reportOptionsStage.show();
    }

    /** A method that helps load appointment data from the SQL database. */
    @FXML
    private void loadAppointmentTable() {
        try {
            customerTable.setItems(DAO.Customer_DAO.getAllCustomers());
            customer_IDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
            postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        } catch (SQLException sqlException) {
            Logger.getLogger(Customer_Controller.class.getName()).log(Level.SEVERE, null, sqlException);
        }
    }

    /** A method to override the superclass. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAppointmentTable();
        try {
            ObservableList<String> allDivisions = Geographical_DAO.getDivisions();
            divisionCB.setItems(allDivisions);
            divisionCB.setVisibleRowCount(5);
            divisionCB.setPromptText("Select Division");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
