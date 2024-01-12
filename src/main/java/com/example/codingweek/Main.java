package com.example.codingweek;

import com.example.codingweek.DAO.UserDAO;
import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.ImageFile;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import com.example.codingweek.facade.BigFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(Main.class.getClassLoader().getResource("static/fxml/form-login.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource("static/fxml/form-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        String css = Objects.requireNonNull(Main.class.getClassLoader().getResource("static/css/login.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
        DataBase dataBase = DataBase.getInstance();
        }

    public static void main(String[] args) {


                launch();
    }
}