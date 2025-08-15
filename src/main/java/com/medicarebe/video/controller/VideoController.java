package com.medicarebe.video.controller;

import com.medicarebe.video.controller.dto.request.SaveVideoRequest;
import com.medicarebe.video.controller.dto.response.VideoInfoResponse;
import com.medicarebe.video.service.VideoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/video")
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/{category}")
    public List<VideoInfoResponse> getList(
            @AuthenticationPrincipal Long userId,
            @PathVariable(value = "category") String category
    ) {
        return videoService.getList(userId, category);
    }

    @PostMapping("/progress")
    public void save(
            @AuthenticationPrincipal Long userId,
            @RequestBody SaveVideoRequest request
    ) {
        videoService.save(userId, request);
    }
}
