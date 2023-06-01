module com.example.schedulingapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens cstewart.schedulingapplication to javafx.fxml;
    exports cstewart.schedulingapplication;
    exports cstewart.schedulingapplication.controller;
    opens cstewart.schedulingapplication.controller to javafx.fxml;
    exports cstewart.schedulingapplication.model;
    opens cstewart.schedulingapplication.model to javafx.fxml;
}