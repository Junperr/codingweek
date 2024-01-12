package com.example.codingweek.controller;

import com.example.codingweek.data.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MessageViewController {
    @FXML
    private VBox oneMess;
    @FXML
    private Label content;

    public void initMessage(Message message) {

        content.setText( message.getSender() + " : " + message.getContent());
    }
}
