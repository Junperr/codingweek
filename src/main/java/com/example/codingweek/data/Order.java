package com.example.codingweek.data;

import java.util.UUID;

public class Order {
    private final UUID id;
    private final Integer cost;
    private final String buyer, seller;

    public UUID getId() { return id; }

    public Integer getCost() { return cost; }

    public String getBuyer() { return buyer; }

    public String getSeller() { return seller; }

    public Order(Integer cost, String buyer, String seller) {
        this.id = UUID.randomUUID();
        this.cost = cost;
        this.buyer = buyer;
        this.seller = seller;
    }

    public Order(UUID id, Integer cost, String buyer, String seller) {
        this.id = id;
        this.cost = cost;
        this.buyer = buyer;
        this.seller = seller;
    }

    public void printOrder() {
        System.out.println(
                "id: " + id + '\n' +
                "cost: " + cost + '\n' +
                "buyer: " + buyer + '\n' +
                "seller: " + seller + '\n'
        );
    }
}
