package com.medicarebe.auth.security;

import com.medicarebe.user.entity.User;
import com.medicarebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> authentication) {
        // 기존: isAssignable(...) → 컴파일 에러 방지용 표준 구현
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        return processAuthentication(authenticationToken);
    }

    private Authentication processAuthentication(JwtAuthenticationToken authenticationToken) {
        User user = userRepository.findByAuthId(String.valueOf(authenticationToken.getPrincipal()))
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        String userPassword = String.valueOf(authenticationToken.getCredentials());
        if (!passwordEncoder.matches(userPassword, user.getPassword())) {
            throw new RuntimeException("INCORRECT_PASSWORD");
        }

        return new JwtAuthenticationToken(
                user.getId(),
                null,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}