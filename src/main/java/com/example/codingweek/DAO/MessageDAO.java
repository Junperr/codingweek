package com.example.codingweek.DAO;

import com.example.codingweek.data.Message;
import com.example.codingweek.database.DataBase;

import java.util.*;

public class MessageDAO {
    /**
     * create a new messae, add it to the database and create a message instance
     * @param content - the content of the message
     * @param sender - the sender of the message
     * @param receiver - the receiver of the message
     * @return - the instance of the offer
     */
    public Message newMessage(String content, String sender, String receiver) {
        Message message = new Message(content, sender, receiver);
        addMessage(message);
        return message;
    }

    /**
     * add a message to the database
     * @param message - the instance of message to add in the database
     */
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

    /**
     * used to get all the user who have a conv with current user
     * @param userName - the name of the user we want to get all the other user in contact with
     * @return - a set with all the name of the user in contact with userName
     */
    public Set<String> getAllUser(String userName) {
        DataBase db = DataBase.getInstance();
        ArrayList<HashMap<String, Object>> sender = db.fetchAllMap("select distinct sender from Messages where receiver=?", userName);
        ArrayList<HashMap<String, Object>> receiver = db.fetchAllMap("select distinct receiver from Messages where sender=?", userName);

        Set<String> chatters = new HashSet<String>();

        for (HashMap<String, Object> stringObjectHashMap : sender) {
            chatters.add((String) stringObjectHashMap.get("sender"));
        }
        for (HashMap<String, Object> stringObjectHashMap : receiver) {
            chatters.add((String) stringObjectHashMap.get("receiver"));
        }

        return chatters;
    }

    /**
     * Last message between userNameCurrent and userName
     * @param userNameCurrent - current logged in user
     * @param userName - another user
     * @return - a hash map containing the last message
     */
    public HashMap<String, Object> getLastMessageWith(String userNameCurrent, String userName) {
        DataBase db = DataBase.getInstance();
        HashMap<String, Object> lastTimestamp = db.fetchOneMap("select max(timestamp) as timestamp from Messages where (receiver=? and sender=?) or (receiver=? and sender=?)",userNameCurrent, userName, userName, userNameCurrent);

        return db.fetchOneMap("select * from Messages where timestamp=?", lastTimestamp.get("timestamp"));
    }

    /**
     * get all message between two user (used with the current user and another one ordered by oldest to youngest
     * @param userNameCurrent - current user or user 1
     * @param userName - the other one or user 2
     * @return - an Arraylist with all instance of message between the two user
     */
    public ArrayList<Message> getAllMessageWith(String userNameCurrent, String userName) {
        DataBase db = DataBase.getInstance();
        ArrayList<HashMap<String, Object>> request = db.fetchAllMap("select * from Messages where ((receiver=? and sender=?) or (receiver=? and sender=?)) order by timestamp",userNameCurrent, userName, userName, userNameCurrent);
        ArrayList<Message> messages = new ArrayList<>();

        for (HashMap<String, Object> stringObjectHashMap : request) {
            Message message = new Message(UUID.fromString(stringObjectHashMap.get("id").toString()),
                    (Long) stringObjectHashMap.get("timestamp"),
                    stringObjectHashMap.get("content").toString(),
                    stringObjectHashMap.get("sender").toString(),
                    stringObjectHashMap.get("receiver").toString(),
                    stringObjectHashMap.get("seen").toString());

            if (message.getSeen().equals("false")) {
                message.setSeen();
                db.exec("update Messages set seen=? where id=?", message.getSeen(), message.getId());
            }

            messages.add(message);
        }
        return messages;
    }

    /**
     * get the number of unread message for a user
     * @param userName - the user id
     * @return - an int the number of unread message
     */
    public Integer getUnreadNumber(String userName) {
        DataBase db = DataBase.getInstance();
        HashMap<String, Object> unread = db.fetchOneMap("select count(*) as unread from Messages where receiver=? and seen='false'", userName);

        return (Integer) unread.get("unread");
    }
}
