package com.medicarebe.video.controller.dto.request;

public record SaveVideoRequest(
        Long videoId,
        int progress,
        float lastWatch
) {

}
