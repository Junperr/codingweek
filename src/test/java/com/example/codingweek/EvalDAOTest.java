package com.example.codingweek;

import com.example.codingweek.DAO.EvalDAO;
import com.example.codingweek.DAO.OfferDAO;
import com.example.codingweek.DAO.OrderDAO;
import com.example.codingweek.DAO.UserDAO;
import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.Eval;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.Order;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvalDAOTest {

    private DataBase db = DataBase.getInstance();

    @Test
    public void addEvalTest() throws Exception {
        db.reset();
        User joelD = getTestUser();

        Order order = createTestOrder();
        EvalDAO evalDAO = new EvalDAO();

        Eval eval = new Eval(5, order.getId(), order.getBuyer(),"Test");
        evalDAO.addEval(eval);

        ArrayList<HashMap<String, Object>> evalsMap = db.fetchAllMap("SELECT * FROM Reviews Where orderId = ? And writer = ?", eval.getOrderId().toString(), eval.getWriter());

        assertEquals(1, evalsMap.size());
        assertEquals(eval.getOrderId().toString(), evalsMap.get(0).get("orderId").toString());
        assertEquals(eval.getEval(), evalsMap.get(0).get("eval"));
        assertEquals(eval.getWriter(), evalsMap.get(0).get("writer").toString());
        assertEquals(eval.getReviewDescription(), evalsMap.get(0).get("description").toString());
    }

    @Test
    public void newEvalTest() throws Exception{
        db.reset();
        User joelD = getTestUser();

        Order order = createTestOrder();
        EvalDAO evalDAO = new EvalDAO();

        Eval eval = evalDAO.createNewEval(5, order.getId(), order.getBuyer(),"Test");

        ArrayList<HashMap<String, Object>> evalsMap = db.fetchAllMap("SELECT * FROM Reviews Where orderId = ? And writer = ?", eval.getOrderId().toString(), eval.getWriter());

        assertEquals(1, evalsMap.size());
        assertEquals(eval.getOrderId().toString(), evalsMap.get(0).get("orderId").toString());
        assertEquals(eval.getEval(), evalsMap.get(0).get("eval"));
        assertEquals(eval.getWriter(), evalsMap.get(0).get("writer").toString());
        assertEquals(eval.getReviewDescription(), evalsMap.get(0).get("description").toString());
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
