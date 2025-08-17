package com.medicarebe.user.service;

import com.medicarebe.user.api.update.UpdateUserDetailsRequest;
import com.medicarebe.user.api.update.UserResponse;
import com.medicarebe.user.domain.User;
import com.medicarebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateUserService {
    private final UserRepository userRepository;
    public UserResponse updateMyPage(Long userId, UpdateUserDetailsRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("USER_NOT_FOUND"));

        user.updateDetails(
                req.fullName(),
                req.reasonComeToKorea(),
                req.planStudyDate(),
                req.birth(),
                req.gender(),
                req.nationality(),
                req.phoneNumber(),
                req.purpose(),
                req.topikLevel(),
                req.heardFrom(),
                req.applyCaregiverProgram(),
                req.educationLevel(),
                req.englishLevel()
        );

        return UserResponse.from(user); // 업데이트된 회원 정보를 응답
    }
}
