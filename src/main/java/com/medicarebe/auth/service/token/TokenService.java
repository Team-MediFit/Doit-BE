package com.medicarebe.auth.service.token;

import com.medicarebe.auth.repository.InMemoryTokenRepository;
import com.medicarebe.auth.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final InMemoryTokenRepository tokenRepository;
    private final TokenProvider tokenProvider;
    @Transactional
    public RefreshResult refresh(String oldRefreshToken) {
        if (oldRefreshToken == null || oldRefreshToken.isBlank()) {
            throw new IllegalArgumentException("INVALID_REFRESH");
        }
        Long userId = tokenRepository.findByRefreshToken(oldRefreshToken)
                .orElseThrow(() -> new IllegalArgumentException("INVALID_REFRESH"));

        tokenRepository.remove(oldRefreshToken);

        String newAccessToken = tokenProvider.generateToken(userId);
        String newRefreshToken = tokenProvider.generateRefreshToken();

        tokenRepository.saveRefreshToken(newRefreshToken, userId);

        return new RefreshResult(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void logout(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return; // idempotent: nothing to revoke
        }
        tokenRepository.remove(refreshToken);
    }

    public record RefreshResult(String accessToken, String refreshToken) {}
}