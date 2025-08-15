package com.medicarebe.quiz.repository;

import com.medicarebe.quiz.domain.Quiz;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findByVideoId(Long videoId);

}
