package com.medicarebe.video.repository;

import com.medicarebe.video.domain.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByCategory(String category);
}
