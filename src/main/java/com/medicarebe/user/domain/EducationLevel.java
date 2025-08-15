package com.medicarebe.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EducationLevel {
    GRADUATED_HIGH_SCHOOL("Graduated from high school"),
    GRADUATED_COLLEGE_UNIVERSITY("Graduated from college/university"),
    STUDYING_REGULAR_12TH("Currently studying in the regular 12th grade"),
    STUDYING_CONTINUING_12TH("Currently studying in the 12th grade of continuing education"),
    VOCATIONAL_TRAINING("Vocational training"),
    ETC("etc.");

    private final String description;

}
