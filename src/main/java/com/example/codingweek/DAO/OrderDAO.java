package com.example.codingweek.DAO;

import com.example.codingweek.data.Offer;
import com.example.codingweek.data.Order;
import com.example.codingweek.database.DataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class OrderDAO {

    private final DataBase db = DataBase.getInstance();

    public Order newOrder(Integer cost, String buyer, String seller) {
        Order order = new Order(cost, buyer, seller);
        addOrder(order);
        return order;
    }

    public void addOrder(Order order) {
        db.exec("insert into Orders (id, cost, buyer, seller) values (?,?,?,?)",
                order.getId(),
                order.getCost(),
                order.getBuyer(),
                order.getSeller());
    }

    public ArrayList<Order> getAllOrders(){
        ArrayList<Order> orderList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> ordersMap = db.fetchAllMap("SELECT * FROM Orders");
        for (HashMap<String,Object> order : ordersMap){
            orderList.add(new Order((UUID) order.get("id"),
                    (Integer) order.get("cost"),
                    order.get("buyer").toString(),
                    order.get("seller").toString()));
        }
        return orderList;
    }

    public ArrayList<Order> getOwnOrders(String username){
        ArrayList<Order> orderList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> ordersMap = db.fetchAllMap("SELECT * FROM Orders Where buyer = ?", username);
        for (HashMap<String,Object> order : ordersMap){
            orderList.add(new Order((UUID) order.get("id"),
                    (Integer) order.get("cost"),
                    order.get("buyer").toString(),
                    order.get("seller").toString()));
        }
        return orderList;
    }

    public Order getOrderById(UUID id) {
        HashMap<String, Object> orderMap = db.fetchOneMap("SELECT * FROM Orders WHERE id = ?", id);
        return new Order(id,
                (Integer) orderMap.get("cost"),
                orderMap.get("buyer").toString(),
                orderMap.get("seller").toString());
    }

}
