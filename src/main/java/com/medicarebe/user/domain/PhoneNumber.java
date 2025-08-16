package com.medicarebe.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class PhoneNumber {
    @Column(name = "phone_country_code", length = 10)
    private String countryCode; // "+63"
    @Column(name = "phone_number", length = 30)
    private String number;      // "9171234567"

    protected PhoneNumber() {}

    public PhoneNumber(String countryCode, String number) {
        this.countryCode = countryCode;
        this.number = number;
    }

    /** "+639171234567" 같은 문자열을 받아 PhoneNumber로 변환 */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PhoneNumber fromString(String raw) {
        if (raw == null || !raw.startsWith("+")) {
            throw new IllegalArgumentException("INVALID_PHONE");
        }
        String digits = raw.substring(1);
        // 국가코드/번호 파싱 로직은 단순 예시 — 필요시 libphonenumber 사용 권장
        String cc = "+" + digits.substring(0, 2);        // 예: "+63"
        String num = digits.substring(2);                // 예: "9171234567"
        return new PhoneNumber(cc, num);
    }

    /** 직렬화 시 "+639171234567" 형태로 출력 */
    @JsonValue
    public String toE164() {
        return countryCode + number;
    }
}