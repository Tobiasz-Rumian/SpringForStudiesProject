package com.klima.projekt1.offer.service;

import com.klima.projekt1.configuration.ErrorCode;
import com.klima.projekt1.exception.DomainException;
import com.klima.projekt1.offer.model.entity.Offer;
import com.klima.projekt1.offer.repository.OfferRepository;
import io.vavr.control.Try;
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
        return Try.of(()->offerRepository.findOne(offerId)).getOrElseThrow(()->DomainException.of(ErrorCode.OFFER_NOT_FOUND));
    }
    public long getOfferUserCount(long offerId){
       return getOffers().size();
    }
}
