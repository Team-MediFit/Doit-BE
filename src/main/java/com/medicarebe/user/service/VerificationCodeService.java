package com.medicarebe.user.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {

    private static final int CODE_TTL_MINUTES = 5;     // 5 min TTL
    private static final int SEND_WINDOW_SECONDS = 60; // 1 req / 60s

    private final MailService mailService;
    private final SecureRandom random = new SecureRandom();

    // email -> code (일정 TTL)
    private final Cache<String, String> codeCache = CacheBuilder.newBuilder()
            .expireAfterWrite(CODE_TTL_MINUTES, TimeUnit.MINUTES)
            .maximumSize(100_000)
            .build();

    // email -> last sent timestamp (rate limit)
    private final Cache<String, Long> throttleCache = CacheBuilder.newBuilder()
            .expireAfterWrite(SEND_WINDOW_SECONDS, TimeUnit.SECONDS)
            .maximumSize(100_000)
            .build();

    public void sendCode(String email) {
        long now = System.currentTimeMillis();
        Long last = throttleCache.getIfPresent(email);
        if (last != null && now - last < Duration.ofSeconds(SEND_WINDOW_SECONDS).toMillis()) {
            throw new IllegalStateException("TOO_MANY_REQUESTS");
        }

        String code = generate6Digits();
        codeCache.put(email, code);
        throttleCache.put(email, now);

        mailService.sendVerificationCode(email, code);
    }

    public boolean verify(String email, String inputCode) {
        String saved = codeCache.getIfPresent(email);
        if (saved == null) return false;
        boolean ok = saved.equals(inputCode);
        if (ok) {
            codeCache.invalidate(email); // one-time use
        }
        return ok;
    }

    private String generate6Digits() {
        // 000000 ~ 999999 (leading zeros preserved)
        int n = random.nextInt(1_000_000);
        return "%06d".formatted(n);
    }
}