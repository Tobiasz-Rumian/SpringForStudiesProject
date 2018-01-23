package com.rumian.projekt1.offer.repository;

import com.rumian.projekt1.offer.model.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Offer findOfferByName(String offer);
}
