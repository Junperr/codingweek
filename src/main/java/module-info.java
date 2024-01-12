module com.example.codingweek {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.io;

    opens com.example.codingweek.data to com.fasterxml.jackson.databind;
    opens com.example.codingweek to javafx.fxml;
    exports com.example.codingweek;
    exports com.example.codingweek.controller;
    opens com.example.codingweek.controller to javafx.fxml;
    exports com.example.codingweek.javafxComponent;
    opens com.example.codingweek.javafxComponent to javafx.fxml;
}