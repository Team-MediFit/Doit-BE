package com.medicarebe.quiz.controller.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicarebe.quiz.domain.Quiz;
import com.medicarebe.quiz.domain.QuizType;
import java.util.Map;
import lombok.Builder;

@Builder
public record QuizResponse(
        Long quizId,
        String instruction,
        QuizType quizType,
        Map<String, Object> details
) {

    public static QuizResponse from(Quiz quiz) {
        return QuizResponse.builder()
                .quizId(quiz.getId())
                .instruction(quiz.getInstruction())
                .quizType(quiz.getQuizType())
                .details(quiz.getDetails(new ObjectMapper()))
                .build();
    }
}