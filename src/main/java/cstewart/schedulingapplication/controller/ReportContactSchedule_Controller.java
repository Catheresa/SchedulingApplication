package cstewart.schedulingapplication.controller;

import cstewart.schedulingapplication.model.Appointment;
import cstewart.schedulingapplication.model.Contact;
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

/** A class that allows a user to view a report of a selected contacts schedule. */
public class ReportContactSchedule_Controller implements Initializable {
    // Combo boxes for dropdown selections.
    @FXML private ComboBox<Contact> appointmentContactNameCB;

    //The appointment table and columns:
    @FXML private TableView appointmentTable;
    @FXML private TableColumn appointment_IDCol;
    @FXML private TableColumn customer_IDCol;
    @FXML private TableColumn descriptionCol;
    @FXML private TableColumn endCol;
    @FXML private TableColumn startCol;
    @FXML private TableColumn titleCol;
    @FXML private TableColumn typeCol;

    Contact selectedContact;

    /** A method that filters a list of appointments by the selected contact.
     @param actionEvent filters appointment list by contact.
     */
    @FXML
    public void onSelectContactNameReportContactSchedule(ActionEvent actionEvent) {
        try {
            selectedContact = appointmentContactNameCB.getSelectionModel().getSelectedItem();
            int id = selectedContact.getContact_ID();
            ObservableList<Appointment> searchContact = DAO.Appointment_DAO.lookupAppointment(id);
            appointmentTable.setItems(searchContact);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** A method that helps load appointment data from the SQL database. */
    @FXML
    private void loadAppointmentTable() {
        try {
            appointmentTable.setItems(DAO.Appointment_DAO.getAllAppointments());

            appointment_IDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            customer_IDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));

        } catch (SQLException sqlException) {
            Logger.getLogger(Customer_Controller.class.getName()).log(Level.SEVERE, null, sqlException);
        }
    }

    /** A method that refreshes the list to show all appointments.
     @param actionEvent shows all appointments.
     */
    public void onClickRefresh(ActionEvent actionEvent) throws IOException {
        Parent reportOptionsScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportContactSchedule.fxml"));
        Scene reportOptionsScene = new Scene(reportOptionsScreen);
        Stage reportOptionsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportOptionsStage.setScene(reportOptionsScene);
        reportOptionsStage.show();
    }

    /** A method exits the report and returns to the report options screen.
     @param actionEvent navigates to the report options screen.
     */
    @FXML
    public void onClickExitScreenReportContactSchedule(ActionEvent actionEvent) throws IOException {
        Parent reportOptionsScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportOptions.fxml"));
        Scene reportOptionsScene = new Scene(reportOptionsScreen);
        Stage reportOptionsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportOptionsStage.setScene(reportOptionsScene);
        reportOptionsStage.show();
    }

    /** A method to override the superclass. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAppointmentTable();
        try {
            ObservableList<Contact> allContacts = DAO.Contact_DAO.getAllContacts();
            appointmentContactNameCB.setItems(allContacts);
            appointmentContactNameCB.setVisibleRowCount(5);
            appointmentContactNameCB.setPromptText("Select Contact ID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
