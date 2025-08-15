package com.medicarebe.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class PhoneNumber {
    @Column(name = "phone_country_code", length = 10)
    private String countryCode; // 예: "+82"

    @Column(name = "phone_number", length = 30)
    private String number;      // 예: "10-1234-5678"

    protected PhoneNumber() {}

    public PhoneNumber(String countryCode, String number) {
        this.countryCode = countryCode;
        this.number = number;
    }
}