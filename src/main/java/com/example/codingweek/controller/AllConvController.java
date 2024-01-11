package com.example.codingweek.controller;

import com.example.codingweek.facade.BigFacade;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AllConvController implements Initializable {

    public void setStage(Stage stage) {
    }

    public void setMainController(LayoutController layoutController) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BigFacade bf = new BigFacade();

    }
}
