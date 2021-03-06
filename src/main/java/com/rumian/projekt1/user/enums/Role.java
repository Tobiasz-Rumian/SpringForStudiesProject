package com.rumian.projekt1.user.enums;

import com.rumian.projekt1.user.model.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    UNLOGGED(new Authority("UNLOGGED")),
    USER(new Authority("USER")),
    ADMIN(new Authority("ADMIN"));

    private final Authority authority;
}
