package com.example.codingweek.facade;

import com.example.codingweek.DAO.OfferDAO;
import com.example.codingweek.DAO.UserDAO;
import com.example.codingweek.data.Offer;
import com.example.codingweek.data.User;

import java.util.ArrayList;
import java.util.UUID;

public class BigFacade {

    private final OfferDAO offerDAO;

    private final UserDAO userDAO;

    public BigFacade() {
        this.offerDAO = new OfferDAO();
        this.userDAO = new UserDAO();
    }

    public Offer createNewOffer(String title, String description, String imagePath, Integer price, String type, ArrayList<String> categories) {
        return offerDAO.newOffer(title, description, imagePath, price, type, categories);
    }

    public Offer getOfferById(UUID id) {
        return offerDAO.getOfferById(id);
    }

    public ArrayList<Offer> getAllOffers() {
        return offerDAO.getAllOffers();
    }

    public ArrayList<Offer> getOffersWithFilters(String type, String zipCode, String priceMin, String priceMax, ArrayList<String> categories) {
        return offerDAO.getOffersWithFilters(type, zipCode, priceMin, priceMax, categories);
    }

    public void createNewUser(String firstName, String lastName, String userName, String email, String password, String address, String city, String zipCode) throws Exception{
        userDAO.newUser(firstName, lastName, userName, email, password, address, city, zipCode);
    }

    public void logUser(String username, String pwd) throws Exception{
        userDAO.logUser(username, pwd);
    }

    public void updateField(User user, String newValue, String field, String pwd) throws Exception{
        userDAO.updateField(newValue, field, pwd, user);
    }

    public void updatePosUser(User user, String address, String city, String zipcode, String pwd) throws Exception{
        userDAO.checkedUpdatePos(user, address, city, zipcode, pwd);
    }

    public User getUserByUsername(String username) throws Exception{
        return userDAO.getUserByUsername(username);
    }
}
