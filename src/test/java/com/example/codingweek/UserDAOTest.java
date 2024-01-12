package com.example.codingweek;

import com.example.codingweek.DAO.UserDAO;
import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDAOTest {

    @Test
    public void newUserTest() throws Exception {
        UserDAO userDAO = new UserDAO();
        User user = new UserDAO().newUser("JoelTest", "DuhemTest", "joelDTest", "joel.duhemTest@telecomnancy.net", "Aa@45678Test", "57 boulevard saint vincent", "NancyTest", "59000");
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
        DataBase db = DataBase.getInstance();
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

        ArrayList<HashMap<String, Object>> fetch = db.fetchAllMap("select * from Users where userName =?", "joelDTest");
        assertEquals(0, fetch.size());

        UserDAO userDAO = new UserDAO();
        User user = new UserDAO().newUser("JoelTest", "DuhemTest", "joelDTest", "joel.duhemTest@telecomnancy.net", "Aa@45678Test", "57 boulevard saint vincent", "NancyTest", "59000");
        fetch = db.fetchAllMap("select * from Users where userName =?", "joelDTest");
        assertEquals(1, fetch.size());
    }

    @Test
    public void logUserTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void getUserByPasswordTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void getUserByUsernameTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void getAllUsersTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void updateFirstNameTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void updateLastNameTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void updateEmailTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void updatePasswordTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void updateCoinsTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void updateUsernameTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void checkedUpdatePosTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void updatePosTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void getUserAvgEvalTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    @Test
    public void updateAvgEvalTest() throws Exception {
        // TODO: Ajouter le contenu du test
    }

    private User getTestUser() {
        User joelD = new UserDAO().getUserByUsername("joelDTest");
        if (joelD == null) {
            try {
                System.out.println(joelD);
                joelD = new UserDAO().newUser("JoelTest", "DuhemTest", "joelDTest", "joel.duhemTest@telecomnancy.net", "Aa@45678Test", "57 boulevard saint vincent", "NancyTest", "59000");
                System.out.println(joelD);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        CurrentUser.logUser(joelD);
        return joelD;
    }
}
