module com.example.codingweek21 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.codingweek21 to javafx.fxml;
    exports com.example.codingweek21;
}