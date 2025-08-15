package com.medicarebe.video.repository;

import com.medicarebe.user.domain.User;
import com.medicarebe.video.domain.UserVideo;
import com.medicarebe.video.domain.Video;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserVideoRepository extends JpaRepository<UserVideo, Long> {

    List<UserVideo> findByUserAndVideoIn(User user, List<Video> videos);

    Optional<UserVideo> findByUserIdAndVideoId(Long aLong, Long aLong1);
}
