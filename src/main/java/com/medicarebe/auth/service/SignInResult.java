package com.medicarebe.auth.service;

public record SignInResult(
        Long id,
        String accessToken,
        String refreshToken
) {

}
