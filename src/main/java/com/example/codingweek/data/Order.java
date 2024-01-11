package com.example.codingweek.data;

import java.util.UUID;

public class Order {
    private final UUID id;
    private final Integer cost;
    private final String buyer, seller;

    public Order(UUID id, Integer cost, String buyer, String seller) {
        this.id = id;
        this.cost = cost;
        this.buyer = buyer;
        this.seller = seller;
    }
}
