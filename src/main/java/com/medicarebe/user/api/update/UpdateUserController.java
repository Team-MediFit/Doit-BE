package com.medicarebe.user.api.update;

import io.swagger.v3.oas.annotations.Parameter;

import com.medicarebe.core.meta.LoginUser;
import com.medicarebe.user.service.UpdateUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UpdateUserController {
    private final UpdateUserService updateUserService;

    @PutMapping("/me")
    public UserResponse updateMyPage(
            @Parameter(hidden = true) @LoginUser Long userId,
            @Valid @RequestBody UpdateUserDetailsRequest request) {
        return updateUserService.updateMyPage(userId, request);
    }
}
