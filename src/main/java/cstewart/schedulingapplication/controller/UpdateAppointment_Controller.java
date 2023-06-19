package cstewart.schedulingapplication.controller;

import DAO.Appointment_DAO;
import DAO.UserLogin_DAO;
import cstewart.schedulingapplication.model.Appointment;
import cstewart.schedulingapplication.model.Contact;
import cstewart.schedulingapplication.model.Customer;
import cstewart.schedulingapplication.model.User;
import cstewart.schedulingapplication.utility.TimeHelper;
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
import java.time.*;
import java.util.ResourceBundle;

/** A class that allows a user to update an existing appointment. */
public class UpdateAppointment_Controller implements Initializable {
    // Combo boxes for dropdown selections.
    @FXML private ComboBox<Contact> contactNameCB;
    @FXML private ComboBox<Customer> customerIDCB;
    @FXML private ComboBox<LocalTime> endTimeCB;
    @FXML private ComboBox<LocalTime> startTimeCB;
    @FXML private ComboBox<User> userIDCB;

    // Text fields for updating appointment details.
    @FXML private TextField txtAppointmentId;
    @FXML private TextField txtDescription;
    @FXML private TextField txtLocation;
    @FXML private TextField txtTitle;
    @FXML private TextField txtType;

    // Date picker for user to select date.
    @FXML private DatePicker datePicker;

    Appointment identifiedAppointment;

    /** A method utilized to send selected appointment data to the "UpdateAppointment" screen.
     @param appointment send selected appointment to the update appointment screen.
     */
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

        for(Contact contact : contactNameCB.getItems()){
            if(appointment.getContact_ID() == contact.getContact_ID()){
                contactNameCB.setValue(contact);
            }
        }
        for(Customer customer : customerIDCB.getItems()){
            if(appointment.getCustomer_ID() == customer.getCustomer_ID()){
                customerIDCB.setValue(customer);
            }
        }
        for(User user : userIDCB.getItems()){
            if(appointment.getUser_ID() == user.getUser_ID()){
                userIDCB.setValue(user);
            }
        }
    }

    /** A method utilized to return to the appointment screen without making changes.
     @param actionEvent to return to the appointment screen.
     */
    @FXML
    public void onClickExitScreen(ActionEvent actionEvent) throws IOException {
        Parent appointmentScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
        Scene appointmentScene = new Scene(appointmentScreen);
        Stage appointmentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appointmentStage.setScene(appointmentScene);
        appointmentStage.show();
    }

    /** A method utilized to update an existing appointment in the database.
     @param actionEvent to update appointment in the database.
     */
    public void onClickUpdateAppointment(ActionEvent actionEvent) throws IOException {
        int tempAppointmentID = Integer.parseInt(txtAppointmentId.getText());
        String tempTitle = txtTitle.getText();
        String tempDescription = txtDescription.getText();
        String tempLocation = txtLocation.getText();
        String tempType = txtType.getText();
        LocalDate tempDate = datePicker.getValue();
        LocalTime tempStart = (LocalTime) startTimeCB.getValue();
        LocalTime tempEnd =  (LocalTime) endTimeCB.getValue();

        int tempCustomerID = customerIDCB.getValue().getCustomer_ID();
        int tempUserID = userIDCB.getValue().getUser_ID();
        int tempContactID = contactNameCB.getValue().getContact_ID();

        LocalDateTime myLDTStart = LocalDateTime.of(tempDate, tempStart);
        LocalDateTime myLDTEnd = LocalDateTime.of(tempDate, tempEnd);

        if(!Appointment_DAO.isOverlappingAppointment(tempDate, tempStart, tempEnd,tempCustomerID,tempAppointmentID)){
            DAO.Appointment_DAO.updateAppointment(tempAppointmentID,tempTitle,tempDescription,tempLocation,tempType,
                    myLDTStart,myLDTEnd,tempCustomerID, tempUserID, tempContactID);
        }else {
            Alert overlapExists = new Alert(Alert.AlertType.ERROR);
            overlapExists.setTitle("Appointment Overlap");
            overlapExists.setHeaderText("Retry");
            overlapExists.setContentText("An appointment already exists for this time slot.");
            overlapExists.showAndWait();

            return;
        }

        Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
        Scene customerScene = new Scene(customerScreen);
        Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        customerStage.setScene(customerScene);
        customerStage.show();
    }

    /** A method utilized to clear the selections made to the dropdowns in the update appointment screen.
     @param actionEvent to clear selections.
     */
    public void onClickClearSelections(ActionEvent actionEvent) {
        contactNameCB.getSelectionModel().clearSelection();
        customerIDCB.getSelectionModel().clearSelection();
        userIDCB.getSelectionModel().clearSelection();
    }

    /** A method to override the superclass. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            startTimeCB.setItems(TimeHelper.getStartTimeList());

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
