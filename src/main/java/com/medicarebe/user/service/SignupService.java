package com.medicarebe.user.service;


import com.medicarebe.user.api.signin.SignUpRequest;
import com.medicarebe.user.domain.User;
import com.medicarebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest request) {

        assertAuthIdNotDuplicate(request.authId());

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = User.create(
                request.authId(),
                request.userName(),
                encodedPassword,
                request.agreedPolicy()
        );

        userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public void assertAuthIdNotDuplicate(String authId) {
        if (userRepository.existsByAuthId(authId)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
    }
    @Transactional(readOnly = true)
    public boolean checkDuplicateUser(String authId) {
        return userRepository.existsByAuthId(authId);
    }
}