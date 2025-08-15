package com.medicarebe.user.api.signin;

import com.medicarebe.user.service.SignupService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "Users - Sign Up", description = "회원가입 및 아이디 중복체크 API")
public class SignUpController {
    private final SignupService signupService;

    @Schema(name = "AuthIdDuplicationResponse", description = "authId 중복여부 응답")
    public record AuthIdDuplicationResponse(
            @Schema(description = "중복 여부", example = "true")
            boolean duplicate
    ) {}

    @PostMapping("/sign-up")
    @Operation(
            summary = "회원가입",
            description = "authId/username/password로 회원을 생성합니다. username은 현재 authId와 동일하게 저장되며, role은 USER로 기본 설정됩니다.")
    @ApiResponse(responseCode = "200", description = "회원가입 성공")
    @ApiResponse(responseCode = "400", description = "요청 값 검증 실패", content = @Content)
    @ApiResponse(responseCode = "409", description = "중복된 authId", content = @Content)
    public void signUp(@Valid @RequestBody SignUpRequest request) {
        signupService.signUp(request);
    }

    @GetMapping("/sign-up/check-duplicate-auth-id")
    @Operation(
            summary = "authId 중복체크",
            description = "주어진 authId가 이미 사용 중인지 확인합니다.")
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @Parameter(name = "auth-id", description = "6~20자 영문/숫자 조합 권장", required = true, example = "Test1234")
    public AuthIdDuplicationResponse checkAuthIdDuplication(
            @RequestParam("auth-id")
            @Size(min = 6, max = 20, message = "INVALIDATED_AUTHID_TYPE")
            String authId
    ) {
        boolean duplicated = signupService.checkDuplicateUser(authId);
        return new AuthIdDuplicationResponse(duplicated);
    }
}
