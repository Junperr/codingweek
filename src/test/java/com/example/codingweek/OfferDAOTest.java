package com.example.codingweek;

import com.example.codingweek.DAO.OfferDAO;
import com.example.codingweek.DAO.UserDAO;
import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.ImageFile;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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


        ArrayList<HashMap<String, Object>> fetch = db.fetchAllMap("select * from Offers where id =?", offer.getId());
        assertEquals(1, fetch.size());
        HashMap<String, Object> offerMap = fetch.get(0);
        assertEquals("Location d'aspirateur", offerMap.get("title"));
        assertEquals("Cherche à louer un aspirateur pour faire le grand ménage ce week-end", offerMap.get("description"));
        assertEquals("joelDTest", offerMap.get("user"));
        assertEquals(50, offerMap.get("price"));
        assertEquals("Loan", offerMap.get("type"));
        assertEquals("true", offerMap.get("availability"));
        assertEquals("default.png",offerMap.get("imagePath"));
        ArrayList<HashMap<String, Object>> fetchCategories = db.fetchAllMap("select * from Categories where offer =?", offer.getId());
        assertEquals(2, fetchCategories.size());
        assertEquals("Aspirateur", fetchCategories.get(0).get("category"));
        assertEquals("Ménage", fetchCategories.get(1).get("category"));
    }

    @Test
    public void addOfferTestImage() throws Exception {
        db.reset();
        User joelD = getTestUser();

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Aspirateur");
        categories.add("Ménage");
        ImageFile imagetest = new ImageFile("/home/junper/Info/PCD/codingweek-21/src/main/resources/static/images/offers/pelle.jpg", "offers/");
        Offer offer = new Offer("Location d'aspirateur", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end", "joelDTest", ImageFile.getImageName(imagetest), 50, "Loan", true, categories);

        OfferDAO offerDAO = new OfferDAO();
        offerDAO.addOffer(offer, imagetest);

        ArrayList<HashMap<String, Object>> fetch = db.fetchAllMap("select * from Offers where id =?", offer.getId());
        assertEquals(1, fetch.size());
        HashMap<String, Object> offerMap = fetch.get(0);
        assertEquals("Location d'aspirateur", offerMap.get("title"));
        assertEquals("Cherche à louer un aspirateur pour faire le grand ménage ce week-end", offerMap.get("description"));
        assertEquals("joelDTest", offerMap.get("user"));
        assertEquals(50, offerMap.get("price"));
        assertEquals("Loan", offerMap.get("type"));
        assertEquals("true", offerMap.get("availability"));
        assertEquals(ImageFile.getImageName(imagetest),offerMap.get("imagePath"));
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
    public void newOfferTestImage() throws Exception {
        db.reset();
        User joelD = getTestUser();

        System.out.println(CurrentUser.getUser());

        OfferDAO offerDAO = new OfferDAO();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Aspirateur");
        categories.add("balai");
        ImageFile imagetest = new ImageFile("/home/junper/Info/PCD/codingweek-21/src/main/resources/static/images/offers/pelle.jpg", "offers/");

        Offer offer = offerDAO.newOffer("Location de balai", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end", imagetest, 50, "Loan", categories);


        assertEquals("Location de balai", offer.getTitle());
        assertEquals("Cherche à louer un aspirateur pour faire le grand ménage ce week-end", offer.getDescription());
        assertEquals("joelDTest", offer.getUser());
        assertEquals(50, offer.getPrice());
        assertEquals("Loan", offer.getType());
        assertEquals(true, offer.getAvailability());
        assertEquals(2, offer.getCategories().size());
        assertEquals(ImageFile.getImageName(imagetest),offer.getImagePath());
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

        CurrentUser.logoutUser();
        User admin = new UserDAO().getUserByUsername("joelD");
        CurrentUser.logUser(admin);

        ArrayList<Offer> ownOffers3 = offerDAO.getOwnOffers(admin.userName);
        assertEquals(false, ownOffers3.isEmpty());
        assertEquals(3, ownOffers3.size());
        new OfferDAO().newOffer("Location de balai", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end", null, 50, "Loan", new ArrayList<>());

        ArrayList<Offer> ownOffers4 = offerDAO.getOwnOffers(admin.userName);
        assertEquals(false, ownOffers4.isEmpty());
        assertEquals(4, ownOffers4.size());

        CurrentUser.logoutUser();
        CurrentUser.logUser(joelD);
        ArrayList<Offer> ownOffers2 = offerDAO.getOwnOffers(joelD.userName);

        assertEquals(false, ownOffers2.isEmpty());
        assertEquals(1, ownOffers2.size());
    }

    @Test
    public void getOtherOffersTest() throws Exception {
        db.reset();
        db.init();

        User joelD = getTestUser();
        OfferDAO offerDAO = new OfferDAO();

        ArrayList<Offer> ownOffers0 = offerDAO.getOthersOffer(joelD.userName);

        assertEquals(false, ownOffers0.isEmpty());
        assertEquals(5, ownOffers0.size());


        Offer offer = createTestOffer();


        ArrayList<Offer> ownOffers = offerDAO.getOthersOffer(joelD.userName);

        assertEquals(false, ownOffers.isEmpty());
        assertEquals(5, ownOffers.size());

        CurrentUser.logoutUser();
        db.printData(db.fetchAll("SELECT * FROM Users"));
        User admin = new UserDAO().getUserByUsername("admin");
        System.out.println(admin);
        CurrentUser.logUser(admin);
        new OfferDAO().newOffer("Location de balai", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end", null, 50, "Loan", new ArrayList<>());
        CurrentUser.logoutUser();
        CurrentUser.logUser(joelD);
        ArrayList<Offer> ownOffers2 = offerDAO.getOthersOffer(joelD.userName);

        assertEquals(false, ownOffers2.isEmpty());
        assertEquals(6, ownOffers2.size());
    }

    @Test
    public void getAllOffersTest() throws Exception{
        db.reset();
        db.init();

        OfferDAO offerDAO = new OfferDAO();

        ArrayList<Offer> allOffers = offerDAO.getAllOffers();

        assertEquals(false, allOffers.isEmpty());
        assertEquals(5, allOffers.size());
        Offer offer = createTestOffer();
        ArrayList<Offer> allOffers1 = offerDAO.getAllOffers();

        assertEquals(false, allOffers1.isEmpty());
        assertEquals(6, allOffers1.size());

    }

    @Test
    public void getOffersWithFiltersTest() throws Exception{
        db.reset();
        db.init();
        User joelD = getTestUser();


        OfferDAO offerDAO = new OfferDAO();


        ArrayList<Offer> offersvoid = offerDAO.getOffersWithFilters(null, null,null,null,null);
        assertEquals(3, offersvoid.size());
        ArrayList<Offer> offersLoan = offerDAO.getOffersWithFilters("Loan", null,null,null,null);
        assertEquals(2, offersLoan.size());
        ArrayList<Offer> offersService = offerDAO.getOffersWithFilters("Service", null,null,null,null);
        assertEquals(1, offersService.size());
        ArrayList<Offer> offers59 = offerDAO.getOffersWithFilters(null, "59789",null,null,null);
        assertEquals(2, offers59.size());
        ArrayList<Offer> offers33 = offerDAO.getOffersWithFilters(null, "33789",null,null,null);
        assertEquals(1, offers33.size());
        ArrayList<Offer> offers02 = offerDAO.getOffersWithFilters(null, "02789",null,null,null);
        assertEquals(0, offers02.size());
        ArrayList<Offer> offersPriceMin100 = offerDAO.getOffersWithFilters(null, null,"100",null,null);
        assertEquals(1, offersPriceMin100.size());
        ArrayList<Offer> offersPriceMax100 = offerDAO.getOffersWithFilters(null, null,null,"100",null);
        assertEquals(2, offersPriceMax100.size());
        ArrayList<String> categoryElec = new ArrayList<String>() {{
            add("Electroménager");
        }};
        ArrayList<Offer> offersElec = offerDAO.getOffersWithFilters(null, null,null,null,categoryElec);
        assertEquals(1, offersElec.size());
        ArrayList<String> categoryOutilJardinAge = new ArrayList<String>() {{
            add("Jardinage");
            add("Jardin");
            add("Outils");
        }};
        ArrayList<Offer> offersOutilJardinAge = offerDAO.getOffersWithFilters(null, null,null,null,categoryOutilJardinAge);
        assertEquals(1, offersOutilJardinAge.size());
        ArrayList<Offer> offers7 = offerDAO.getOffersWithFilters("Loan", "59000", "10", "50", categoryElec);
        assertEquals(1, offers7.size());

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
