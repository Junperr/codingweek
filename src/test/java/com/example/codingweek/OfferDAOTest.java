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
    public void addOfferTest() throws Exception {
        User joelD = new UserDAO().getUserByUsername("joelD");
        CurrentUser.logUser(joelD);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Aspirateur");
        categories.add("Ménage");
        Offer offer = new Offer("Location d'aspirateur", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end", "joelD", null, 50, "Loan", true, categories);

        OfferDAO offerDAO = new OfferDAO();
        offerDAO.addOffer(offer, null);
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
    public void newOfferTest() throws Exception {
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

    @Test
    public void updateAvailabilityTest() throws Exception {
        Offer offer = createTestOffer();
        OfferDAO offerDAO = new OfferDAO();
        assertEquals(true, offer.getAvailability());
        offerDAO.updateAvailability(offer);
        assertEquals(false, offer.getAvailability());
    }

    @Test
    public void getOfferByIdTest() throws Exception {
        Offer offer = createTestOffer();
        OfferDAO offerDAO = new OfferDAO();
        Offer fetchedOffer = offerDAO.getOfferById(offer.getId());

        assertEquals(offer.getTitle(), fetchedOffer.getTitle());
        assertEquals(offer.getDescription(), fetchedOffer.getDescription());
        assertEquals(offer.getUser(), fetchedOffer.getUser());
        assertEquals(offer.getPrice(), fetchedOffer.getPrice());
        assertEquals(offer.getType(), fetchedOffer.getType());
        assertEquals(offer.getAvailability(), fetchedOffer.getAvailability());
        assertEquals(offer.getCategories(), fetchedOffer.getCategories());
    }

    @Test
    public void getOwnOffersTest() throws Exception {
        User joelD = createTestUser();
        Offer offer = createTestOffer();
        OfferDAO offerDAO = new OfferDAO();

        ArrayList<Offer> ownOffers = offerDAO.getOwnOffers(joelD.userName);

        assertEquals(false, ownOffers.isEmpty());
        assertEquals(offer.getTitle(), ownOffers.get(0).getTitle());
        assertEquals(offer.getDescription(), ownOffers.get(0).getDescription());
        assertEquals(offer.getUser(), ownOffers.get(0).getUser());
        assertEquals(offer.getPrice(), ownOffers.get(0).getPrice());
        assertEquals(offer.getType(), ownOffers.get(0).getType());
        assertEquals(offer.getAvailability(), ownOffers.get(0).getAvailability());
    }

    @Test
    public void getOthersOfferTest() {

    }

    @Test
    public void getAllOffersTest() {

    }
    private Offer createTestOffer() throws Exception {
        OfferDAO offerDAO = new OfferDAO();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Aspirateur");
        categories.add("balai");
        Offer offer = offerDAO.newOffer("Location de balai", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end",  null, 50,  "Loan", categories);
        return offer;
    }
    private User createTestUser(){
        User joelD = new UserDAO().getUserByUsername("joelD");
        CurrentUser.logUser(joelD);
        return joelD;
    }

}
