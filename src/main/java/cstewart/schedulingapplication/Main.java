package cstewart.schedulingapplication;

import DAO.JDBC_DAO;
import cstewart.schedulingapplication.controller.Login_Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;
import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;

/** The "Main" class. */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //create a ZoneID object
        ZoneId zoneID = ZoneId.systemDefault();

        //create a Scene and set the stage
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Scheduling System");
        stage.setScene(scene);

        //pull in the setLabel method from the LoginController so the label can be set on that screen
        Login_Controller LIController = fxmlLoader.getController();
        LIController.setLabel(zoneID.toString());

        //show that view
        stage.show();
    }
    /** The "Main" method that connects the program to the database and launches the program. */
    public static void main(String[] args) {
        JDBC_DAO.getConnection();
        launch();
        JDBC_DAO.closeConnection();

    }
}