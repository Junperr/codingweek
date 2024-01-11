package com.example.codingweek.DAO;

import com.example.codingweek.data.Offer;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class OfferDAO {

    public Offer newOffer(String title, String description, String imagePath, Integer price, String type, ArrayList<String> Categories){
        Offer offer =  new Offer(title, description, User.getInstance().userName, imagePath, price, type, true, Categories);
        addOffer(offer);
        return offer;
    }

    public void addOffer(Offer offer) {
        DataBase db = DataBase.getInstance();
        db.exec("INSERT INTO Offers (id,title, description, price, type, user, availability) VALUES (?, ?, ?, ?, ?, ?, 1)",
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                offer.getPrice(),
                offer.getType(),
                User.getInstance().userName);
        // Insert the offer themes into the database
        for (String theme : offer.getCategories()) {
            db.exec("INSERT INTO Categories (offer, category) VALUES (?, ?)", offer.getId(), theme);
        }
    }

    public Offer getOfferById(UUID id){
        DataBase db = DataBase.getInstance();
        HashMap<String,Object> offerMap = db.fetchOneMap("SELECT * FROM Offers WHERE id = ?", id);
        ArrayList<ArrayList<Object>> categories = db.fetchAll("SELECT category FROM Categories WHERE offer = ?", id);
        ArrayList<String> categoriesString = new ArrayList<>();
        for (ArrayList<Object> category : categories) {
            categoriesString.add(category.get(0).toString());
        }
        return new Offer(id,
                offerMap.get("title").toString(),
                offerMap.get("type").toString(),
                offerMap.get("user").toString(),
                offerMap.get("description").toString(),
                (offerMap.get("imagePath") != null) ? offerMap.get("imagePath").toString() : "default.png",
                Integer.parseInt(offerMap.get("price").toString()),
                Boolean.parseBoolean(offerMap.get("availability").toString()),
                categoriesString);
    }

    public ArrayList<Offer> getAllOffers(){
        DataBase db = DataBase.getInstance();
        ArrayList<HashMap<String,Object>> offerMap = db.fetchAllMap("SELECT * FROM Offers");
        ArrayList<HashMap<String,Object>> categories = db.fetchAllMap("SELECT category FROM Categories");

        ArrayList<Offer> offers = new ArrayList<>();

        for (HashMap<String,Object> offer : offerMap) {
            ArrayList<String> categoriesString = new ArrayList<>();
            for (HashMap<String, Object> category : categories) {
                categoriesString.add(category.get("category").toString());
            }
            offers.add(new Offer(UUID.fromString(offer.get("id").toString()),
                    offer.get("title").toString(),
                    offer.get("type").toString(),
                    offer.get("user").toString(),
                    offer.get("description").toString(),
                    (offer.get("imagePath") != null) ? offer.get("imagePath").toString() : "default.png",
                    Integer.parseInt(offer.get("price").toString()),
                    Boolean.parseBoolean(offer.get("availability").toString()),
                    categoriesString));
        }
        return offers;
    }

}
