module com.example.codingweek {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.codingweek21 to javafx.fxml;
    exports com.example.codingweek21;
}