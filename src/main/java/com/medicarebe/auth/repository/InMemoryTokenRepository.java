package com.medicarebe.auth.repository;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class InMemoryTokenRepository {
    private static final Cache<String, Long> TOKEN_REPOSITORY = CacheBuilder.newBuilder()
            .expireAfterWrite(7, TimeUnit.DAYS)
            .build();


    public void saveRefreshToken(String refreshToken, Long userId) {
        TOKEN_REPOSITORY.put(refreshToken, userId);
    }


    public Optional<Long> findByRefreshToken(String refreshToken) {
        return Optional.ofNullable(TOKEN_REPOSITORY.getIfPresent(refreshToken));
    }

    public void remove(String refreshToken) {
        TOKEN_REPOSITORY.invalidate(refreshToken);
    }

    public void removeAllByUserId(Long userId) {
        TOKEN_REPOSITORY.asMap().entrySet().removeIf(entry -> userId.equals(entry.getValue()));
    }
}