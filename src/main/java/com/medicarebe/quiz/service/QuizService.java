package com.medicarebe.quiz.service;

import com.medicarebe.quiz.controller.dto.response.QuizResponse;
import com.medicarebe.quiz.domain.Quiz;
import com.medicarebe.quiz.repository.QuizRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    @Transactional(readOnly = true)
    public List<QuizResponse> getList(Long videoId) {
        List<Quiz> quizzes = quizRepository.findByVideoId(videoId);

        return quizzes.stream()
                .map(QuizResponse::from)
                .collect(Collectors.toList());
    }
}
