module com.example.codingweek21 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.codingweek21 to javafx.fxml;
    exports com.example.codingweek21;
    exports com.example.codingweek21.controller;
    opens com.example.codingweek21.controller to javafx.fxml;
}