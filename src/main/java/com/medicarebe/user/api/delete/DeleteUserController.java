package com.medicarebe.user.api.delete;

import com.medicarebe.core.meta.LoginUser;
import com.medicarebe.user.service.DeleteUserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users - Delete", description = "회원 탈퇴 API")
public class DeleteUserController {

    private final DeleteUserService deleteUserService;

    @DeleteMapping
    public ResponseEntity<Void> deleteMyAccount(@Parameter(hidden = true)@LoginUser Long userId) {
        deleteUserService.deleteUser(userId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}