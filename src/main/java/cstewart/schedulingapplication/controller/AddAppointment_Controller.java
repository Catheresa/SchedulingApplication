package cstewart.schedulingapplication.controller;

import DAO.UserLogin_DAO;
import cstewart.schedulingapplication.model.*;
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

public class AddAppointment_Controller implements Initializable {

    //Text Fields, date picker, and dropdown selections to add an appointment
    @FXML private ComboBox<Contact> contactNameCB;
    @FXML private TextField txtTitle;
    @FXML private TextField txtDescription;
    @FXML private TextField txtLocation;
    @FXML private TextField txtType;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<LocalTime> startTimeCB;
    @FXML private ComboBox<LocalTime> endTimeCB;
    @FXML private ComboBox<Customer> customerIDCB;
    @FXML private ComboBox<User> userIDCB;

    @FXML
    public void onClickAddAppointment(ActionEvent actionEvent) throws IOException {
//        ZoneId myZoneId = ZoneId.systemDefault();
//        ZoneId utcZoneId = ZoneId.of("UTC");

        int tempContactID = contactNameCB.getValue().getContact_ID();
        String tempTitle = txtTitle.getText();
        String tempDescription = txtDescription.getText();
        String tempLocation = txtLocation.getText();
        String tempType = txtType.getText();
        LocalDate tempDate = datePicker.getValue();
        LocalTime tempStart = (LocalTime) startTimeCB.getValue();
        LocalTime tempEnd =  (LocalTime) endTimeCB.getValue();
        int tempCustomerID = customerIDCB.getValue().getCustomer_ID();
        int tempUserID = userIDCB.getValue().getUser_ID();

        //FIX ME!  NOT CONVERTING TO UTC IN DB
        LocalDateTime myLDTStart = LocalDateTime.of(tempDate, tempStart);
        LocalDateTime myLDTEnd = LocalDateTime.of(tempDate, tempEnd);

        try {
            DAO.Appointment_DAO.addAppointment(tempContactID, tempTitle, tempDescription,tempLocation,tempType,myLDTStart,myLDTEnd,tempCustomerID,tempUserID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
            Scene customerScene = new Scene(customerScreen);
            Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            customerStage.setScene(customerScene);
            customerStage.show();
        }
    }

    /** A method that clears out the combo box selections.
     * *
     * @param actionEvent to clear combo boxes. */
    @FXML
    public void onClickClearSelections(ActionEvent actionEvent) {
        contactNameCB.getSelectionModel().clearSelection();
        customerIDCB.getSelectionModel().clearSelection();
        userIDCB.getSelectionModel().clearSelection();
    }
    /** A method that allows user to exit screen and go back to the appointment screen.
     * *
     * @param actionEvent to exit screen and go back to the appointment screen. */
    @FXML
    public void onClickExitScreen(ActionEvent actionEvent) throws IOException {
        Parent appointmentScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
        Scene appointmentScene = new Scene(appointmentScreen);
        Stage appointmentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appointmentStage.setScene(appointmentScene);
        appointmentStage.show();
    }

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

            LocalTime tempStart = LocalTime.of(8,0);
            LocalTime tempEnd = LocalTime.of(20,0);

            // FIX ME! From midnight to midnight...DYNAMIC ERROR CHECKING REQUIRED BASED ON LOCAL TIME
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
