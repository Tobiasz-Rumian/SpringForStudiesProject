package com.rumian.projekt1.offer.service;

import com.rumian.projekt1.offer.model.entity.Offer;
import com.rumian.projekt1.offer.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    OfferRepository offerRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<Offer> getOffers(){
        return offerRepository.findAll();
    }

    public Offer getOffer(long offerId){
        return offerRepository.findOne(offerId);
    }
    public Offer getOfferByName(String offer){
       return offerRepository.findOfferByName(offer);
    }
    public long getOfferUserCount(long offerId){
       return getOffers().size();
    }

    public void saveOffer(Offer offer) {
        offerRepository.save(offer);
    }

    public void deleteOffer(long offerId) {
        offerRepository.delete(offerId);
    }
}
