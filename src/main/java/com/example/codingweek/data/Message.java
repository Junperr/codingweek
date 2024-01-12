package com.example.codingweek.data;

import java.sql.Timestamp;
import java.util.UUID;

public class Message {
    private final UUID id;
    private final Long timestamp;
    private final String content, sender, receiver;
    private String seen;

    public UUID getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen() {
        this.seen = "true";
    }

    public Message(String content, String sender, String receiver) {
        this.id = UUID.randomUUID();
        this.timestamp = System.currentTimeMillis();
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.seen = "false";
    }

    public Message(UUID id, Long timestamp, String content, String sender, String receiver, String seen) {
        this.id = id;
        this.timestamp = timestamp;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.seen = seen;
    }

    public void printMessage() {
        System.out.println(
                "id: " + id + '\n' +
                        "timestamp: " + timestamp + '\n' +
                        "content: " + content + '\n' +
                        "sender: " + sender + '\n' +
                        "receiver: " + receiver + '\n'
        );
    }
}
