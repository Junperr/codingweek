package com.example.codingweek21;

import com.example.codingweek21.database.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("form-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
        DataBase dataBase = DataBase.getInstance();
        dataBase.printData(dataBase.fetchAll("SELECT * FROM table1"));
    }

    public static void main(String[] args) {
        launch();
    }
}