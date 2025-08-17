package com.medicarebe.user.api.update;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateUserDetailsRequest(
        @Schema(example = "Maria Lopez")
        String fullName,
        @Schema(example = "I want to study Korean language")
        String reasonComeToKorea,
        @Schema(example = "2025-09")
        java.time.YearMonth planStudyDate,
        @Schema(example = "1998-04-10")
        java.time.LocalDate birth,
        @Schema(example = "FEMALE")
        com.medicarebe.user.domain.Gender gender,
        @Schema(example = "Philippines")
        String nationality,
        @Schema(example = "+639171234567")
        com.medicarebe.user.domain.PhoneNumber phoneNumber,
        @Schema(example = "BEGINNER")
        com.medicarebe.user.domain.EnglishLevel englishLevel,
        @Schema(example = "TO_STUDY_FOR_TOPIK")
        com.medicarebe.user.domain.Purpose purpose,
        @Schema(example = "TOPIK_1")
        com.medicarebe.user.domain.TopikLevel topikLevel,
        @Schema(example = "FACEBOOK")
        com.medicarebe.user.domain.HeardFrom heardFrom,
        @Schema(example = "YES")
        com.medicarebe.user.domain.ApplyCaregiverProgram applyCaregiverProgram,
        @Schema(example = "GRADUATED_HIGH_SCHOOL")
        com.medicarebe.user.domain.EducationLevel educationLevel
) {}
