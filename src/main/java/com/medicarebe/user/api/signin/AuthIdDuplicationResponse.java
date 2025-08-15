package com.medicarebe.user.api.signin;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AuthIdDuplicationResponse", description = "authId 중복여부 응답")
public record AuthIdDuplicationResponse(
        @Schema(description = "중복 여부", example = "true")
        boolean duplicate
) {}