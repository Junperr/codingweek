package com.example.codingweek21;

import com.example.codingweek21.database.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(Main.class.getClassLoader().getResource("static/fxml/form-login.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource("static/fxml/form-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        String css = Objects.requireNonNull(Main.class.getClassLoader().getResource("static/css/style.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

        DataBase db = DataBase.getInstance();
        System.out.println(db.fetchAll("select * Users"));
    }

    public static void main(String[] args) {
        launch();
    }
}