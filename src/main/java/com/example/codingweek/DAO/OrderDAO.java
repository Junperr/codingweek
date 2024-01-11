package com.example.codingweek.DAO;

import com.example.codingweek.data.Order;
import com.example.codingweek.database.DataBase;

import java.util.UUID;

public class OrderDAO {
    public Order newOrder(Integer cost, String buyer, String seller) {
        Order order = new Order(cost, buyer, seller);
        addOrder(order);
        return order;
    }

    public void addOrder(Order order) {
        DataBase db = DataBase.getInstance();
        db.exec("insert into Orders (id, cost, buyer, seller) values (?,?,?,?)",
                order.getId(),
                order.getCost(),
                order.getBuyer(),
                order.getSeller());
    }
}
