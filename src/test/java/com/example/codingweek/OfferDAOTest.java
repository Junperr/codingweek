package com.example.codingweek;

import com.example.codingweek.DAO.OfferDAO;
import com.example.codingweek.DAO.UserDAO;
import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OfferDAOTest {

    @Test
    public void addOfferTest(){
        User joelD = new UserDAO().getUserByUsername("joelD");
        CurrentUser.logUser(joelD);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Aspirateur");
        categories.add("Ménage");
        Offer offer = new Offer("Location d'aspirateur", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end", "joelD", null, 50, "Loan", true, categories);

        OfferDAO offerDAO = new OfferDAO();
        offerDAO.addOffer(offer);
        DataBase db = DataBase.getInstance();
        ArrayList<HashMap<String,Object>> fetch = db.fetchAllMap("select * from Offers where id =?", offer.getId());
        assertEquals(1, fetch.size());
        assertEquals("Location d'aspirateur", fetch.get(0).get("title"));
        assertEquals("Cherche à louer un aspirateur pour faire le grand ménage ce week-end", fetch.get(0).get("description"));
        assertEquals("joelD", fetch.get(0).get("user"));
        assertEquals(50, fetch.get(0).get("price"));
        assertEquals("Loan", fetch.get(0).get("type"));
        assertEquals("true", fetch.get(0).get("availability"));
        ArrayList<HashMap<String,Object>> fetchCategories = db.fetchAllMap("select * from Categories where offer =?", offer.getId());
        assertEquals(2,fetchCategories.size());
        assertEquals("Aspirateur", fetchCategories.get(0).get("category"));
        assertEquals("Ménage", fetchCategories.get(1).get("category"));
    }
    @Test
    public void newOfferTest(){
        User joelD = new UserDAO().getUserByUsername("joelD");
        CurrentUser.logUser(joelD);
        OfferDAO offerDAO = new OfferDAO();
        ArrayList<String> categories = new ArrayList<>();

        categories.add("Aspirateur");
        categories.add("balai");
        Offer offer = offerDAO.newOffer("Location de balai", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end",  null, 50,  "Loan", categories);

        assertEquals("Location de balai", offer.getTitle());
        assertEquals("Cherche à louer un aspirateur pour faire le grand ménage ce week-end", offer.getDescription());
        assertEquals("joelD", offer.getUser());
        assertEquals(50, offer.getPrice());
        assertEquals("Loan", offer.getType());
        assertEquals(true, offer.getAvailability());
        assertEquals(2, offer.getCategories().size());
        assertEquals("Aspirateur", offer.getCategories().get(0));
        assertEquals("balai", offer.getCategories().get(1));
    }


}
