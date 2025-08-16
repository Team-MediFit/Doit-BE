package com.medicarebe.user.service;

import com.medicarebe.auth.service.token.TokenService;
import com.medicarebe.user.api.resetpassword.ResetPasswordRequest;
import com.medicarebe.user.domain.User;
import com.medicarebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public void reset(ResetPasswordRequest req) {
        // 1) 존재 확인
        User user = userRepository.findByAuthId(req.getAuthId())
                .orElseThrow(() -> new IllegalArgumentException("USER_NOT_FOUND"));

        // 2) 새 비밀번호/확인값 일치
        if (!req.getNewPassword().equals(req.getPasswordCheck())) {
            throw new IllegalArgumentException("PASSWORD_MISMATCH");
        }

        // 3) 기존 비번과 동일 금지
        if (passwordEncoder.matches(req.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("SAME_AS_OLD_PASSWORD");
        }

        // 4) 인코딩 후 저장 (dirty checking)
        String encoded = passwordEncoder.encode(req.getNewPassword());
        user.changePassword(encoded);

        // 5) 모든 세션/리프레시 토큰 무효화
        tokenService.logoutAll(user.getId());
    }
}
