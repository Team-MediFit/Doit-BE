package com.medicarebe.user.api.update;

import com.medicarebe.user.domain.*;

import java.time.LocalDate;
import java.time.YearMonth;

public record UserResponse(
        Long id,
        String fullName,
        String reasonComeToKorea,
        YearMonth planStudyDate,
        LocalDate birth,
        Gender gender,
        String nationality,
        PhoneNumber phoneNumber,
        Purpose purpose,
        TopikLevel topikLevel,
        HeardFrom heardFrom,
        ApplyCaregiverProgram applyCaregiverProgram,
        EducationLevel educationLevel
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getReasonComeToKorea(),
                user.getPlanStudyDate(),
                user.getBirth(),
                user.getGender(),
                user.getNationality(),
                user.getPhoneNumber(),
                user.getPurpose(),
                user.getTopikLevel(),
                user.getHeardFrom(),
                user.getApplyCaregiverProgram(),
                user.getEducationLevel()
        );
    }
}
