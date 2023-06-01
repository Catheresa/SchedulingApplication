package cstewart.schedulingapplication.controller;

import DAO.Appointment_DAO;
import DAO.UserLogin_DAO;
import cstewart.schedulingapplication.FileIOMain;
import cstewart.schedulingapplication.model.Appointment;
import cstewart.schedulingapplication.model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login_Controller implements Initializable {
    @FXML private Label loginLBL;
    @FXML private Label userNameLBL;
    @FXML private TextField usernameLogin;
    @FXML private Label passwordLBL;
    @FXML private TextField passwordLogin;
    @FXML private Button submitLogin;
    @FXML private Label zoneIDLogin;

    ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());

    public void setLabel(String label){
        zoneIDLogin.setText(label);
    }

    @FXML
    public void onClickSubmitLogin(ActionEvent actionEvent) throws  SQLException {
        String pass = passwordLogin.getText();
        String userName = usernameLogin.getText();
        User selectedUser = UserLogin_DAO.loginValid(userName,pass);
        int selectedUserID = selectedUser.getUser_ID();
        ObservableList<Appointment> searchForAppointments = Appointment_DAO.appointmentWithinMinutesOfLogin(selectedUserID);
        try {
            if (selectedUser != null) {
                if(searchForAppointments.size() > 0){
                    for(int i = 0; i < searchForAppointments.size(); i++){
                        Alert appointmentInMinutes = new Alert(Alert.AlertType.INFORMATION);
                        appointmentInMinutes.setTitle("Appointment Information!");
                        appointmentInMinutes.setHeaderText("Appointment in minutes!");
                        appointmentInMinutes.setContentText("A scheduled appointment ID: " +
                                searchForAppointments.get(i).getAppointment_ID() + "is coming up on " +
                                searchForAppointments.get(i).getStart() + ".");
                        appointmentInMinutes.showAndWait();
                    }

                }

                Parent customerScreen = FXMLLoader.load(getClass().getResource("/cstewart/schedulingapplication/customer.fxml"));
                Scene customerScene = new Scene(customerScreen);
                Stage customerStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                customerStage.setScene(customerScene);
                customerStage.show();
                FileIOMain.writeToFileLog(userName,true);

            }else {
                if (Locale.getDefault().getLanguage().equals("fr")) {
                    Alert loginError = new Alert(Alert.AlertType.ERROR);
                    loginError.setTitle(rb.getString("L_Error"));
                    loginError.setHeaderText(rb.getString("Error"));
                    loginError.setContentText(rb.getString("Sentence"));
                    loginError.show();
                    FileIOMain.writeToFileLog(userName,false);
                } else {
                    Alert loginError = new Alert(Alert.AlertType.ERROR);
                    loginError.setTitle("Login Error");
                    loginError.setHeaderText("Invalid Login ");
                    loginError.setContentText("Try entering username and password again");
                    loginError.show();
                    FileIOMain.writeToFileLog(userName,false);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if(Locale.getDefault().getLanguage().equals("fr")){
                loginLBL.setText(rb.getString("Login"));
                userNameLBL.setText(rb.getString("Username"));
                passwordLBL.setText(rb.getString("Password"));
                submitLogin.setText(rb.getString("Submit"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
