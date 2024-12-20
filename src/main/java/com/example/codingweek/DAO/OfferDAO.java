package com.example.codingweek.DAO;

import com.example.codingweek.auth.CurrentUser;
import com.example.codingweek.data.ImageFile;
import com.example.codingweek.data.Offer;
import com.example.codingweek.database.DataBase;
import com.example.codingweek.error.ErrorManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OfferDAO {

    private final DataBase db = DataBase.getInstance();

    public Offer newOffer(String title, String description, ImageFile image, Integer price, String type, ArrayList<String> Categories) throws Exception {
        Offer offer = new Offer(title, description, CurrentUser.getUser().userName, ImageFile.getImageName(image), price, type, true, Categories);
        System.out.println(image);
        System.out.println(ImageFile.getImageName(image));
        addOffer(offer, image);
        return offer;
    }

    public void addOffer(Offer offer, ImageFile image) throws Exception {
        ErrorManager errManager = new ErrorManager();

        try {
            errManager.handleEmptyFields(offer);
            errManager.handleInvalidTitle(offer.getTitle());
            errManager.handleInvalidDesc(offer.getDescription());
            errManager.handleInvalidPrice(offer.getPrice());
            errManager.handleInvalidType(offer.getType());
            errManager.handleInvalidImage(image);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

        String imagePath = null;

        if (image != null) {
            ImageFile.saveImage(image);
            imagePath = ImageFile.getImageName(image);
        } else {
            imagePath = "default.png";
        }

        db.exec("INSERT INTO Offers (id,title, description, imagePath, price, type, user, availability) VALUES (?, ?, ?, ?, ?, ?, ?, 'true')",
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                imagePath,
                offer.getPrice(),
                offer.getType(),
                CurrentUser.getUser().userName);
        // Insert the offer themes into the database
        for (String theme : offer.getCategories()) {
            db.exec("INSERT INTO Categories (offer, category) VALUES (?, ?)", offer.getId(), theme);
        }
    }

    public void updateAvailability(Offer offer) {
        db.exec("UPDATE Offers SET availability = ? WHERE id = ?", "false", offer.getId());
        offer.setAvailability(false);
    }

    public Offer getOfferById(UUID id) {
        HashMap<String, Object> offerMap = db.fetchOneMap("SELECT * FROM Offers WHERE id = ?", id);
        return new Offer(id,
                offerMap.get("title").toString(),
                offerMap.get("type").toString(),
                offerMap.get("user").toString(),
                offerMap.get("description").toString(),
                (offerMap.get("imagePath") != null) ? offerMap.get("imagePath").toString() : "default.png",
                Integer.parseInt(offerMap.get("price").toString()),
                Boolean.parseBoolean(offerMap.get("availability").toString()),
                getOfferCategories(offerMap));
    }

    public ArrayList<Offer> getOwnOffers(String username) {
        ArrayList<HashMap<String, Object>> offerMap = db.fetchAllMap("SELECT * FROM Offers Where user = ?", username);

        ArrayList<Offer> offers = new ArrayList<>();

        for (HashMap<String, Object> offer : offerMap) {
            offers.add(new Offer(UUID.fromString(offer.get("id").toString()),
                    offer.get("title").toString(),
                    offer.get("type").toString(),
                    offer.get("user").toString(),
                    offer.get("description").toString(),
                    (offer.get("imagePath") != null) ? offer.get("imagePath").toString() : "default.png",
                    Integer.parseInt(offer.get("price").toString()),
                    Boolean.parseBoolean(offer.get("availability").toString()),
                    getOfferCategories(offer)));
        }
        return offers;
    }

    /**
     * @param currentUserName
     * @return
     */
    public ArrayList<Offer> getOthersOffer(String currentUserName) {
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
                    offer.get("availability").toString().equals("true"),
                    categoriesString));
        }
        return offers;
    }


    public ArrayList<Offer> getAllOffers() {
        ArrayList<HashMap<String, Object>> offerMap = db.fetchAllMap("SELECT * FROM Offers");

        ArrayList<Offer> offers = new ArrayList<>();

        for (HashMap<String, Object> offer : offerMap) {
            offers.add(new Offer(UUID.fromString(offer.get("id").toString()),
                    offer.get("title").toString(),
                    offer.get("type").toString(),
                    offer.get("user").toString(),
                    offer.get("description").toString(),
                    (offer.get("imagePath") != null) ? offer.get("imagePath").toString() : "default.png",
                    Integer.parseInt(offer.get("price").toString()),
                    Boolean.parseBoolean(offer.get("availability").toString()),
                    getOfferCategories(offer)));
        }
        return offers;
    }

    public ArrayList<String> getOfferCategories(Map<String, Object> offer) {

        ArrayList<HashMap<String, Object>> categoriesMap = db.fetchAllMap("SELECT Category FROM Categories Where offer = ?", offer.get("id").toString());
        ArrayList<String> categories = new ArrayList<>();
        for (HashMap<String, Object> category : categoriesMap) {
            categories.add(category.get("category").toString());
        }
        return categories;
    }

    public Map<String, Object> getFilters(String type, String zipCode, String priceMin, String priceMax, ArrayList<String> categories) {
        Map<String, Object> filters = new HashMap<>();
        if (type != null) filters.put("type", type);
        if (!(zipCode == null || zipCode.equals(""))) filters.put("zipCode", zipCode);
        filters.put("priceMin", (priceMin == null || priceMin.equals("")) ? "-1" : priceMin);
        filters.put("priceMax", (priceMax == null || priceMax.equals("")) ? "-1" : priceMax);
        if (categories != null) filters.put("category", categories);
        return filters;

    }

    public ArrayList<Offer> getOffersWithFilters(String type, String zipCode, String priceMin, String priceMax, ArrayList<String> Selectedcategories) {
        Map<String, Object> args = getFilters(type, zipCode, priceMin, priceMax, Selectedcategories);

        var query = "select * from Offers as o join Categories as c on c.offer = o.id join Users u on o.user = u.userName ";
        if (!args.isEmpty()) {
            query += "where";
            String queryEnd = "";
            Boolean addFilter = false;
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
                    query += entry.getKey() + " like '";
                    query += entry.getValue().toString().substring(0,2);
                    query += "%' and";
                } else if (entry.getKey().equals("priceMax") && entry.getValue() != "-1") {
                    query += " o.price <=";
                    query += entry.getValue();
                    query += " and";
                } else if (entry.getKey().equals("priceMin") && entry.getValue() != "-1") {
                    query += " o.price >=";
                    query += entry.getValue();
                    query += " and";
                } else if (entry.getKey().equals("type")){
                    query += " o.";
                    query += entry.getKey() + "= '";
                    query += entry.getValue();
                    query += "' and";
                }
            }
            if (queryEnd.length() >= 3) {
                queryEnd = queryEnd.substring(0, queryEnd.length() - 3);
                query += queryEnd;
                query += ") and o.user != '" + CurrentUser.getUser().userName + "' and o.availability = 'true' group by o.id";
            } else {

                query += " o.user != '" + CurrentUser.getUser().userName + "' and o.availability = 'true' group by o.id";

            }
        } else query = "select * from offers";

        System.out.println(query);

        ArrayList<HashMap<String, Object>> offerMap = db.fetchAllMap(query);

        ArrayList<Offer> offers = new ArrayList<>();
        for (HashMap<String, Object> offer : offerMap) {
            System.out.println(offer.get("id"));

            offers.add(new Offer(UUID.fromString(offer.get("id").toString()),
                    offer.get("title").toString(),
                    offer.get("type").toString(),
                    offer.get("user").toString(),
                    offer.get("description").toString(),
                    (offer.get("imagePath") != null) ? offer.get("imagePath").toString() : "default.png",
                    Integer.parseInt(offer.get("price").toString()),
                    Boolean.parseBoolean(offer.get("availability").toString()),
                    getOfferCategories(offer)));
        }
        return offers;
    }

}
