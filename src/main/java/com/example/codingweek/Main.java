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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource("static/fxml/form-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        String css = Objects.requireNonNull(Main.class.getClassLoader().getResource("static/css/login.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
        DataBase dataBase = DataBase.getInstance();
        }

    public static void main(String[] args) {
//        ImageFile imagetest = new ImageFile("/home/junper/Info/PCD/codingweek-21/src/main/resources/static/images/offers/pelle.jpg", "offers/");
//        System.out.println(ImageFile.getImageName(imagetest));
//        System.out.println(imagetest.getTotalSpace());
//        System.out.println(imagetest.getFileExtension());
//        User admin = new UserDAO().getUserByUsername("admin");
//        CurrentUser.logUser(admin);
//        BigFacade bf = new BigFacade();
//        try {
//            bf.createNewOffer("test", "test", imagetest, 5, "Offer", new ArrayList<String>());
//        } catch (Exception e) {
//            System.out.println("Offer was not created");
//            e.printStackTrace();
//        }

                launch();
    }
}