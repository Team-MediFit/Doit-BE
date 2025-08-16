package com.medicarebe.user.api.mail;

import com.medicarebe.user.service.VerificationCodeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/email")
@RequiredArgsConstructor
@Validated
public class EmailVerificationController {

    private final VerificationCodeService verificationCodeService;

    public record SendCodeRequest(@Email @NotBlank String email) {}
    public record VerifyCodeRequest(@Email @NotBlank String email, @NotBlank String code) {}
    public record VerifyCodeResponse(boolean verified, String message) {}

    @PostMapping("/send-code")
    public ResponseEntity<Void> send(@RequestBody @Valid SendCodeRequest req) {
        verificationCodeService.sendCode(req.email());
        return ResponseEntity.accepted().build(); // 202 Accepted
    }

    @PostMapping("/verify-code")
    public ResponseEntity<VerifyCodeResponse> verify(@RequestBody @Valid VerifyCodeRequest req) {
        boolean ok = verificationCodeService.verify(req.email(), req.code());
        return ResponseEntity.ok(
                new VerifyCodeResponse(ok, ok ? "Verification succeeded." : "Invalid or expired code.")
        );
    }
}