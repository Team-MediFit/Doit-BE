package com.medicarebe.auth.api.logout;

import com.medicarebe.auth.service.token.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Logout", description = "로그아웃 API")
public class LogoutController {
    private final TokenService tokenService;

    @PostMapping("logout")
    public ResponseEntity<Void> logout(@CookieValue(name="refreshToken", required=false) String refreshCookie,
                                       HttpServletResponse response) {
        if (refreshCookie != null) tokenService.logout(refreshCookie);

        ResponseCookie expired = ResponseCookie.from("refreshToken", "")
                .httpOnly(true).
                secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, expired.toString());
        return ResponseEntity.noContent().build();
    }
}
