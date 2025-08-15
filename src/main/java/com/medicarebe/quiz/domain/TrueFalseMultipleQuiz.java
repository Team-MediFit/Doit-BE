package com.medicarebe.quiz.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("TRUE_FALSE_MULTIPLE")
public class TrueFalseMultipleQuiz extends Quiz {

    // 여러 개의 문장과 각 문장의 정답(True/False)을 함께 저장 (JSON 배열)
    // e.g., [
    //   {"statement": "투안 씨는 베트남 사람이에요.", "isCorrect": true},
    //   {"statement": "저는 학생이에요.", "isCorrect": true},
    //   {"statement": "올리 씨는 의사예요.", "isCorrect": false}
    // ]
    @Column(name = "statements", columnDefinition = "json", nullable = false)
    private String statements;

    @Override
    public QuizType getQuizType() {
        return QuizType.TRUE_FALSE_MULTIPLE;
    }

    @Override
    public Map<String, Object> getDetails(ObjectMapper objectMapper) {
        Map<String, Object> detailsMap = new LinkedHashMap<>();
        try {
            detailsMap.put("statements", objectMapper.readValue(this.statements, new TypeReference<List<Map<String, Object>>>() {}));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON string from entity", e);
        }
        return detailsMap;
    }
}
