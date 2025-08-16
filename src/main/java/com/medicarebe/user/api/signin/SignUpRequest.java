package com.medicarebe.user.api.signin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(

        @Schema(description = "사용자 이름", example = "홍길동")
        @NotBlank
        @Size(max = 50)
        String userName,

        @Schema(description = "로그인 ID", example = "Test1234")
        @NotBlank
        @Size(max = 50)
        String authId,

        @Schema(description = "비밀번호", example = "Test1234!")
        @NotBlank
        @Size(min = 8, max = 64)
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d|.*\\W).{8,64}$",
                message = "영문과 숫자/특수문자 중 2종 이상 포함"
        )
        String password,

        @Schema(description = "비밀번호 확인", example = "Test1234!")
        String confirmPassword,

        @Schema(description = "정책 동의 여부", example = "true")
        @AssertTrue
        Boolean agreedPolicy
) {}