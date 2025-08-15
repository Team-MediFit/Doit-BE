package com.medicarebe.video.domain;

import com.medicarebe.core.BaseEntity;
import com.medicarebe.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVideo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_video_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(name = "progress", nullable = false)
    private Integer progress;

    @Column(name = "last_watch")
    private Float lastWatch;

    @Column(name = "watched", nullable = false)
    private boolean watched;

    public void update(int progress, float lastWatch) {
        this.progress = progress;
        this.lastWatch = lastWatch;
    }
}
