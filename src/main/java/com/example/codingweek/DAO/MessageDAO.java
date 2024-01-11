package com.example.codingweek.DAO;

import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.Message;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import com.example.codingweek.javafxSceneHandler.ChangeScene;

import java.sql.Timestamp;
import java.util.*;

public class MessageDAO {
    public Message newMessage(String content, String sender, String receiver) {
        Message message = new Message(content, sender, receiver);
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

    /**
     * get all the user who have a conv with current user
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

    public HashMap<String, Object> getLastMessageWith(String userNameCurrent, String userName) {
        DataBase db = DataBase.getInstance();
        HashMap<String, Object> lastTimestamp = db.fetchOneMap("select max(timestamp) from Messages where (receiver=? and sender=?) or (receiver=? and sender=?)",userNameCurrent, userName, userName, userNameCurrent);

        return db.fetchOneMap("select * from Messages where timestamp=?", lastTimestamp.get("timestamp"));
    }

    public ArrayList<HashMap<String, Object>> getAllMessageWith(String userNameCurrent, String userName) {
        DataBase db = DataBase.getInstance();
        ArrayList<HashMap<String, Object>> message = db.fetchAllMap("select * from Messages where (receiver=? and sender=?) or (receiver=? and sender=?) order by timestamp asc)",userNameCurrent, userName, userName, userNameCurrent);

        for (HashMap<String, Object> stringObjectHashMap : message) {
            if (stringObjectHashMap.get("seen").equals("false")) {
                stringObjectHashMap.put("seen", "true");
                db.exec("update Messages set seen=? where id=?", stringObjectHashMap.get("seen"), stringObjectHashMap.get("id"));
            }
        }

        return message;
    }

    public Integer getUnreadNumber(String userName) {
        DataBase db = DataBase.getInstance();
        HashMap<String, Object> unread = db.fetchOneMap("select count(*) as unread where receiver=?", userName);

        return (Integer) unread.get("unread");
    }
}
