package com.klima.projekt1.user.mapper;

import com.klima.projekt1.user.model.dto.UserDto;
import com.klima.projekt1.user.model.entity.User;
import org.mapstruct.Mapper;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public class UserMapper {

    public UserDto toUserDto(User user) {
        boolean haveDebt = user.getPayDate() != null && user.getPayDate().isBefore(ZonedDateTime.now());
        String offerName = (user.getOffer() == null) ? "Brak" : user.getOffer().getName();
        return Optional.of(user)
                .map(user1 -> UserDto.builder().offerName(offerName)
                        .firstName(user1.getFirstName())
                        .lastName(user1.getLastName())
                        .address(user1.getAddress().getAddress())
                        .email(user1.getEmail())
                        .pesel(user1.getPesel())
                        .phoneNumber(user1.getAddress().getPhoneNumber()).haveDebt(haveDebt).build()).orElse(null);

    }

    public List<UserDto> toUserDTOs(List<User> users) {
        return Optional.ofNullable(users)
                .map(users1 -> users1.stream()
                        .map(this::toUserDto)
                        .collect(Collectors.toList()))
                .orElse(null);
    }
}
