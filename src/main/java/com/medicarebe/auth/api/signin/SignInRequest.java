package com.medicarebe.auth.api.signin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SignInRequest (
        @NotBlank(message = "아이디를 입력해주세요.")
        @Schema(name = "authId", example = "Test1234")
        String authId,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Schema(name = "password", example = "Test1234!")
        String password
) {

}

