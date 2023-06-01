package cstewart.schedulingapplication.controller;

import DAO.Contact_DAO;
import DAO.Customer_DAO;
import DAO.UserLogin_DAO;
import cstewart.schedulingapplication.model.Appointment;
import cstewart.schedulingapplication.model.Contact;
import cstewart.schedulingapplication.model.Customer;
import cstewart.schedulingapplication.model.User;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class UpdateAppointment_Controller implements Initializable {


    @FXML private ComboBox<Contact> contactNameCB;
    @FXML private TextField txtAppointmentId;
    @FXML private TextField txtTitle;
    @FXML private TextField txtDescription;
    @FXML private TextField txtLocation;
    @FXML private TextField txtType;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<LocalTime> startTimeCB;
    @FXML private ComboBox<LocalTime> endTimeCB;
    @FXML private ComboBox<Customer> customerIDCB;
    @FXML private ComboBox<User> userIDCB;

    Appointment identifiedAppointment;
    Contact identifiedContact;

//    /** A method utilized to send appointment data to the "UpdateAppointment" screen.
//     * @param appointment send appointment to the update appointment screen. */
    @FXML
    public void sendAppointment(Appointment appointment) throws SQLException {
        identifiedAppointment = appointment;

        txtAppointmentId.setText(String.valueOf(appointment.getAppointment_ID()));
        txtTitle.setText(String.valueOf(appointment.getTitle()));
        txtDescription.setText(String.valueOf(appointment.getDescription()));
        txtLocation.setText(String.valueOf(appointment.getLocation()));
        txtType.setText(String.valueOf(appointment.getType()));
        datePicker.setValue(appointment.getStart().toLocalDate());
        startTimeCB.setValue(appointment.getStart().toLocalTime());
        endTimeCB.setValue(appointment.getEnd().toLocalTime());

        try {
            customerIDCB.setValue(Customer_DAO.lookupCustomer(appointment.getCustomer_ID()));
            // FIX ME!  Why isn't it loading the contact into the update appointment screen?
            contactNameCB.setValue(Contact_DAO.lookupContact(appointment.getContact_ID()));
            userIDCB.setValue(UserLogin_DAO.lookupUser(appointment.getUser_ID()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** A method utilized to exit the "UpdateAppointment" screen.
     * @param actionEvent to send user back to the appointment screen. */
    @FXML
    public void onClickExitScreen(ActionEvent actionEvent) throws IOException {
        Parent appointmentScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
        Scene appointmentScene = new Scene(appointmentScreen);
        Stage appointmentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appointmentStage.setScene(appointmentScene);
        appointmentStage.show();
    }

    public void onClickUpdateAppointment(ActionEvent actionEvent) throws IOException {
        int tempAppointmentID = Integer.parseInt(txtAppointmentId.getText());
        String tempTitle = txtTitle.getText();
        String tempDescription = txtDescription.getText();
        String tempLocation = txtLocation.getText();
        String tempType = txtType.getText();
        LocalDate tempDate = datePicker.getValue();
        LocalTime tempStart = (LocalTime) startTimeCB.getValue();
        LocalTime tempEnd =  (LocalTime) endTimeCB.getValue();
        Timestamp tempCreate_Date = new Timestamp(System.currentTimeMillis());
        String tempCreated_By = "script";
        Timestamp tempUpdate_Date = new Timestamp(System.currentTimeMillis());
        String tempUpdated_By = "script";

        int tempCustomerID = customerIDCB.getValue().getCustomer_ID();
        int tempUserID = userIDCB.getValue().getUser_ID();
        int tempContactID = contactNameCB.getValue().getContact_ID();
        LocalDateTime myLDTStart = LocalDateTime.of(tempDate, tempStart);
        LocalDateTime myLDTEnd = LocalDateTime.of(tempDate, tempEnd);

        DAO.Appointment_DAO.updateAppointment(tempAppointmentID,tempTitle,tempDescription,tempLocation,tempType,myLDTStart,myLDTEnd,tempCreate_Date,
                tempCreated_By,tempUpdate_Date,tempUpdated_By,tempCustomerID, tempUserID, tempContactID);

        Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
        Scene customerScene = new Scene(customerScreen);
        Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        customerStage.setScene(customerScene);
        customerStage.show();
    }

    public void onSelectContactName(ActionEvent actionEvent) {

    }

    public void onClickClearSelections(ActionEvent actionEvent) {
        contactNameCB.getSelectionModel().clearSelection();
        customerIDCB.getSelectionModel().clearSelection();
        userIDCB.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Contact> allContacts = DAO.Contact_DAO.getAllContacts();
            contactNameCB.setItems(allContacts);
            contactNameCB.setVisibleRowCount(5);
            contactNameCB.setPromptText("Select Contact ID");

            ObservableList<Customer> allCustomers = DAO.Customer_DAO.getAllCustomers();
            customerIDCB.setItems(allCustomers);
            customerIDCB.setVisibleRowCount(5);
            customerIDCB.setPromptText("Select Customer ID");

            ObservableList<User> allUsers = UserLogin_DAO.getAllUsers();
            userIDCB.setItems(allUsers);
            userIDCB.setVisibleRowCount(5);
            userIDCB.setPromptText("Select User ID");

            LocalTime tempStart = LocalTime.of(8,0);
            LocalTime tempEnd = LocalTime.of(20,0);

            for(int i=0; i <24; i++){
                startTimeCB.getItems().add(LocalTime.of(i,0));
                if(i < 23){
                    endTimeCB.getItems().add(LocalTime.of(i+1,0));
                }
            }
            endTimeCB.getItems().add(LocalTime.of(0,0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
