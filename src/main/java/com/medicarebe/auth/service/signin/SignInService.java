package com.medicarebe.auth.service.signin;

import com.medicarebe.auth.security.TokenProvider;
import com.medicarebe.auth.service.SignInResult;
import com.medicarebe.user.domain.User;
import com.medicarebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignInService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public SignInResult signIn(String authId, String rawPassword) {
        User user = userRepository.findByAuthId(authId)
                .orElseThrow(() -> new IllegalArgumentException("USER_NOT_FOUND"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("INVALID_CREDENTIALS");
        }

        // TokenProvider 규격에 맞춰 호출 (role 없이 id만 포함)
        String accessToken = tokenProvider.generateToken(user.getId());
        String refreshToken = tokenProvider.generateRefreshToken();

        return new SignInResult(user.getId(), accessToken, refreshToken);
    }
}