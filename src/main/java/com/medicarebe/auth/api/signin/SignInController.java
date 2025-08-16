package com.medicarebe.auth.api.signin;

import com.medicarebe.auth.api.token.TokenResponse;
import com.medicarebe.auth.service.signin.SignInService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "SignIn", description = "로그인 API")
public class SignInController {

    private final SignInService signInService;

    @PostMapping("/sign-in")
    public TokenResponse signIn(@Valid @RequestBody SignInRequest request, HttpServletResponse response) {

        var signInResult = signInService.signIn(request.authId(), request.password());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", signInResult.refreshToken())
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("None")
                .secure(true)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return TokenResponse.of(signInResult.accessToken());
    }
}
