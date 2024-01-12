package com.example.codingweek.DAO;

import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.Order;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;
import com.example.codingweek.error.ErrorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class OrderDAO {

    private final DataBase db = DataBase.getInstance();

    public Order newOrder(UUID offerId, Integer cost, String buyer, String seller) {
        Order order = new Order(offerId, cost, buyer, seller);
        addOrder(order);
        return order;
    }

    public void addOrder(Order order) {
        db.exec("insert into Orders (id, offer, cost, buyer, seller) values (?,?,?,?,?)",
                order.getId(),
                order.getOfferId(),
                order.getCost(),
                order.getBuyer(),
                order.getSeller());
    }

    public ArrayList<Order> getAllOrders(){
        ArrayList<Order> orderList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> ordersMap = db.fetchAllMap("SELECT * FROM Orders");
        for (HashMap<String,Object> order : ordersMap){
            orderList.add(new Order(UUID.fromString(order.get("id").toString()),
                    UUID.fromString(order.get("offer").toString()),
                    (Integer) order.get("cost"),
                    order.get("buyer").toString(),
                    order.get("seller").toString()));
        }
        return orderList;
    }

    public ArrayList<Order> getOwnOrders(String username){
        System.out.println(username);
        ArrayList<Order> orderList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> ordersMap = db.fetchAllMap("SELECT * FROM Orders Where buyer = ?", username);
        for (HashMap<String,Object> order : ordersMap){
            orderList.add(new Order(UUID.fromString(order.get("id").toString()),
                    UUID.fromString(order.get("offer").toString()),
                    (Integer) order.get("cost"),
                    username,
                    order.get("seller").toString()));
        }
        System.out.println(orderList);
        return orderList;
    }

    public Order getOrderById(UUID id) {
        HashMap<String, Object> orderMap = db.fetchOneMap("SELECT * FROM Orders WHERE id = ?", id);
        return new Order(id,
                (Integer) orderMap.get("cost"),
                orderMap.get("buyer").toString(),
                orderMap.get("seller").toString());
    }



    public void passOrder(UUID offerId, String pwd, User user) throws Exception {
        ErrorManager errManager = new ErrorManager();
        UserDAO userDAO = new UserDAO();
        OfferDAO offerDAO = new OfferDAO();

        try {
            errManager.handleCheckPassWord(userDAO.getUserByPassword(user.userName, pwd));

            Offer offer = offerDAO.getOfferById(offerId);
            errManager.handleInvalidCoinsOrder(user.coins - offer.getPrice());
            User seller = userDAO.getUserByUsername(offer.getUser());
            userDAO.updateCoins(user, user.coins - offer.getPrice());
            userDAO.updateCoins(seller, seller.coins + offer.getPrice());

            Order order = newOrder(offerId,offer.getPrice(),user.userName,seller.userName);
            addOrder(order);

            offerDAO.updateAvailability(offer);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }



    }

    public ArrayList<Order> getOwnOrdersOther(String usernameSeller){
        ArrayList<Order> orderList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> ordersMap = db.fetchAllMap("SELECT * FROM Orders Where buyer = ? and seller = ?", CurrentUser.getUser().userName, usernameSeller);
        for (HashMap<String,Object> order : ordersMap){
            orderList.add(new Order(UUID.fromString(order.get("id").toString()),
                    (Integer) order.get("cost"),
                    order.get("buyer").toString(),
                    order.get("seller").toString()));
        }
        return orderList;
    }

}
