package com.medicarebe.quiz.controller;

import com.medicarebe.quiz.controller.dto.response.QuizResponse;
import com.medicarebe.quiz.service.QuizService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping()
    public List<QuizResponse> getList(@RequestParam(value = "videoId") Long videoId) {
        return quizService.getList(videoId);
    }
}
