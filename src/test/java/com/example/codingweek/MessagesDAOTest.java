package com.example.codingweek;

import com.example.codingweek.DAO.MessageDAO;
import com.example.codingweek.data.Message;
import com.example.codingweek.database.DataBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessagesDAOTest {
    private DataBase db = DataBase.getInstance();

    @Test
    public void newMessageTest() {
        db.reset();
        MessageDAO messageDAO = new MessageDAO();

        Message testMessage = messageDAO.newMessage("Bonne année", "Ugo", "Julie");

        assertEquals(testMessage.getId().getClass(), UUID.class);
        assertEquals(testMessage.getTimestamp().getClass(), Long.class);
        assertEquals("Bonjour à tous", testMessage.getContent());
        assertEquals("Ugo", testMessage.getSender());
        assertEquals("Julie", testMessage.getReceiver());
        assertEquals("false", testMessage.getSeen());
    }

    @Test
    public void addMessageTest() {
        db.reset();
        MessageDAO messageDAO = new MessageDAO();

        Message testMessage = new Message(UUID.fromString("f3925355-d605-4f40-b012-829adb56738c"), System.currentTimeMillis(), "Bonne année", "Ugo", "Julie", "false");

        messageDAO.addMessage(testMessage);

        ArrayList<HashMap<String, Object>> fetch = db.fetchAllMap("select * from Messages");

        assertEquals(1, fetch.size());
        assertEquals("f3925355-d605-4f40-b012-829adb56738c", fetch.get(0).get("id"));
        // cannot test value because of "Integer to long" error
        assertEquals(Long.class, fetch.get(0).get("timestamp").getClass());
        assertEquals("Bonne année", fetch.get(0).get("content"));
        assertEquals("Ugo", fetch.get(0).get("sender"));
        assertEquals("Julie", fetch.get(0).get("receiver"));
        assertEquals("false", fetch.get(0).get("seen"));
    }

    @Test
    public void getAllUserTest() {
        db.reset();
        MessageDAO messageDAO = new MessageDAO();

        Message testMessage1 = new Message(UUID.fromString("f3925355-d605-4f40-b012-829adb56738c"), System.currentTimeMillis(), "Bonne année", "Ugo", "Julie", "false");
        Message testMessage2 = new Message(UUID.fromString("95b134bc-85b5-4e94-8bec-a0b7a5e8cfc2"), System.currentTimeMillis(), "Bonne année", "Ugo", "Aristide", "false");
        messageDAO.addMessage(testMessage1);
        messageDAO.addMessage(testMessage2);

        Set<String> testAnswer = messageDAO.getAllUser("Ugo");

        assertEquals(2, testAnswer.size());
        assertTrue(testAnswer.contains("Julie"));
        assertTrue(testAnswer.contains("Aristide"));
    }

    @Test
    public void getLastMessageWithTest() throws InterruptedException {
        db.reset();
        MessageDAO messageDAO = new MessageDAO();

        Long currentTime = System.currentTimeMillis();
        Message testMessage1 = new Message(UUID.fromString("f3925355-d605-4f40-b012-829adb56738c"), currentTime, "Bonne année", "Ugo", "Julie", "false");
        TimeUnit.SECONDS.sleep(1);
        Message testMessage2 = new Message(UUID.fromString("95b134bc-85b5-4e94-8bec-a0b7a5e8cfc2"), System.currentTimeMillis(), "Bonne santé", "Ugo", "Julie", "false");
        messageDAO.addMessage(testMessage1);
        messageDAO.addMessage(testMessage2);

        HashMap<String, Object> testAnswer = messageDAO.getLastMessageWith("Ugo","Julie");

        assertEquals(6, testAnswer.size());
        assertEquals("95b134bc-85b5-4e94-8bec-a0b7a5e8cfc2", testAnswer.get("id").toString());
        assertEquals("Bonne santé", testAnswer.get("content").toString());
        assertEquals("Ugo", testAnswer.get("sender").toString());
        assertEquals("Julie", testAnswer.get("receiver").toString());
        assertEquals("false", testAnswer.get("seen").toString());
        assertTrue(((Long) testAnswer.get("timestamp") > currentTime));
    }

    @Test
    public void getAllMessageWithTest() {
        db.reset();
        MessageDAO messageDAO = new MessageDAO();

        Message testMessage1 = new Message(UUID.fromString("f3925355-d605-4f40-b012-829adb56738c"), System.currentTimeMillis(), "Bonne année", "Ugo", "Julie", "false");
        messageDAO.addMessage(testMessage1);
        Message testMessage2 = new Message(UUID.fromString("95b134bc-85b5-4e94-8bec-a0b7a5e8cfc2"), System.currentTimeMillis(), "Bonne année", "Julie", "Ugo", "false");
        messageDAO.addMessage(testMessage2);

        ArrayList<Message> testAnswer = messageDAO.getAllMessageWith("Ugo", "Julie");

        assertEquals(2, testAnswer.size());
        assertEquals(UUID.fromString("f3925355-d605-4f40-b012-829adb56738c"), testAnswer.get(0).getId());
        assertEquals(UUID.fromString("95b134bc-85b5-4e94-8bec-a0b7a5e8cfc2"), testAnswer.get(1).getId());
    }

    @Test
    public void getUnreadNumberTest() {
        db.reset();
        MessageDAO messageDAO = new MessageDAO();

        Message testMessage1 = new Message(UUID.fromString("f3925355-d605-4f40-b012-829adb56738c"), System.currentTimeMillis(), "Bonne année", "Aristide", "Ugo", "true");
        Message testMessage2 = new Message(UUID.fromString("95b134bc-85b5-4e94-8bec-a0b7a5e8cfc2"), System.currentTimeMillis(), "Bonne année", "Julie", "Ugo", "false");
        Message testMessage3 = new Message(UUID.fromString("de6ea568-954e-43d7-9adc-33a44f6bdccc"), System.currentTimeMillis(), "Bonne année", "Ugo", "Julie", "true");
        messageDAO.addMessage(testMessage1);
        messageDAO.addMessage(testMessage2);
        messageDAO.addMessage(testMessage3);

        Integer testAnswer = messageDAO.getUnreadNumber("Ugo");

        assertEquals(1, testAnswer);
    }
}
