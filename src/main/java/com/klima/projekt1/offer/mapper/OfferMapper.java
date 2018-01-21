package com.klima.projekt1.offer.mapper;


import com.klima.projekt1.offer.model.dto.OfferDto;
import com.klima.projekt1.offer.model.entity.Offer;
import com.klima.projekt1.offer.service.OfferService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class OfferMapper {

    public OfferService offerService;

    @Autowired
    public void setOfferRepository(OfferService offerService) {
        this.offerService = offerService;
        Offer offer;
    }

    @Mapping(target = "usersCount", expression = "java(offer.getUsers().size())")
    public abstract OfferDto toOfferDTO(Offer offer);

    public List<OfferDto> toOfferDTOs(List<Offer> offers) {
        return Optional.ofNullable(offers)
                .map(offers1 -> offers1.stream()
                        .map(this::toOfferDTO)
                        .collect(Collectors.toList()))
                .orElse(null);
    }
}

