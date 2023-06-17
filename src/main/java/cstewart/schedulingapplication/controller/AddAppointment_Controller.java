package cstewart.schedulingapplication.controller;

import DAO.Appointment_DAO;
import DAO.UserLogin_DAO;
import cstewart.schedulingapplication.model.*;
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

/** A class that allows a user to add an appointment to the database. */
public class AddAppointment_Controller implements Initializable {
    // Combo boxes for dropdown selections.
    @FXML private ComboBox<Contact> contactNameCB;
    @FXML private ComboBox<Customer> customerIDCB;
    @FXML private ComboBox<LocalTime> endTimeCB;
    @FXML private ComboBox<LocalTime> startTimeCB;
    @FXML private ComboBox<User> userIDCB;

    // Date picker to select appointment date.
    @FXML private DatePicker datePicker;

    // Text fields for adding appointment details.
    @FXML private TextField txtDescription;
    @FXML private TextField txtLocation;
    @FXML private TextField txtTitle;
    @FXML private TextField txtType;

    /** A method that adds an appointment to the database.
     @param actionEvent to add appointment.
     */
    @FXML
    public void onClickAddAppointment(ActionEvent actionEvent) throws IOException {
        try {
            int tempContactID = 0;
            String tempTitle = txtTitle.getText();
            String tempDescription = txtDescription.getText();
            String tempLocation = txtLocation.getText();
            String tempType = txtType.getText();
            LocalDate tempDate = datePicker.getValue();
            LocalTime tempStart = startTimeCB.getValue();
            LocalTime tempEnd = endTimeCB.getValue();
            int tempCustomerID = 0;
            int tempUserID = 0;

            if (contactNameCB.getValue() == null || customerIDCB.getValue() == null || endTimeCB.getValue() == null ||
                    startTimeCB.getValue() == null || userIDCB.getValue() == null || datePicker.getValue() == null ||
                    txtDescription.getText().isEmpty() || txtLocation.getText().isEmpty() ||
                    txtTitle.getText().isEmpty() || txtType.getText().isEmpty()) {

                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Retry");
                error.setContentText("Please ensure all selections have been made and fields have been populated.");
                error.showAndWait();

                return;

            } else {
                LocalDateTime myLDTStart = LocalDateTime.of(tempDate, tempStart);
                LocalDateTime myLDTEnd = LocalDateTime.of(tempDate, tempEnd);

                tempContactID = contactNameCB.getValue().getContact_ID();
                tempCustomerID = customerIDCB.getValue().getCustomer_ID();
                tempUserID = userIDCB.getValue().getUser_ID();

                if (!Appointment_DAO.isOverlappingAppointment(tempDate, tempStart, tempEnd, tempCustomerID, -1)) {
                    DAO.Appointment_DAO.addAppointment(tempContactID, tempTitle, tempDescription, tempLocation, tempType,
                            myLDTStart, myLDTEnd, tempCustomerID, tempUserID);

                    Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
                    Scene customerScene = new Scene(customerScreen);
                    Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    customerStage.setScene(customerScene);
                    customerStage.show();
                } else {
                    Alert overlapExists = new Alert(Alert.AlertType.ERROR);
                    overlapExists.setTitle("Appointment Overlap");
                    overlapExists.setHeaderText("Retry");
                    overlapExists.setContentText("An appointment already exists for this time slot.");
                    overlapExists.showAndWait();
                    return;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** A method that allows user to exit screen and go back to the appointment screen.
     @param actionEvent to exit screen and go back to the appointment screen.
     */
    @FXML
    public void onClickExitScreen(ActionEvent actionEvent) throws IOException {
        Parent appointmentScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
        Scene appointmentScene = new Scene(appointmentScreen);
        Stage appointmentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appointmentStage.setScene(appointmentScene);
        appointmentStage.show();
    }

    /** A method to override the superclass. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Contact> allContacts = DAO.Contact_DAO.getAllContacts();
            contactNameCB.setItems(allContacts);
            contactNameCB.setVisibleRowCount(5);

            ObservableList<Customer> allCustomers = DAO.Customer_DAO.getAllCustomers();
            customerIDCB.setItems(allCustomers);
            customerIDCB.setVisibleRowCount(5);

            ObservableList<User> allUsers = UserLogin_DAO.getAllUsers();
            userIDCB.setItems(allUsers);
            userIDCB.setVisibleRowCount(5);

            startTimeCB.setItems(TimeHelper.getStartTimeList());
            endTimeCB.setItems(TimeHelper.getEndTimeList());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
