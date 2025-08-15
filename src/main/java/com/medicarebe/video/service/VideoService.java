package com.medicarebe.video.service;

import com.medicarebe.user.domain.User;
import com.medicarebe.user.repository.UserRepository;
import com.medicarebe.video.controller.dto.request.SaveVideoRequest;
import com.medicarebe.video.controller.dto.response.VideoInfoResponse;
import com.medicarebe.video.domain.UserVideo;
import com.medicarebe.video.domain.Video;
import com.medicarebe.video.repository.UserVideoRepository;
import com.medicarebe.video.repository.VideoRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final UserVideoRepository userVideoRepository;

    @Transactional(readOnly = true)
    public List<VideoInfoResponse> getList(Long userId, String category) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        List<Video> videos = videoRepository.findByCategory(category);

        List<UserVideo> userVideos = userVideoRepository.findByUserAndVideoIn(user, videos);

        Map<Long, UserVideo> userVideoMap = userVideos.stream()
                .collect(Collectors.toMap(uv -> uv.getVideo().getId(), uv -> uv));

        return videos.stream()
                .map(video -> {
                    UserVideo userVideo = userVideoMap.get(video.getId());
                    return VideoInfoResponse.of(video, userVideo);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(Long userId, SaveVideoRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Video video = videoRepository.findById(request.videoId())
                .orElseThrow(() -> new RuntimeException("Video Not Found"));

        Optional<UserVideo> findUserVideo = userVideoRepository
                .findByUserIdAndVideoId(userId, request.videoId());

        findUserVideo.ifPresentOrElse(
                userVideo -> userVideo.update(request.progress(), request.lastWatch()),
                () -> {
                    UserVideo newUserVideo = UserVideo.builder()
                            .user(user)
                            .video(video)
                            .progress(request.progress())
                            .lastWatch(request.lastWatch())
                            .build();
                    userVideoRepository.save(newUserVideo);
                }
        );
    }
}
