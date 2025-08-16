package com.medicarebe.auth.api.token;

import com.medicarebe.auth.service.token.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Token", description = "액세스 토큰 재발급 API")
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/token")
    public TokenResponse refresh(@CookieValue("refreshToken") String refreshCookie,
                                 HttpServletResponse response) {
        var result = tokenService.refresh(refreshCookie);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", result.refreshToken())
                .httpOnly(true)
                .secure(true).
                sameSite("None")
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return TokenResponse.of(result.accessToken());
    }
}