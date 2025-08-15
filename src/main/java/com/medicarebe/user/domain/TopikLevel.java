package com.medicarebe.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TopikLevel {
    TOPIK_1("TOPIK 1"),
    TOPIK_2("TOPIK 2"),
    TOPIK_3("TOPIK 3"),
    TOPIK_4("TOPIK 4"),
    TOPIK_5("TOPIK 5"),
    TOPIK_6("TOPIK 6"),
    NEVER_TAKEN("Never taken TOPIK");

    private final String description;
}
