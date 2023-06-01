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

public class ReportContactSchedule_Controller implements Initializable {
    @FXML private ComboBox<Contact> appointmentContactNameCB;
    @FXML private TableView appointmentTable;
    @FXML private TableColumn appointment_IDCol;
    @FXML private TableColumn titleCol;
    @FXML private TableColumn typeCol;
    @FXML private TableColumn descriptionCol;
    @FXML private TableColumn startCol;
    @FXML private TableColumn endCol;
    @FXML private TableColumn customer_IDCol;


    Contact selectedContact;

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
    public void onClickRefresh(ActionEvent actionEvent) throws IOException {
        Parent reportOptionsScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportContactSchedule.fxml"));
        Scene reportOptionsScene = new Scene(reportOptionsScreen);
        Stage reportOptionsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportOptionsStage.setScene(reportOptionsScene);
        reportOptionsStage.show();
    }
    @FXML
    public void onClickExitScreenReportContactSchedule(ActionEvent actionEvent) throws IOException {
        Parent reportOptionsScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/reportOptions.fxml"));
        Scene reportOptionsScene = new Scene(reportOptionsScreen);
        Stage reportOptionsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        reportOptionsStage.setScene(reportOptionsScene);
        reportOptionsStage.show();
    }

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
