package com.klima.projekt1.offer.mapper;


import com.klima.projekt1.offer.model.dto.OfferDTO;
import com.klima.projekt1.offer.model.entity.Offer;
import com.klima.projekt1.offer.service.OfferService;
import lombok.AccessLevel;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

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
    public abstract OfferDTO toOfferDTO(Offer offer);

    public List<OfferDTO> toOfferDTOs(List<Offer> events) {
        return Optional.ofNullable(events)
                .map(events1 -> events1.stream()
                        .map(this::toOfferDTO)
                        .collect(Collectors.toList()))
                .orElse(null);
    }
}

