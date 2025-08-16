package com.medicarebe.user.service;


import com.medicarebe.auth.service.token.TokenService;
import com.medicarebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteUserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("USER_NOT_FOUND");
        }
        tokenService.logoutAll(userId);
        userRepository.deleteById(userId);
    }
}
