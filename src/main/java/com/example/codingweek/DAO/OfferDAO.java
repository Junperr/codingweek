package com.example.codingweek.DAO;

import com.example.codingweek.data.Offer;
import com.example.codingweek.data.User;
import com.example.codingweek.database.DataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OfferDAO {

    public Offer newOffer(String title, String description, String imagePath, Integer price, String type, ArrayList<String> Categories) {
        Offer offer = new Offer(title, description, User.getInstance().userName, imagePath, price, type, true, Categories);
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

    public Offer getOfferById(UUID id) {
        DataBase db = DataBase.getInstance();
        HashMap<String, Object> offerMap = db.fetchOneMap("SELECT * FROM Offers WHERE id = ?", id);
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

    public ArrayList<Offer> getOfferAvailableWithoutOwnOffer(String currentUserName) {
        DataBase db = DataBase.getInstance();
        ArrayList<HashMap<String, Object>> offerMap = db.fetchAllMap("SELECT * FROM Offers join Users U on U.userName = Offers.user WHERE userName != ? and availability != 0", currentUserName);

        ArrayList<HashMap<String, Object>> categories = db.fetchAllMap("SELECT category FROM Categories");
        ArrayList<Offer> offers = new ArrayList<>();

        for (HashMap<String, Object> offer : offerMap) {
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


    public ArrayList<Offer> getAllOffers() {
        DataBase db = DataBase.getInstance();
        ArrayList<HashMap<String, Object>> offerMap = db.fetchAllMap("SELECT * FROM Offers");
        ArrayList<HashMap<String, Object>> categories = db.fetchAllMap("SELECT category FROM Categories");

        ArrayList<Offer> offers = new ArrayList<>();

        for (HashMap<String, Object> offer : offerMap) {
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

    public Map<String, Object> getFilters(String type, String zipCode, String priceMin, String priceMax, ArrayList<String> categories) {
        Map<String, Object> filters = new HashMap<>();
        if (type != null) filters.put("type", type);
        if (!(zipCode == null || zipCode.equals(""))) filters.put("zipCode", zipCode);
        filters.put("priceMin", (priceMin == null || priceMin.equals("")) ? "0" : priceMin);
        filters.put("priceMax", (priceMax == null || priceMax.equals("")) ? "10000000" : priceMax);
        if (categories != null) filters.put("category", categories);
        return filters;

    }

    public ArrayList<Offer> getOffersWithFilters(String type, String zipCode, String priceMin, String priceMax, ArrayList<String> Selectedcategories) {
        Map<String, Object> args = getFilters(type, zipCode, priceMin, priceMax, Selectedcategories);

        DataBase db = DataBase.getInstance();
        var query = "select * from Offers as o join Categories as c on c.offer = o.id join Users u on o.user = u.userName ";
        if (!args.isEmpty()) {
            query += "where";
            String queryEnd = "";
            for (Map.Entry<String, Object> entry : args.entrySet()) {
                if (entry.getKey().equals("category") && entry.getValue() != null) {
                    queryEnd += " (";
                    for (String category : (ArrayList<String>) entry.getValue()) {
                        queryEnd += " c.";
                        queryEnd += entry.getKey() + "= '";
                        queryEnd += category;
                        queryEnd += "' or";
                    }
                } else if (entry.getKey().equals("zipCode") && entry.getValue() != "") {
                    query += " u.";
                    query += entry.getKey() + "= '";
                    query += entry.getValue();
                    query += "' and";
                } else if (entry.getKey().equals("priceMax")) {
                    query += " o.price <=";
                    query += entry.getValue();
                    query += " and";
                } else if (entry.getKey().equals("priceMin")) {
                    query += " o.price >=";
                    query += entry.getValue();
                    query += " and";
                } else {
                    if (entry.getValue() != null) {
                        query += " o.";
                        query += entry.getKey() + "= '";
                        query += entry.getValue();
                        query += "' and";
                    }
                }
            }
            if (queryEnd.length() >= 3) {
                queryEnd = queryEnd.substring(0, queryEnd.length() - 3);
                query += queryEnd;
                query += ") group by o.id";
            } else {
                query = query.substring(0, query.length() - 3);
                query += " group by o.id";
            }
        } else query = "select * from offers";

        ArrayList<HashMap<String, Object>> offerMap = db.fetchAllMap(query);

        ArrayList<Offer> offers = new ArrayList<>();
        ArrayList<String> categoriesString = new ArrayList<>();
        for (HashMap<String, Object> offer : offerMap) {
            ArrayList<HashMap<String, Object>> categories = db.fetchAllMap("SELECT category FROM Categories where offer= ?", offer.get("id"));
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
