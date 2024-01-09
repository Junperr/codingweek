module com.example.codingweek {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.codingweek to javafx.fxml;
    exports com.example.codingweek;
    exports com.example.codingweek.controller;
    opens com.example.codingweek.controller to javafx.fxml;
    exports com.example.codingweek.javafxComponent;
    opens com.example.codingweek.javafxComponent to javafx.fxml;
}