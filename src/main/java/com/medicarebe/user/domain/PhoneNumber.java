package com.medicarebe.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PhoneNumber {

    @Column(name = "phone_country_code", length = 5, nullable = false)
    private String countryCode;     // 예: "+63"

    @Column(name = "phone_number", length = 30, nullable = false)
    private String nationalNumber;  // 예: "9171234567"

    private PhoneNumber(String countryCode, String nationalNumber) {
        this.countryCode = countryCode;
        this.nationalNumber = nationalNumber;
    }

    /** "+639171234567" 같은 전체 문자열을 받아 파싱/검증 */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PhoneNumber fromString(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("INVALID_PHONE");
        }
        try {
            PhoneNumberUtil util = PhoneNumberUtil.getInstance();
            // "ZZ"는 국제 포맷(지역 미지정) 파싱용. +가 없으면 실패하도록.
            Phonenumber.PhoneNumber parsed = util.parse(raw, "ZZ");
            if (!util.isValidNumber(parsed)) {
                throw new IllegalArgumentException("INVALID_PHONE");
            }
            String cc = "+" + parsed.getCountryCode();
            String nn = String.valueOf(parsed.getNationalNumber());
            return new PhoneNumber(cc, nn);
        } catch (Exception e) {
            throw new IllegalArgumentException("INVALID_PHONE");
        }
    }

    /** countryCode와 nationalNumber로부터 생성(서버 내부용), 유효성 검증 포함 */
    public static PhoneNumber of(String countryCode, String nationalNumber) {
        if (countryCode == null || !countryCode.startsWith("+")) {
            throw new IllegalArgumentException("INVALID_COUNTRY_CODE");
        }
        if (nationalNumber == null || !nationalNumber.matches("\\d{4,}")) {
            throw new IllegalArgumentException("INVALID_NATIONAL_NUMBER");
        }
        try {
            PhoneNumberUtil util = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber()
                    .setCountryCode(Integer.parseInt(countryCode.substring(1)))
                    .setNationalNumber(Long.parseLong(nationalNumber));
            if (!util.isValidNumber(pn)) {
                throw new IllegalArgumentException("INVALID_PHONE");
            }
            return new PhoneNumber(countryCode, nationalNumber);
        } catch (Exception e) {
            throw new IllegalArgumentException("INVALID_PHONE");
        }
    }

    /** 직렬화 시 항상 E.164("+639171234567")로 출력 */
    @JsonValue
    public String toE164() {
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber()
                .setCountryCode(Integer.parseInt(countryCode.substring(1)))
                .setNationalNumber(Long.parseLong(nationalNumber));
        return util.format(pn, PhoneNumberUtil.PhoneNumberFormat.E164);
    }

    @Override
    public String toString() {
        return toE164();
    }

    // equals/hashCode 필요하면 추가 (countryCode + nationalNumber 기준)
    @Override
    public int hashCode() {
        return (countryCode + ":" + nationalNumber).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PhoneNumber other)) return false;
        return this.countryCode.equals(other.countryCode)
                && this.nationalNumber.equals(other.nationalNumber);
    }
}