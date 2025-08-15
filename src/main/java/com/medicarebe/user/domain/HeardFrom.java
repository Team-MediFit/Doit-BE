package com.medicarebe.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HeardFrom {
    FACEBOOK("Facebook"),
    TIKTOK("Tiktok"),
    INSTAGRAM("Instagram"),
    INSTITUTION_RECOMMENDATION("Institution Recommendation"),
    WEBSITE_SEARCH("Website search"),
    FRIEND_RECOMMENDATION("Friend's recommendation"),
    ETC("etc.");

    private final String description;
}
