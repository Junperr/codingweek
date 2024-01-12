package com.example.codingweek;

import com.example.codingweek.DAO.EvalDAO;
import com.example.codingweek.DAO.OfferDAO;
import com.example.codingweek.DAO.OrderDAO;
import com.example.codingweek.DAO.UserDAO;
import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.Order;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDAOTest {

    @Test
    public void newUserTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();

        UserDAO userDAO = new UserDAO();
        User user = userDAO.newUser("JoelTest", "DuhemTest", "joelDTest", "joel.duhemTest@telecomnancy.net", "Aa@45678Test", "57 boulevard saint vincent", "NancyTest", "59000");
        assertEquals("JoelTest", user.firstName);
        assertEquals("DuhemTest", user.lastName);
        assertEquals("joelDTest", user.userName);
        assertEquals("joel.duhemTest@telecomnancy.net", user.email);
        assertEquals("Aa@45678Test", user.password);
        assertEquals("57 boulevard saint vincent", user.address);
        assertEquals("NancyTest", user.city);
        assertEquals("59000", user.zipCode);
        assertEquals(100, user.coins);
        assertEquals(-1, user.avgEval);


        // Check if the user is in the database (addUser)

        ArrayList<HashMap<String, Object>> fetch = db.fetchAllMap("select * from Users where userName =?", "joelDTest");
        assertEquals(1, fetch.size());
        assertEquals("JoelTest", fetch.get(0).get("firstName"));
        assertEquals("DuhemTest", fetch.get(0).get("lastName"));
        assertEquals("joelDTest", fetch.get(0).get("userName"));
        assertEquals("joel.duhemTest@telecomnancy.net", fetch.get(0).get("email"));
        assertEquals("Aa@45678Test", fetch.get(0).get("password"));
        assertEquals("57 boulevard saint vincent", fetch.get(0).get("address"));
        assertEquals("NancyTest", fetch.get(0).get("city"));
        assertEquals("59000", fetch.get(0).get("zipCode"));
        assertEquals("100", fetch.get(0).get("coins"));
        assertEquals("-1", fetch.get(0).get("averageEval"));
    }

    @Test
    public void addUserTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();
        ArrayList<HashMap<String, Object>> fetch = db.fetchAllMap("select * from Users where userName =?", "joelDTest");
        assertEquals(0, fetch.size());

        UserDAO userDAO = new UserDAO();
        User user = new UserDAO().newUser("JoelTest", "DuhemTest", "joelDTest", "joel.duhemTest@telecomnancy.net", "Aa@45678Test", "57 boulevard saint vincent", "NancyTest", "59000");
        fetch = db.fetchAllMap("select * from Users where userName =?", "joelDTest");
        assertEquals(1, fetch.size());
    }

    @Test
    public void logUserTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();
    }

    @Test
    public void getUserByPasswordTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();

        User userTest = getTestUser();
        UserDAO userDAO = new UserDAO();
        assertEquals(userTest.userName, userDAO.getUserByPassword("joelDTest", "Aa@45678Test").userName);
        assertEquals(userTest.address, userDAO.getUserByPassword("joelDTest", "Aa@45678Test").address);
        assertEquals(userTest.avgEval, userDAO.getUserByPassword("joelDTest", "Aa@45678Test").avgEval);
        assertEquals(userTest.city, userDAO.getUserByPassword("joelDTest", "Aa@45678Test").city);
        assertEquals(userTest.coins, userDAO.getUserByPassword("joelDTest", "Aa@45678Test").coins);
        assertEquals(userTest.email, userDAO.getUserByPassword("joelDTest", "Aa@45678Test").email);
    }

    @Test
    public void getUserByUsernameTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();

        User userTest = getTestUser();
        UserDAO userDAO = new UserDAO();
        assertEquals(userTest.userName, userDAO.getUserByUsername("joelDTest").userName);
        assertEquals(userTest.address, userDAO.getUserByUsername("joelDTest").address);
        assertEquals(userTest.avgEval, userDAO.getUserByUsername("joelDTest").avgEval);
        assertEquals(userTest.city, userDAO.getUserByUsername("joelDTest").city);
        assertEquals(userTest.coins, userDAO.getUserByUsername("joelDTest").coins);
        assertEquals(userTest.email, userDAO.getUserByUsername("joelDTest").email);
    }

    @Test
    public void updateFirstNameTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();

        User userTest = getTestUser();
        UserDAO userDAO = new UserDAO();
        userDAO.updateFirstName(userTest, "joel");
        assertEquals("joel", userTest.firstName);
    }

    @Test
    public void updateLastNameTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();

        User userTest = getTestUser();
        UserDAO userDAO = new UserDAO();
        userDAO.updateLastName(userTest, "duhem");
        assertEquals("duhem", userTest.lastName);
    }

    @Test
    public void updateEmailTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();

        User userTest = getTestUser();
        UserDAO userDAO = new UserDAO();
        userDAO.updateEmail(userTest, "joel.duhem@telecomnancy.eu");
        assertEquals("joel.duhem@telecomnancy.eu", userTest.email);
    }

    @Test
    public void updatePasswordTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();

        User userTest = getTestUser();
        UserDAO userDAO = new UserDAO();
        userDAO.updatePassword(userTest, "Aa@45678");
        assertEquals("Aa@45678", userTest.password);
    }

    @Test
    public void updateCoinsTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();

        User userTest = getTestUser();
        UserDAO userDAO = new UserDAO();
        userDAO.updateCoins(userTest, 50);
        assertEquals(50, userTest.coins);
    }

    @Test
    public void updateUsernameTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();

        User userTest = getTestUser();
        UserDAO userDAO = new UserDAO();
        userDAO.updateUsername(userTest, "joelD");
        assertEquals("joelD", userTest.userName);
    }


    @Test
    public void updatePosTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();
        User userTest = getTestUser();
        UserDAO userDAO = new UserDAO();

        userDAO.updatePos(userTest.userName, "57 avenue saint vincent", "Nancy", "54000");
        assertEquals("57 avenue saint vincent", userTest.address);
        assertEquals("Nancy", userTest.city);
        assertEquals("54000", userTest.zipCode);

    }

    @Test
    public void getUserAvgEvalTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();
        User userTest = getTestUser();
        UserDAO userDAO = new UserDAO();
        assertEquals(-1, userDAO.getUserAvgEval(userTest));

    }

    @Test
    public void updateAvgEvalTest() throws Exception {
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();
        User userTest = getTestUser();
        UserDAO userDAO = new UserDAO();
        assertEquals(-1, userTest.avgEval);
        userDAO.updateAvgEval(userTest);

        UUID idReview = UUID.randomUUID();
        EvalDAO evalDAO = new EvalDAO();
        OrderDAO orderDAO = new OrderDAO();
        OfferDAO offerDAO = new OfferDAO();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("test");
        Offer offer = offerDAO.newOffer("test", "test", null, 50, "Loan", categories);
        Order order = orderDAO.newOrder(offer.getId(), 50, "julieZ", "joelD");
        evalDAO.createNewEval(idReview,5, order.getId(), "julieZ" , "test");
        userDAO.updateAvgEval(userTest);
        assertEquals(5.0, userTest.avgEval);

    }

    private User getTestUser() throws Exception {
        UserDAO userDAO = new UserDAO();
        User joelD = userDAO.newUser("JoelTest", "DuhemTest", "joelDTest", "joel.duhemTest@telecomnancy.net", "Aa@45678Test", "57 boulevard saint vincent", "NancyTest", "59000");
        CurrentUser.logUser(joelD);
        return joelD;
    }
}
