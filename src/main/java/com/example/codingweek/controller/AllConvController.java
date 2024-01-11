package com.example.codingweek.controller;

import com.example.codingweek.facade.BigFacade;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AllConvController implements Initializable {
    private LayoutController layoutController;
    private Stage stage;

    public void setStage(Stage stage) {this.stage = stage;}

    public void setMainController(LayoutController layoutController) {this.layoutController = layoutController;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BigFacade bf = new BigFacade();

    }
}
