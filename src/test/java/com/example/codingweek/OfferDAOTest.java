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

    private DataBase db = DataBase.getInstance();


    @Test
    public void addOfferTestNoImage() throws Exception {
        db.reset();
        User joelD = getTestUser();

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Aspirateur");
        categories.add("Ménage");
        Offer offer = new Offer("Location d'aspirateur", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end", "joelDTest", null, 50, "Loan", true, categories);

        OfferDAO offerDAO = new OfferDAO();
        offerDAO.addOffer(offer, null);


        DataBase db = DataBase.getInstance();
        ArrayList<HashMap<String, Object>> fetch = db.fetchAllMap("select * from Offers where id =?", offer.getId());
        assertEquals(1, fetch.size());
        HashMap<String, Object> offerMap = fetch.get(0);
        assertEquals("Location d'aspirateur", offerMap.get("title"));
        assertEquals("Cherche à louer un aspirateur pour faire le grand ménage ce week-end", offerMap.get("description"));
        assertEquals("joelDTest", offerMap.get("user"));
        assertEquals(50, offerMap.get("price"));
        assertEquals("Loan", offerMap.get("type"));
        assertEquals("true", offerMap.get("availability"));
        ArrayList<HashMap<String, Object>> fetchCategories = db.fetchAllMap("select * from Categories where offer =?", offer.getId());
        assertEquals(2, fetchCategories.size());
        assertEquals("Aspirateur", fetchCategories.get(0).get("category"));
        assertEquals("Ménage", fetchCategories.get(1).get("category"));
    }

    @Test
    public void newOfferTestNoImage() throws Exception {
        db.reset();
        User joelD = getTestUser();

        System.out.println(CurrentUser.getUser());

        OfferDAO offerDAO = new OfferDAO();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Aspirateur");
        categories.add("balai");
        Offer offer = offerDAO.newOffer("Location de balai", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end", null, 50, "Loan", categories);


        assertEquals("Location de balai", offer.getTitle());
        assertEquals("Cherche à louer un aspirateur pour faire le grand ménage ce week-end", offer.getDescription());
        assertEquals("joelDTest", offer.getUser());
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
        db.reset();
        db.init();
        User joelD = getTestUser();
        OfferDAO offerDAO = new OfferDAO();

        ArrayList<Offer> ownOffers0 = offerDAO.getOwnOffers(joelD.userName);

        assertEquals(true, ownOffers0.isEmpty());

        Offer offer = createTestOffer();


        ArrayList<Offer> ownOffers = offerDAO.getOwnOffers(joelD.userName);

        assertEquals(false, ownOffers.isEmpty());
        assertEquals(1, ownOffers.size());

//        CurrentUser.logoutUser();
//        User admin = new UserDAO().getUserByUsername("annaG");
//        System.out.println(admin);
//        CurrentUser.logUser(admin);
//        new OfferDAO().newOffer("Location de balai", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end", null, 50, "Loan", new ArrayList<>());
//        CurrentUser.logoutUser();
//        CurrentUser.logUser(joelD);
//        ArrayList<Offer> ownOffers2 = offerDAO.getOwnOffers(joelD.userName);
//
//        assertEquals(false, ownOffers2.isEmpty());
//        assertEquals(1, ownOffers2.size());
    }

    @Test
    public void getOthersOfferTest() {

    }

    @Test
    public void getAllOffersTest() {

    }

    private Offer createTestOffer() throws Exception {
        User joelD = getTestUser();

        OfferDAO offerDAO = new OfferDAO();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Aspirateur");
        categories.add("balai");
        Offer offer = offerDAO.newOffer("Location de balai", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end", null, 50, "Loan", categories);
        return offer;
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
        System.out.println(joelD);
        CurrentUser.logUser(joelD);
        System.out.println(CurrentUser.getUser());
        return joelD;
    }


}
