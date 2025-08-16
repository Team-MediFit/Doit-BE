package com.medicarebe.user.api.resetpassword;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Schema(example = "Test1234")
    private String authId;

    @NotBlank
    @Size(min = 8, max = 64)
    @Pattern(
            regexp = "^(?=.*[!@#$%^&*])(?=.*[a-zA-Z0-9]).{8,64}$",
            message = "영문/숫자/특수문자 포함 8~64자"
    )
    @Schema(example = "Newpassword!")
    private String newPassword;

    @NotBlank
    @Schema(example = "Newpassword!")
    private String passwordCheck;
}