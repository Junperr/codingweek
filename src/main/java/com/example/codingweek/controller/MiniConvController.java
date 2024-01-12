package com.example.codingweek.controller;

import com.example.codingweek.data.Message;
import com.example.codingweek.facade.BigFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public class MiniConvController {
    @FXML
    private Label nameSender, lastMessage, time;
    private String userName, content;
    private Long timestamp;

    public void initMiniConv(Message message) {
        this.userName = message.getSender();
        this.content = message.getContent();
        this.timestamp = message.getTimestamp();
        nameSender.setText(message.getSender());
        lastMessage.setText(message.getContent());
        time.setText(message.getTimestamp().toString());
    }

    @FXML
    public void goToConv() {
        BigFacade bf = new BigFacade();
        ArrayList<Message> conv = bf.getConv(userName);


    }
}
