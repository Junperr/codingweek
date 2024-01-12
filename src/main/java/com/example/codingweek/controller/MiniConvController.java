package com.example.codingweek.controller;

import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.Message;
import com.example.codingweek.data.User;
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
        User currentUser = CurrentUser.getUser();

        if (currentUser.userName.equals(message.getReceiver())) {
            this.userName = message.getSender();
            otherName.setText(message.getSender());
        } else {
            this.userName = message.getReceiver();
            otherName.setText(message.getReceiver());
        }

        this.content = message.getContent();
        this.timestamp = message.getTimestamp();
        lastMessage.setText(message.getContent());

        Timestamp hours = new Timestamp(message.getTimestamp());
        String s = new SimpleDateFormat("dd/MM/yyyy").format(hours);
        time.setText(s);
    }

    @FXML
    public void goToConv() throws IOException {
        changeScene.changeSceneToConv(this, this.userName);
    }
}
