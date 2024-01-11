package com.example.codingweek.data;

import com.example.codingweek.DAO.OfferDAO;
import com.example.codingweek.SerializedData;

import java.util.ArrayList;
import java.util.UUID;

public class Offer extends SerializedData {

    private final UUID id;
    private String title, type, user, description, imagePath;

    private Integer price;

    private Boolean availability;

    private final ArrayList<String> Categories;

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Integer getPrice() {
        return price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public ArrayList<String> getCategories() {
        return Categories;
    }

    public UUID getId() {
        return id;
    }

    public Offer(String title, String description, String user, String imagePath, Integer price, String type, Boolean availability, ArrayList<String> Categories) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.type = type;
        this.user = user;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
        this.availability = availability;
        this.Categories = Categories;
    }

    public Offer(UUID id,String title, String type, String user, String description, String imagePath, Integer price, Boolean availability, ArrayList<String> Categories) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.user = user;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
        this.availability = availability;
        this.Categories = Categories;
    }

    public void printOffer() {
        System.out.println("Offer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", user='" + user + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                ", Categories=" + Categories +
                '}');
    }

}
