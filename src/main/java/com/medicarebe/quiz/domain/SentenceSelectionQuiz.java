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
@DiscriminatorValue("SENTENCE_SELECTION")
public class SentenceSelectionQuiz extends Quiz {

    // 선택지로 주어지는 문장들 (JSON 배열 형태)
    // 각 선택지는 문장 텍스트와 음성 파일 URL을 가질 수 있음
    // e.g., [{"text": "안녕하세요, 저는 마리아예요.", "audio": "url1"}, {"text": "안녕하세요, 저는 마리아에요.", "audio": "url2"}]
    @Column(name = "choices", columnDefinition = "json", nullable = false)
    private String choices;

    // 정답 문장 (e.g., "안녕하세요, 저는 마리아예요.")
    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @Override
    public QuizType getQuizType() {
        return QuizType.SENTENCE_SELECTION;
    }

    @Override
    public Map<String, Object> getDetails(ObjectMapper objectMapper) {
        Map<String, Object> detailsMap = new LinkedHashMap<>();
        try {
            detailsMap.put("choices", objectMapper.readValue(this.choices, new TypeReference<List<Map<String, String>>>() {}));
            detailsMap.put("correctAnswer", this.correctAnswer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON string from entity", e);
        }
        return detailsMap;
    }
}
