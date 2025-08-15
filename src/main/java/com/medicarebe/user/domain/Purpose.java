package com.medicarebe.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Purpose {
    TO_GET_JOB_AS_CAREGIVER_IN_KOREA("To get a job as a caregiver in Korea"),
    TO_FIND_OTHER_JOB_IN_KOREA("To find other job in Korea"),
    TO_STUDY_ABROAD_IN_KOREA("To come to study abroad in Korea"),
    TO_STUDY_FOR_TOPIK("To study for the TOPIK"),
    TO_STUDY_KOREAN_LANGUAGE("To study Korean language");

    private final String description;
}