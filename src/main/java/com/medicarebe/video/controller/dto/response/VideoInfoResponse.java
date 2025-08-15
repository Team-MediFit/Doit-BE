package com.medicarebe.video.controller.dto.response;

import com.medicarebe.video.domain.UserVideo;
import com.medicarebe.video.domain.Video;
import lombok.Builder;

@Builder
public record VideoInfoResponse(
        Long videoId,
        String category,
        String title,
        String description,
        String url,
        int progress,
        float lastWatch,
        boolean watched
) {
    public static VideoInfoResponse of(Video video, UserVideo userVideo) {
        return VideoInfoResponse.builder()
                .videoId(video.getId())
                .category(video.getCategory())
                .title(video.getTitle())
                .description(video.getDescription())
                .url(video.getDescription())
                .progress(userVideo.getProgress() == null ? 0 : userVideo.getProgress())
                .lastWatch(userVideo.getLastWatch() == null ? 0 : userVideo.getLastWatch())
                .watched(userVideo.isWatched())
                .build();
    }
}
