package com.example.codingweek.facade;

import com.example.codingweek.DAO.OfferDAO;
import com.example.codingweek.Offer.Offer;

import java.util.ArrayList;
import java.util.UUID;

public class OfferFacade {

    private final OfferDAO offerDAO;

    public OfferFacade() {
        this.offerDAO = new OfferDAO();
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
}
