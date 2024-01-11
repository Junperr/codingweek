package com.example.codingweek.DAO;

import com.example.codingweek.data.Message;
import com.example.codingweek.database.DataBase;

import java.sql.Timestamp;

public class MessageDAO {
    public Message newMessage(Timestamp timestamp, String content, String sender, String receiver) {
        Message message = new Message(timestamp, content, sender, receiver);
        addMessage(message);
        return message;
    }

    public void addMessage(Message message) {
        DataBase db = DataBase.getInstance();
        db.exec("insert into Messages (id, timestamp, content, sender, receiver, seen) values (?,?,?,?,?,?)",
                message.getId(),
                message.getTimestamp(),
                message.getContent(),
                message.getSender(),
                message.getReceiver(),
                message.getSeen());
    }
}
