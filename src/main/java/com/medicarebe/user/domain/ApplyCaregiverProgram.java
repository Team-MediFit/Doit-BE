package com.medicarebe.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplyCaregiverProgram {
    YES("Yes, I want to apply"),
    NO("No, thanks");

    private final String description;
}
