package cstewart.schedulingapplication.controller;

import DAO.Appointment_DAO;
import cstewart.schedulingapplication.model.Appointment;
import cstewart.schedulingapplication.model.Contact;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Appointment_Controller implements Initializable {


    // The radio buttons where user can toggle between a view of appointments by week or month:
    @FXML private ToggleGroup tgView;
    @FXML private RadioButton radioBtnAllView;
    @FXML private ToggleButton radioBtnWeekView;
    @FXML private ToggleButton radioBtnMonthView;

    //The appointment table and columns:
    @FXML private TableView<Appointment> appointmentTbl;
    @FXML private TableColumn<Appointment, Integer> appointmentIDCol;
    @FXML private TableColumn<Appointment, String> appointmentTitleCol;
    @FXML private TableColumn<Appointment, String> appointmentDescriptionCol;
    @FXML private TableColumn<Appointment, String> appointmentLocationCol;
    @FXML private TableColumn<Contact, String> appointmentContactCol;
    @FXML private TableColumn<Appointment, String> appointmentTypeCol;
    @FXML private TableColumn<Appointment, String> appointmentStartCol;
    @FXML private TableColumn<Appointment, String> appointmentEndCol;
    @FXML private TableColumn<Appointment, Integer> appointmentCustomerIDCol;
    @FXML private TableColumn<Appointment, Integer> appointmentUserIDCol;

    Appointment selectedAppointment;

    /** A method that loads the add appointment screen when the user clicks on"Add" button.
     * @param actionEvent go to add appointment screen. */
    @FXML
    void onClickAppointmentAdd(ActionEvent actionEvent) throws IOException {
        Parent addAppointmentScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/addAppointment.fxml"));
        Scene addAppointmentScene = new Scene(addAppointmentScreen);
        Stage addAppointmentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addAppointmentStage.setScene(addAppointmentScene);
        addAppointmentStage.show();
    }
    /** A method that allows a user to select an appointment to update by selecting an appointment and then pressing the update button.
     User will be taken to the update appointment screen. If no appointment has been selected, an error box will appear.
     @param actionEvent go to update appointment screen. */
    @FXML
    void onClickAppointmentUpdate(ActionEvent actionEvent) throws IOException {
        Appointment myAppt = appointmentTbl.getSelectionModel().getSelectedItem();

        if(myAppt != null){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/cstewart/schedulingapplication/updateAppointment.fxml"));
                Parent updateParent = loader.load();
                Scene updateAppointmentScene = new Scene(updateParent);
                UpdateAppointment_Controller ACController = loader.getController();
                ACController.sendAppointment(myAppt);
                Stage updateAppointmentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                updateAppointmentStage.setScene(updateAppointmentScene);
                updateAppointmentStage.show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            Alert loginError = new Alert(Alert.AlertType.ERROR);
            loginError.setTitle("Update Appointment Error");
            loginError.setContentText("Please select an appointment to update.");
            loginError.show();
        }
    }
    /** A method that allows a user to delete a selected appointment.
     @param actionEvent to delete a selected appointment. */
    @FXML
    void onClickAppointmentDelete(ActionEvent actionEvent) throws IOException, SQLException {
        selectedAppointment = appointmentTbl.getSelectionModel().getSelectedItem();
        int selectedAppointmentID = selectedAppointment.getAppointment_ID();
        int referenceAppointmentID = selectedAppointmentID;
        String referenceType = selectedAppointment.getType();

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Appointment Deletion");
            alert.setHeaderText("Are you sure you wish to delete the selected appointment?");
            alert.setContentText("This appointment will be deleted.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Appointment_DAO.deleteFromDBByAppointmentID(selectedAppointmentID);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Appointment Deletion");
                alert2.setHeaderText("Deleted");
                alert2.setContentText("The selected appointment ID: " + referenceAppointmentID + " " + referenceType + " has been deleted.");
                alert2.showAndWait();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally{
            Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
            Scene customerScene = new Scene(customerScreen);
            Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            customerStage.setScene(customerScene);
            customerStage.show();
        }
    }

    /** A method that allows the user to exit the application.
     * @param actionEvent to exit the application. */
    @FXML
    void onClickAppointmentExit(ActionEvent actionEvent) throws IOException {
        Platform.exit();
    }
    // load the Appointment Table
    @FXML
    private void loadAppointmentTable() {
        try {
            appointmentTbl.setItems(DAO.Appointment_DAO.getAppointmentsTable());
            appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
            appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("Contact_Name"));
            appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            appointmentCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            appointmentUserIDCol.setCellValueFactory(new PropertyValueFactory<>("User_ID"));

            appointmentTbl.getSortOrder().add(appointmentIDCol);
            appointmentTbl.sort();

        } catch (SQLException sqlException) {
            Logger.getLogger(Customer_Controller.class.getName()).log(Level.SEVERE, null, sqlException);
        }
    }

    public void onToggleRadioBtnAllView(ActionEvent actionEvent) throws IOException {
        try {
            Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/appointment.fxml"));
            Scene customerScene = new Scene(customerScreen);
            Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            customerStage.setScene(customerScene);
            customerStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onToggleRadioBtnWeekView(ActionEvent actionEvent) throws SQLException {
        try {
            if(radioBtnWeekView.isSelected()){
                appointmentTbl.setItems(Appointment_DAO.getView(7));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onToggleRadioBtnMonthView(ActionEvent actionEvent) {
        try {
            if(radioBtnMonthView.isSelected()){
                Appointment_DAO.getView(30);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** A method that loads the customer screen when the user clicks on"Customers" button.
     * @param actionEvent go to the Customer  screen. */
    @FXML
    public void onClickCustomers(ActionEvent actionEvent) throws IOException {
        Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
        Scene customerScene = new Scene(customerScreen);
        Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        customerStage.setScene(customerScene);
        customerStage.show();
    }
    /** A method that loads the report options screen when the user clicks on"Reports" button.
     * @param actionEvent go to the report options screen. */
    @FXML
    public void onClickReports(ActionEvent actionEvent) throws IOException {
        Parent reportOptionsScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportOptions.fxml"));
        Scene reportOptionsScene = new Scene(reportOptionsScreen);
        Stage reportOptionsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportOptionsStage.setScene(reportOptionsScene);
        reportOptionsStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAppointmentTable();

    }


}
