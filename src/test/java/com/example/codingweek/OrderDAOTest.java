package com.example.codingweek;

import com.example.codingweek.DAO.OfferDAO;
import com.example.codingweek.DAO.OrderDAO;
import com.example.codingweek.DAO.UserDAO;
import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.ImageFile;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.Order;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDAOTest {

    private DataBase db = DataBase.getInstance();


    @Test
    public void addOrderTest() throws Exception {
        db.reset();
        User joelD = getTestUser();

        Offer offer = createTestOffer();
        OrderDAO orderDAO = new OrderDAO();

        Order order = new Order(offer.getId(), 50, "joelDTest", offer.getUser());
        orderDAO.addOrder(order);

        ArrayList<HashMap<String, Object>> ordersMap = db.fetchAllMap("SELECT * FROM Orders Where id = ?", order.getId().toString());

        assertEquals(1, ordersMap.size());
        assertEquals(order.getId().toString(), ordersMap.get(0).get("id").toString());
        assertEquals(order.getOfferId().toString(), ordersMap.get(0).get("offer").toString());
        assertEquals(order.getCost(), ordersMap.get(0).get("cost"));
        assertEquals(order.getBuyer(), ordersMap.get(0).get("buyer").toString());
    }

    @Test
    public void newOrderTest() throws Exception {
        db.reset();
        User joelD = getTestUser();

        Offer offer = createTestOffer();
        OrderDAO orderDAO = new OrderDAO();

        Order order = orderDAO.newOrder(offer.getId(), 50, "joelDTest", offer.getUser());

        ArrayList<HashMap<String, Object>> ordersMap = db.fetchAllMap("SELECT * FROM Orders Where id = ?", order.getId().toString());

        assertEquals(1, ordersMap.size());
        assertEquals(order.getId().toString(), ordersMap.get(0).get("id").toString());
        assertEquals(order.getOfferId().toString(), ordersMap.get(0).get("offer").toString());
        assertEquals(order.getCost(), ordersMap.get(0).get("cost"));
        assertEquals(order.getBuyer(), ordersMap.get(0).get("buyer").toString());
    }

    @Test
    public void getOrderByIdTest() throws Exception {
        Order order = createTestOrder();
        OrderDAO orderDAO = new OrderDAO();
        Order order2 = orderDAO.getOrderById(order.getId());



        assertEquals(order.getId(), order2.getId());
        assertEquals(order.getOfferId(), order2.getOfferId());
        assertEquals(order.getCost(), order2.getCost());
        assertEquals(order.getBuyer(), order2.getBuyer());
        assertEquals(order.getSeller(), order2.getSeller());
    }

    @Test
    public void getAllOrdersTest() throws Exception{
        db.reset();
        db.init();

        OrderDAO orderDAO = new OrderDAO();

        ArrayList<Order> allOrders = orderDAO.getAllOrders();

        assertEquals(false, allOrders.isEmpty());
        assertEquals(2, allOrders.size());

        createTestOrder();
        Offer offer = createTestOffer();

        allOrders = orderDAO.getAllOrders();

        assertEquals(false, allOrders.isEmpty());
        assertEquals(3, allOrders.size());

    }

    @Test
    public void getOwnOrdersTest() throws Exception {
        db.reset();
        db.init();

        User joelD = getTestUser();
        OfferDAO offerDAO = new OfferDAO();
        OrderDAO orderDAO = new OrderDAO();

        ArrayList<Order> ownOrders0 = orderDAO.getOwnOrders(joelD.userName);

        assertEquals(true, ownOrders0.isEmpty());

        Order order = createTestOrder();

        ArrayList<Order> ownOrders = orderDAO.getOwnOrders(joelD.userName);

        assertEquals(false, ownOrders.isEmpty());
        assertEquals(1, ownOrders.size());

        CurrentUser.logoutUser();

        User admin = new UserDAO().getUserByUsername("joelD");
        CurrentUser.logUser(admin);

        ArrayList<Order> ownOrders3 = orderDAO.getOwnOrders(admin.userName);
        assertEquals(false, ownOrders3.isEmpty());
        assertEquals(1, ownOrders3.size());

        CurrentUser.logoutUser();
        CurrentUser.logUser(joelD);
        ArrayList<Order> ownOrders2 = orderDAO.getOwnOrders(joelD.userName);

        assertEquals(false, ownOrders2.isEmpty());
        assertEquals(1, ownOrders2.size());
    }

    @Test
    public void passOrderTest() throws Exception {
        db.reset();
        db.init();

        User joelD = getTestUser();
        OfferDAO offerDAO = new OfferDAO();
        OrderDAO orderDAO = new OrderDAO();

        Offer offer = createTestOffer();

        CurrentUser.logoutUser();
        CurrentUser.logUser(new UserDAO().getUserByUsername("admin"));

        User user = CurrentUser.getUser();

        orderDAO.passOrder(offer.getId(), "", user);

        ArrayList<HashMap<String, Object>> ordersMap = db.fetchAllMap("SELECT * FROM Orders Where buyer = ?", user.userName);

        assertEquals(1, ordersMap.size());
        assertEquals(offer.getId().toString(), ordersMap.get(0).get("offer").toString());
        assertEquals(offer.getPrice(), ordersMap.get(0).get("cost"));
        assertEquals(user.userName, ordersMap.get(0).get("buyer").toString());
        assertEquals(offer.getUser(), ordersMap.get(0).get("seller").toString());

        assertEquals(offerDAO.getOfferById(offer.getId()).getAvailability(), false);
    }

    private Order createTestOrder() throws Exception{
        User joelD = getTestUser();

        OfferDAO offerDAO = new OfferDAO();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Aspirateur");
        categories.add("balai");
        Offer offer = offerDAO.newOffer("Location de balai", "Cherche à louer un aspirateur pour faire le grand ménage ce week-end", null, 50, "Loan", categories);

        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.newOrder(offer.getId(), 50, "joelDTest", offer.getUser());
        return order;
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
