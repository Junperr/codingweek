package com.example.codingweek.controller;

import com.example.codingweek.data.Message;
import com.example.codingweek.facade.BigFacade;
import com.example.codingweek.javafxSceneHandler.ChangeScene;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public class MiniConvController {
    @FXML
    private Label otherName, lastMessage, time;
    private String userName, content;
    private Long timestamp;

    private final ChangeScene changeScene = new ChangeScene();

    public void initMiniConv(Message message) {
        this.userName = message.getSender();
        this.content = message.getContent();
        this.timestamp = message.getTimestamp();
        otherName.setText(message.getSender());
        lastMessage.setText(message.getContent());
        time.setText(message.getTimestamp().toString());
    }

    @FXML
    public void goToConv() throws IOException {
        changeScene.changeSceneToConv(this, this.userName);
    }
}
