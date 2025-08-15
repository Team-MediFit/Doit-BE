package com.medicarebe.user.entity;

import com.medicarebe.core.BaseEntity;
import com.medicarebe.user.domain.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Table(name = "user")
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean agreedPolicy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    //선택입력

    @Column(length = 30)
    private String fullName;

    // 사용자가 한국에 오려는 이유 (자유 입력)
    @Column(name = "reason_come_to_korea", length = 255)
    private String reasonComeToKorea;

    // 계획된 학업 시작 월/일 (년도 미포함)
    @Column(name = "plan_study_date")
    private java.time.YearMonth planStudyDate;

    @Column(name = "plan_study_day")
    private Integer planStudyDay;   // 1~31

    // 생년월일
    @Column(name = "birth", nullable = false)
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


    public enum Role {
        USER,
        ADMIN
    }

}
