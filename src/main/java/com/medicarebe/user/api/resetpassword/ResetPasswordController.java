package com.medicarebe.user.api.resetpassword;


import com.medicarebe.user.service.ResetPasswordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "PassWord Reset", description = "비밀번호 재설정 API")
public class ResetPasswordController {

    private final ResetPasswordService service;

    @PostMapping("/password/reset")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        service.reset(request);
        return ResponseEntity.noContent().build(); // 204
    }
}