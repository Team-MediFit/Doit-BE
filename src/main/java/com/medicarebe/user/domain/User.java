package com.medicarebe.user.domain;

import com.medicarebe.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.YearMonth;

@Table(name = "user")
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String authId;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean agreedPolicy;


    //선택입력
    @Column(length = 30)
    private String fullName;

    // 사용자가 한국에 오려는 이유 (자유 입력)
    @Column(name = "reason_come_to_korea")
    private String reasonComeToKorea;

    // 계획된 학업 시작 년/월 (년도 미포함)
    @Column(name = "plan_study_date")
    private java.time.YearMonth planStudyDate;

    // 생년월일
    @Column(name = "birth")
    private java.time.LocalDate birth;

    // 성별
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

    // 국적
    @Column(name = "nationality", length = 50)
    private String nationality;

    // 전화번호 (국가번호 + 번호)
    @Embedded
    private PhoneNumber phoneNumber;

    // 한국에 오려는 목적
    @Enumerated(EnumType.STRING)
    @Column(name = "purpose", length = 50)
    private Purpose purpose;

    // TOPIK 수준
    @Enumerated(EnumType.STRING)
    @Column(name = "topik_level", length = 20)
    private TopikLevel topikLevel;

    // DoitDoit 알게 된 경로
    @Enumerated(EnumType.STRING)
    @Column(name = "heard_from", length = 50)
    private HeardFrom heardFrom;

    // 케어기버 과정 지원 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "apply_caregiver_program", length = 20)
    private ApplyCaregiverProgram applyCaregiverProgram;

    // 최종 학력
    @Enumerated(EnumType.STRING)
    @Column(name = "education_level", length = 50)
    private EducationLevel educationLevel;

    public static User create(
            String authId,
            String username,
            String encodedPassword,
            boolean agreedPolicy
    ) {
        return User.builder()
                .authId(authId)
                .username(username)
                .password(encodedPassword)
                .agreedPolicy(agreedPolicy)
                .build();
    }
    public void updateDetails(
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
        if (fullName != null) this.fullName = fullName;
        if (reasonComeToKorea != null) this.reasonComeToKorea = reasonComeToKorea;
        if (planStudyDate != null) this.planStudyDate = planStudyDate;
        if (birth != null) this.birth = birth;
        if (gender != null) this.gender = gender;
        if (nationality != null) this.nationality = nationality;
        if (phoneNumber != null) this.phoneNumber = phoneNumber;
        if (purpose != null) this.purpose = purpose;
        if (topikLevel != null) this.topikLevel = topikLevel;
        if (heardFrom != null) this.heardFrom = heardFrom;
        if (applyCaregiverProgram != null) this.applyCaregiverProgram = applyCaregiverProgram;
        if (educationLevel != null) this.educationLevel = educationLevel;
    }
}
